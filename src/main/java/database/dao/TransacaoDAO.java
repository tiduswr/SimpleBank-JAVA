package database.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import model.Conta;
import model.Transacao;
import util.SQL_ERROR_LOG;

public class TransacaoDAO implements CRUD<Transacao, Long>{
    private Connection con;
    
    public TransacaoDAO(Connection con){
        this.con = con;
    }
    
    private void closeStatementAndResultSet(ResultSet rs, Statement st) throws SQLException{
        if(rs != null && !rs.isClosed()) rs.close();
        if(st != null && !st.isClosed()) st.close();
    }
    
    @Override
    public boolean create(Transacao dados) {
        String sqlSelectContas = "SELECT * FROM contas "
                    + "INNER JOIN pessoas "
                    + "ON pessoas.id = contas.idPessoa "
                    + "WHERE agencia='<T>' AND numeroConta='<T>'";
        String sqlCreateTransacoes = "INSERT INTO transacoes (idContaOrigem, idContaDestino, tipoTransacao, "
                    + "valMovimentado, dtMovimento) "
                    + "VALUES (<T>, <T>, <T>, <T>, '<T>')";
        
        Transacao find = read(dados.getIdTransacao());
        
        if(find == null){
            try {
                Statement st = con.createStatement();
                
                //Pega id da conta origem
                String aux = sqlSelectContas.replaceFirst("<T>", dados.getFrom().getAgencia());
                aux = aux.replaceFirst("<T>", dados.getFrom().getNumeroConta());
                ResultSet rs = st.executeQuery(aux);
                sqlCreateTransacoes = sqlCreateTransacoes.replaceFirst("<T>", rs.getString("idConta"));
                closeStatementAndResultSet(rs, st);
                
                //Pega id da conta destino
                st = con.createStatement();
                aux = sqlSelectContas.replaceFirst("<T>", dados.getTo().getAgencia());
                aux = aux.replaceFirst("<T>", dados.getTo().getNumeroConta());
                rs = st.executeQuery(aux);
                sqlCreateTransacoes = sqlCreateTransacoes.replaceFirst("<T>", rs.getString("idConta"));
                closeStatementAndResultSet(rs, st);
                
                //Preenche restante dos dados
                st = con.createStatement();
                sqlCreateTransacoes = sqlCreateTransacoes.replaceFirst("<T>", String.valueOf(dados.getTipo().getValue()));
                sqlCreateTransacoes = sqlCreateTransacoes.replaceFirst("<T>", String.valueOf(dados.getValMovimentado()));
                sqlCreateTransacoes = sqlCreateTransacoes.replaceFirst("<T>", 
                                                    new SimpleDateFormat("dd/MM/yyyy").format(dados.getDtMovimento()));
                
                st.execute(sqlCreateTransacoes);
                closeStatementAndResultSet(rs, st);
                return true;
            } catch (SQLException ex) {
                SQL_ERROR_LOG.message("Error in Insert Transacao!", ex);
            }
        }
        return false;
    }
    
