package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.sqlite.SQLiteConfig;
import util.SQL_ERROR_LOG;

public class SQLiteConnection implements DatabaseConnect {
    
    protected Connection con;
    private final String url;
    
    public SQLiteConnection(){
        con = null;
        url = "jdbc:sqlite:db/database.db";
    }
    
    public SQLiteConnection(String url){
        con = null;
        this.url = "jdbc:sqlite:" + url;
    }
    
    @Override
    public boolean connect(){
        try {
            SQLiteConfig config = new SQLiteConfig();  
            config.enforceForeignKeys(true);  
            con = DriverManager.getConnection(url, config.toProperties());
            System.out.println("DB_LOG -> Database Connected!");
            return true;
        } catch (SQLException ex) {
            SQL_ERROR_LOG.message("Error trying to connect on DataBase!", ex);
        }
        return false;
    }
    
    @Override
    public boolean closeConnection(){
        if(con != null){
            try {
                con.close();
                System.out.println("DB_LOG -> Database Disconected!");
                return true;
            } catch (SQLException ex) {
                SQL_ERROR_LOG.message("Error trying to Disconnect on DataBase!", ex);
            }
        }
        return false;
    }
    
    @Override
    public Connection getConnection(){
        return this.con;
    }
    
}
