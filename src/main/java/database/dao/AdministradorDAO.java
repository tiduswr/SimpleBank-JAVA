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
import model.Administrador;
import model.Cliente;
import model.Endereco;
import model.Telefone;
import util.SQL_ERROR_LOG;

public class AdministradorDAO implements CRUD<Administrador, String>{
    private Connection con;
    
    public AdministradorDAO(Connection con){
        this.con = con;
    }
    
    private void closeStatementAndResultSet(ResultSet rs, Statement st) throws SQLException{
        if(rs != null && !rs.isClosed()) rs.close();
        if(st != null && !st.isClosed()) st.close();
    }
    
    private String createSqlPessoa(String sql, Administrador dados){
        sql = sql.replaceFirst("<T>", dados.getCpf());
        sql = sql.replaceFirst("<T>", dados.getNome());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sql = sql.replaceFirst("<T>", sdf.format(dados.getDtNascimento()));
        sql = sql.replaceFirst("<T>", dados.getEmail());
        sql = sql.replaceFirst("<T>", dados.getEndereco().getBairro());
        sql = sql.replaceFirst("<T>", dados.getEndereco().getCidade());
        sql = sql.replaceFirst("<T>", dados.getEndereco().getEstado());
        sql = sql.replaceFirst("<T>", String.valueOf(dados.getEndereco().getNumCasa()));
        sql = sql.replaceFirst("<T>", dados.getEndereco().getRua());
        sql = sql.replaceFirst("<T>", String.valueOf(dados.getFone().getDdd()));
        sql = sql.replaceFirst("<T>", dados.getFone().getNumero());
        return sql;
    }
    
    private String createSqlAdministrador(String sql, Administrador dados){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sql = sql.replaceFirst("<T>", sdf.format(dados.getDtAdmissao()));
        sql = sql.replaceFirst("<T>", dados.getCargo());
        return sql;
    }
    
    @Override
    public boolean create(Administrador dados) {
        Administrador find = read(dados.getCpf());
        Cliente findCliente = new ClienteDAO(this.con).read(dados.getCpf());
        String sqlPessoa = "INSERT INTO pessoas (cpf, nome, dtNascimento, email, bairro, cidade, estado, "
                + "numCasa, rua, dddTelefone, numeroTelefone) " 
                + "VALUES ('<T>', '<T>', '<T>', '<T>', '<T>', '<T>', '<T>', <T>, '<T>', <T>, '<T>')";
        String sqlAdm= "INSERT INTO administradores (dtAdmissao, cargo, idPessoa) " 
                + "VALUES ('<T>', '<T>', <T>)";
        try {
            if(find == null && findCliente == null){
                Statement st = con.createStatement();
                long idPessoa = getIdPessoa(dados.getCpf());
                
                if(idPessoa == -1){
                    sqlPessoa = createSqlPessoa(sqlPessoa, dados);
                    st.execute(sqlPessoa);
                    idPessoa = getIdPessoa(dados.getCpf());
                }
                
                sqlAdm = createSqlAdministrador(sqlAdm, dados);
                sqlAdm = sqlAdm.replaceFirst("<T>", String.valueOf(idPessoa));
                st.execute(sqlAdm);
                                
                closeStatementAndResultSet(null, st);
                return true;
            }
        } catch (SQLException ex) {
            SQL_ERROR_LOG.message("Error in Insert Administrador!", ex);
        }
        return false;
    }
    
    private long getIdPessoa(String cpf){
        String sql = "SELECT * FROM pessoas WHERE cpf='" + cpf +"'";
        
        try {            
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(sql);

            if(rs != null && !rs.isClosed()){
                return rs.getLong("id");
            }
            
            closeStatementAndResultSet(rs, st);
        } catch (SQLException ex) {
            SQL_ERROR_LOG.message("Error in read Pessoa!", ex);
            return -1;
        }
        return -1;
    }
    
