package database.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Usuario;
import util.SQL_ERROR_LOG;

public class UsuarioDAO implements CRUD<Usuario, String>{
    private Connection con;
    
    public UsuarioDAO(Connection con){
        this.con = con;
    }
    
    private void closeStatementAndResultSet(ResultSet rs, Statement st) throws SQLException{
        if(rs != null && !rs.isClosed()) rs.close();
        if(st != null && !st.isClosed()) st.close();
    }
    
    @Override
    public boolean create(Usuario dados) {
        Usuario find = read(dados.getCpf());
        String sqlUsuario = "INSERT INTO usuarios (senha, tipo, idPessoa) VALUES ('<T>', <T>, <T>)";
        String sqlSelect = "SELECT * pessoas WHERE cpf = '" + dados.getCpf() + "'";
        
        try {
            if(find == null){
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sqlSelect);
                
                sqlUsuario = sqlUsuario.replaceFirst("<T>", dados.getSenha());
                sqlUsuario = sqlUsuario.replaceFirst("<T>", String.valueOf(dados.getTipo().getValue()));
                if(rs != null && !rs.isClosed()){
                    sqlUsuario = sqlUsuario.replaceFirst("<T>", String.valueOf(rs.getLong("id")));
                }
                st.execute(sqlUsuario);
                closeStatementAndResultSet(rs, st);
                return true;
            }
        } catch (SQLException ex) {
            SQL_ERROR_LOG.message("Error in Insert Usuario!", ex);
        }
        return false;
    }

    @Override
    public Usuario read(String cpf) {
        String sql = "SELECT * FROM pessoas "
                    + "INNER JOIN usuarios "
                    + "ON pessoas.id = usuarios.idPessoa "
                    + "WHERE cpf='" + cpf +"'";
        
        try {            
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            if(rs != null && !rs.isClosed()){
                Usuario o = new Usuario();
                
                o.setId(rs.getLong("idUser"));
                o.setCpf(rs.getString("cpf"));
                o.setSenha(rs.getString("senha"));
                o.setTipo(Usuario.TipoUsuario.getByInt(rs.getInt("tipo")));
                
                closeStatementAndResultSet(rs, st);

                return o;
            }

            closeStatementAndResultSet(rs, st);
        } catch (SQLException ex) {
            SQL_ERROR_LOG.message("Error in read Usuario!", ex);
        }
        
        return null;
    }

    @Override
    public boolean update(Usuario dados) {
        String sql = "UPDATE usuarios SET senha='<T>', tipo=<T>"
                + " WHERE id =" + String.valueOf(dados.getId());
        
        try {
            Statement st = con.createStatement();
            
            sql = sql.replaceFirst("<T>", dados.getSenha());
            sql = sql.replaceFirst("<T>", String.valueOf(dados.getTipo().getValue()));
            
            st.execute(sql);
            
            closeStatementAndResultSet(null, st);
            
            Usuario teste = read(dados.getCpf());
            return teste != null && dados.getId() == teste.getId();
        } catch (SQLException ex) {
            SQL_ERROR_LOG.message("Error in update Usuario!", ex);
        }
        
        return false;
    }

    @Override
    public boolean delete(String cpf) {
        Usuario find = read(cpf);
        
        String sql = "DELETE FROM usuarios WHERE idUser=<T>";
        
        try {
            if(find != null){
                sql = sql.replaceFirst("<T>",String.valueOf(find.getId()));
                Statement st = con.createStatement();
                
                st.execute(sql);
                closeStatementAndResultSet(null, st);
                return true;
            }
        } catch (SQLException ex) {
            SQL_ERROR_LOG.message("Error in delete Usuario!", ex);
        }
        
        return false;
    }

    @Override
    public ArrayList<Usuario> list() {
        String sql = "SELECT * FROM pessoas "
                    + "INNER JOIN usuarios "
                    + "ON pessoas.id = usuarios.idPessoa ";
        
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            ArrayList<Usuario> l = new ArrayList<>();
            
            while(rs.next()){
                Usuario o = new Usuario();

                o.setId(rs.getLong("idUser"));
                o.setCpf(rs.getString("cpf"));
                o.setSenha(rs.getString("senha"));
                o.setTipo(Usuario.TipoUsuario.getByInt(rs.getInt("tipo")));
                
                l.add(o);
            }
            closeStatementAndResultSet(rs, st);
            
            return l;
        } catch (SQLException ex) {
            SQL_ERROR_LOG.message("Error in create list of Usuarios!", ex);
        }
        return null;
    }
    
}