    private Conta buildConta(ResultSet rs) throws SQLException, ParseException{
        Conta o = new Conta();
        o.setAgencia(rs.getString("agencia"));
        o.setNumeroConta(rs.getString("numeroConta"));
        o.setDateCreation(new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString("dtCreation")));
        o.setSaldo(rs.getDouble("saldo"));
        o.setTitular(rs.getString("cpf"));
        return o;
    }
    
    @Override
    public Transacao read(Long id) {
        String sql = "SELECT * FROM transacoes "
                    + "WHERE idTransacao=" + String.valueOf(id);
        String sqlConta = "SELECT * FROM contas "
                        + "INNER JOIN pessoas "
                        + "ON pessoas.id = contas.idPessoa "
                        + "WHERE idConta = <T>";
        try {            
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            if(rs != null && !rs.isClosed()){
                Statement stAux = con.createStatement();
                ResultSet rsAux = stAux.executeQuery(sqlConta.replaceFirst("<T>", String.valueOf(rs.getLong("idContaOrigem"))));
                
                Transacao o = new Transacao();
                
                if(rsAux != null && !rsAux.isClosed()){
                    o.setFrom(buildConta(rsAux));
                    rsAux.close();
                    
                    rsAux = stAux.executeQuery(sqlConta.replaceFirst("<T>", String.valueOf(rs.getLong("idContaDestino"))));
                    if(rsAux != null && !rsAux.isClosed()){
                        o.setTo(buildConta(rsAux));
                    }else{
                        o.setTo(null);
                    }
                    
                    o.setIdTransacao(rs.getLong("idTransacao"));
                    o.setValMovimentado(rs.getDouble("valMovimentado"));
                    o.setTipo(Transacao.TipoTransacao.getByInt(rs.getInt("tipoTransacao")));
                    try {
                        o.setDtMovimento(new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString("dtMovimento")));
                    } catch (ParseException ex) {
                        SQL_ERROR_LOG.message("Error in read Cliente!(Parse Data Movimento)", null);
                    }
                    closeStatementAndResultSet(rsAux, stAux);
                    closeStatementAndResultSet(rs, st);
                    return o;
                }
                closeStatementAndResultSet(rsAux, stAux);
                closeStatementAndResultSet(rs, st);
                return null;
            }

            closeStatementAndResultSet(rs, st);
        } catch (SQLException ex) {
            SQL_ERROR_LOG.message("Error in read Transacao!", ex);
        } catch (ParseException ex) {
            SQL_ERROR_LOG.message("Error in read Transacao!(Parse Data Cria????o Conta)", null);
        }
        return null;
    }

    @Override
    public boolean update(Transacao dados) {
        //Transacao ?? uma classe para hist??rico de movimenta????es feitas nas contas, ent??o
        //n??o ?? necess??rio que tenha um m??todo update
        return false;
    }

    @Override
    public boolean delete(Long id) {
        Transacao find = read(id);
        String sql = "DELETE FROM transacoes WHERE idTransacao=" + String.valueOf(id);
        
        if(find != null){
            try {
                Statement st = con.createStatement();
                st.execute(sql);
                closeStatementAndResultSet(null, st);
                return true;
            } catch (SQLException ex) {
                SQL_ERROR_LOG.message("Error in delete Transacao!", ex);
            }
        }
        return false;
    }

    @Override
    public ArrayList<Transacao> list() {
        String sql = "SELECT * FROM transacoes";
        String sqlConta = "SELECT * FROM contas "
                        + "INNER JOIN pessoas "
                        + "ON pessoas.id = contas.idPessoa "
                        + "WHERE idConta = <T>";
        
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            ArrayList<Transacao> l = new ArrayList<>();
            
            while(rs.next()){
                Statement stAux = con.createStatement();
                ResultSet rsAux = stAux.executeQuery(sqlConta.replaceFirst("<T>", String.valueOf(rs.getLong("idContaOrigem"))));
                
                Transacao o = new Transacao();
                
                if(rsAux != null && !rsAux.isClosed()){
                    o.setFrom(buildConta(rsAux));
                    rsAux.close();
                    
                    rsAux = stAux.executeQuery(sqlConta.replaceFirst("<T>", String.valueOf(rs.getLong("idContaDestino"))));
                    if(rsAux != null && !rsAux.isClosed()){
                        o.setTo(buildConta(rsAux));
                    }
                    
                    o.setIdTransacao(rs.getLong("idTransacao"));
                    o.setValMovimentado(rs.getDouble("valMovimentado"));
                    o.setTipo(Transacao.TipoTransacao.getByInt(rs.getInt("tipoTransacao")));
                    try {
                        o.setDtMovimento(new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString("dtMovimento")));
                    } catch (ParseException ex) {
                        SQL_ERROR_LOG.message("Error in read Cliente!(Parse Data Movimento)", null);
                    }
                    l.add(o);
                    
                    closeStatementAndResultSet(rsAux, stAux);
                }
            }
            closeStatementAndResultSet(rs, st);
            
            return l;
        } catch (SQLException ex) {
            SQL_ERROR_LOG.message("Error in create list of Transacoes!", ex);
        } catch (ParseException ex) {
            SQL_ERROR_LOG.message("Error in read Transacao!(Parse Data Cria????o Conta)", null);
        }
        return null;
    }
    
    public ArrayList<Transacao> list(long... ids) {
        String sql = "SELECT * FROM contas " +
                    "INNER JOIN pessoas " +
                    "ON pessoas.id = contas.idPessoa " +
                    "INNER JOIN transacoes ON " +
                    "contas.idConta = transacoes.idContaOrigem OR contas.idConta = transacoes.idContaDestino " +
                    "WHERE contas.idConta = " + String.valueOf(ids[0]);
        
        if(ids.length > 1){
            for(int i = 1; i < ids.length; i++){
                sql = sql + " OR contas.idConta = " + String.valueOf(ids[i]);
            }
            sql = sql + " GROUP BY transacoes.idTransacao";
        }
        
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            ArrayList<Transacao> l = new ArrayList<>();
            
            while(rs.next()){
                
                Transacao o = new Transacao();
                
                ContaDAO dao = new ContaDAO(con);
                
                Conta cc = dao.read(rs.getLong("idContaOrigem"));
                o.setFrom(cc);
                
                cc = dao.read(rs.getLong("idContaDestino"));
                o.setTo(cc);

                o.setIdTransacao(rs.getLong("idTransacao"));
                o.setValMovimentado(rs.getDouble("valMovimentado"));
                o.setTipo(Transacao.TipoTransacao.getByInt(rs.getInt("tipoTransacao")));
                try {
                    o.setDtMovimento(new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString("dtMovimento")));
                } catch (ParseException ex) {
                    SQL_ERROR_LOG.message("Error in read Cliente!(Parse Data Movimento)", null);
                }
                l.add(o);
            }
            closeStatementAndResultSet(rs, st);
            
            return l;
        } catch (SQLException ex) {
            SQL_ERROR_LOG.message("Error in create list of Transacoes!", ex);
        }
        return null;
    }
    
}