    @Override
    public Administrador read(String cpf) {
        String sql = "SELECT * FROM pessoas "
                    + "INNER JOIN administradores "
                    + "ON pessoas.id = administradores.idPessoa "
                    + "WHERE cpf='" + cpf +"'";
        
        try {            
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(sql);

            if(rs != null && !rs.isClosed()){
                Administrador o = new Administrador();

                o.setCpf(rs.getString("cpf"));
                try {
                    o.setDtAdmissao(new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString("dtAdmissao")));
                    o.setDtNascimento(new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString("dtNascimento")));
                } catch (ParseException ex) {
                    Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                o.setCargo(rs.getString("cargo"));
                o.setEmail(rs.getString("email"));
                o.setIdDatabase(rs.getLong("id"));
                o.setNome(rs.getString("nome"));
                
                Endereco e = new Endereco();
                e.setBairro(rs.getString("bairro"));
                e.setCidade("cidade");
                e.setEstado("estado");
                e.setNumCasa(rs.getInt("numCasa"));
                e.setRua(rs.getString("rua"));
                
                Telefone tel = new Telefone();
                tel.setDdd(rs.getInt("dddTelefone"));
                tel.setNumero(rs.getString("numeroTelefone"));
                
                o.setEndereco(e);
                o.setFone(tel);
                
                closeStatementAndResultSet(rs, st);

                return o;
            }

            closeStatementAndResultSet(rs, st);
        } catch (SQLException ex) {
            SQL_ERROR_LOG.message("Error in read Administrador!", ex);
        }
        
        return null;
    }

    @Override
    public boolean update(Administrador dados) {
        String sql = "UPDATE pessoas SET cpf='<T>', nome='<T>', dtNascimento='<T>', email='<T>', bairro='<T>', "
                + "cidade='<T>', estado='<T>', numCasa=<T>, rua='<T>', dddTelefone=<T>, numeroTelefone='<T>'"
                + " WHERE id=" + String.valueOf(dados.getIdDatabase());
        
        try {
            sql = createSqlPessoa(sql ,dados);
            Statement st = con.createStatement();
            st.execute(sql);
            closeStatementAndResultSet(null, st);
            
            Administrador teste = read(dados.getCpf());
            return teste != null && dados.getIdDatabase() == teste.getIdDatabase();
        } catch (SQLException ex) {
            SQL_ERROR_LOG.message("Error in update Administrador!", ex);
        }
        
        return false;
    }

    @Override
    public boolean delete(String cpf) {
        Administrador find = read(cpf);
        
        String sql = "DELETE FROM administradores WHERE idPessoa=<T>";
        
        try {
            if(find != null){
                sql = sql.replaceFirst("<T>",String.valueOf(find.getIdDatabase()));
                Statement st = con.createStatement();
                
                st.execute(sql);
                closeStatementAndResultSet(null, st);
                return true;
            }
        } catch (SQLException ex) {
            SQL_ERROR_LOG.message("Error in delete Administrador!", ex);
        }
        
        return false;
    }

    @Override
    public ArrayList<Administrador> list() {
        String sql = "SELECT * FROM pessoas "
                    + "INNER JOIN administradores "
                    + "ON pessoas.id = administradores.idPessoa ";
        
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            ArrayList<Administrador> l = new ArrayList<>();
            
            while(rs.next()){
                Administrador o = new Administrador();

                o.setCpf(rs.getString("cpf"));
                try {
                    o.setDtAdmissao(new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString("dtAdmissao")));
                    o.setDtNascimento(new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString("dtNascimento")));
                } catch (ParseException ex) {
                    Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                o.setCargo(rs.getString("cargo"));
                o.setEmail(rs.getString("email"));
                o.setIdDatabase(rs.getLong("id"));
                o.setNome(rs.getString("nome"));
                
                Endereco e = new Endereco();
                e.setBairro(rs.getString("bairro"));
                e.setCidade("cidade");
                e.setEstado("estado");
                e.setNumCasa(rs.getInt("numCasa"));
                e.setRua(rs.getString("rua"));
                
                Telefone tel = new Telefone();
                tel.setDdd(rs.getInt("dddTelefone"));
                tel.setNumero(rs.getString("numeroTelefone"));
                
                o.setEndereco(e);
                o.setFone(tel);
                
                l.add(o);
            }
            closeStatementAndResultSet(rs, st);
            
            return l;
        } catch (SQLException ex) {
            SQL_ERROR_LOG.message("Error in create list of Administrador!", ex);
        }
        return null;
    }
}
