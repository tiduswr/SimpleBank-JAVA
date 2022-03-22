package database.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Conta;
import util.SQL_ERROR_LOG;

public class ContaDAO implements CRUD<Conta, String>{
    Connection con;
    
    public ContaDAO(Connection con){
        this.con = con;
    }
    
    private String createSql(Conta dados, String sql){
        sql = sql.replaceFirst("<T>", dados.getAgencia());
        sql = sql.replaceFirst("<T>", dados.getNumeroConta());
        sql = sql.replaceFirst("<T>", new SimpleDateFormat("dd/MM/yyyy").format(dados.getDateCreation()));
        sql = sql.replaceFirst("<T>", String.valueOf(dados.getSaldo()));
        try {
            long idPessoa = findIdPessoa(dados.getCpfTitular());
            if(idPessoa != -1){
                sql = sql.replaceFirst("<T>", String.valueOf(idPessoa));
                if(dados.isActive()){
                    sql = sql.replaceFirst("<T>", "1");
                }else{
                    sql = sql.replaceFirst("<T>", "0");
                }
                return sql;
            }else{
                return null;
            }
        } catch (SQLException ex) {
            SQL_ERROR_LOG.message("Erro ao procurar CPF do titular da Conta!", ex);
            return null;
        }
    }
    
    private long findIdPessoa(String cpf) throws SQLException{
        String sql = "SELECT * FROM pessoas WHERE cpf = '" + cpf +"'";
        Statement st = con.createStatement();
        if(st != null){
            ResultSet rs = st.executeQuery(sql);
            if(rs != null && !rs.isClosed()){
                return rs.getLong("id");
            }
        }
        return -1;
    }
    
    private void closeStatementAndResultSet(ResultSet rs, Statement st) throws SQLException{
        if(rs != null && !rs.isClosed()) rs.close();
        if(st != null && !st.isClosed()) st.close();
    }
    
    @Override
    public boolean create(Conta dados) {
        Conta find = read(dados.getAgencia() + "-" + dados.getNumeroConta());
        String sql = "INSERT INTO contas (agencia, numeroConta, dtCreation, saldo, idPessoa, active) " 
                + "VALUES ('<T>', '<T>', '<T>', <T>, <T>, <T>)";
        
        try {
            if(find == null){
                Statement st = con.createStatement();
                sql = createSql(dados, sql);
                
                if(sql != null){
                    st.execute(sql);
                    closeStatementAndResultSet(null, st);
                    return true;
                }
                
                closeStatementAndResultSet(null, st);
                return false;
            }
        } catch (SQLException ex) {
            SQL_ERROR_LOG.message("Error in Insert Conta!", ex);
        }
        return false;
    }

    @Override
    public Conta read(String conta) {
        String cc[] = conta.split("-");
        
        if(cc.length > 1){
            String sql = "SELECT * FROM contas "
                        + "INNER JOIN pessoas "
                        + "ON pessoas.id = contas.idPessoa "
                        + "WHERE agencia='" + cc[0] + "' AND numeroConta='" + cc[1] + "'";
            
            try {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql);
                if(rs != null && !rs.isClosed()){
                    Conta o = new Conta();

                    o.setAgencia(rs.getString("agencia"));
                    o.setNumeroConta(rs.getString("numeroConta"));
                    o.setDateCreation(new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString("dtCreation")));
                    o.setActive(rs.getBoolean("active"));
                    o.setSaldo(rs.getDouble("saldo"));
                    o.setTitular(rs.getString("cpf"));
                    o.setIdConta(rs.getLong("idConta"));
                    closeStatementAndResultSet(rs, st);
                    return o;
                    
                }
            } catch (SQLException ex) {
                SQL_ERROR_LOG.message("Error in read Conta!", ex);
            } catch (ParseException ex) {
                SQL_ERROR_LOG.message("Error in read Conta! (Parse Exception)", null);
            }
        }
        return null;
    }

    @Override
    public boolean update(Conta dados) {
        String sql = "UPDATE contas SET agencia='<T>', numeroConta='<T>', dtCreation='<T>', saldo=<T>, idPessoa=<T>, active=<T>"
                + " WHERE idConta=" + String.valueOf(dados.getIdConta());
        
        try {
            sql = createSql(dados, sql);
            Statement st = con.createStatement();
            
            st.execute(sql);
            closeStatementAndResultSet(null, st);
            
            Conta teste = read(dados.getAgencia() + "-" + dados.getNumeroConta());
            return teste != null && dados.getIdConta() == teste.getIdConta();
        } catch (SQLException ex) {
            SQL_ERROR_LOG.message("Error in update Conta!", ex);
        }
        
        return false;
    }

    @Override
    public boolean delete(String conta) {
        Conta find = read(conta);
        String sql = "DELETE FROM contas WHERE idConta=<T>";
        
        try {
            if(find != null){
                sql = sql.replaceFirst("<T>",String.valueOf(find.getIdConta()));
                Statement st = con.createStatement();
                
                st.execute(sql);
                closeStatementAndResultSet(null, st);
                return true;
            }
        } catch (SQLException ex) {
            SQL_ERROR_LOG.message("Error in delete Conta!", ex);
        }
        
        return false;
    }

    @Override
    public ArrayList<Conta> list() {
        String sql =  "SELECT * FROM contas "
                    + "INNER JOIN pessoas "
                    + "ON pessoas.id = contas.idPessoa";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            ArrayList<Conta> l = new ArrayList<>();
            
            while(rs.next()){
                Conta o = new Conta();

                    o.setAgencia(rs.getString("agencia"));
                    o.setNumeroConta(rs.getString("numeroConta"));
                    o.setDateCreation(new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString("dtCreation")));
                    o.setSaldo(rs.getDouble("saldo"));
                    o.setTitular(rs.getString("cpf"));
                    o.setActive(rs.getBoolean("active"));
                    l.add(o);
            }
            closeStatementAndResultSet(rs, st);
            
            return l;
        } catch (SQLException ex) {
            SQL_ERROR_LOG.message("Error in create list of Contas!", ex);
        } catch (ParseException ex) {
            SQL_ERROR_LOG.message("Error in create list of Contas! (Parse Error)", null);
        }
        return null;
    }
    
    public ArrayList<Conta> list(String cpf) {
        String sql =  "SELECT * FROM contas "
                    + "INNER JOIN pessoas "
                    + "ON pessoas.id = contas.idPessoa "
                    + "WHERE cpf = '" + cpf + "'";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            ArrayList<Conta> l = new ArrayList<>();
            
            while(rs.next()){
                Conta o = new Conta();

                    o.setAgencia(rs.getString("agencia"));
                    o.setNumeroConta(rs.getString("numeroConta"));
                    o.setDateCreation(new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString("dtCreation")));
                    o.setSaldo(rs.getDouble("saldo"));
                    o.setTitular(rs.getString("cpf"));
                    o.setActive(rs.getBoolean("active"));
                    l.add(o);
            }
            closeStatementAndResultSet(rs, st);
            
            return l;
        } catch (SQLException ex) {
            SQL_ERROR_LOG.message("Error in create list of Contas!", ex);
        } catch (ParseException ex) {
            SQL_ERROR_LOG.message("Error in create list of Contas! (Parse Error)", null);
        }
        return null;
    }
    
    public long getLastId(){
        String sql =  "SELECT MAX(idConta) FROM contas";
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            long response = rs.getLong("MAX(idConta)");
            closeStatementAndResultSet(rs, st);
            return response;
        } catch (SQLException ex) {
            Logger.getLogger(ContaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
}
