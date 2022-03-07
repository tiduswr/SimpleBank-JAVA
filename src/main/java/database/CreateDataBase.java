package database;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import util.SQL_ERROR_LOG;

public class CreateDataBase {
    private static final ArrayList<String> sqlTables = new ArrayList<String>();
    private static boolean initialized = false;
    
    private static boolean createTable(String sql,DatabaseConnect con) {
        System.out.println("DB_LOG -> Creating table if not exist in database!");
        try {
            Statement stmt = con.getConnection().createStatement();
            stmt.execute(sql);
            stmt.close();
            return true;
        } catch (SQLException ex) {
            SQL_ERROR_LOG.message("Error in Table Creation!", ex);
            return false;
        }
    }
    
    public static boolean createDataBaseAndTables(DatabaseConnect con){
        if(!initialized) {
            readTextFileWithSqlInstructions();
            initialized = true;
        }
        return sqlTables.stream().noneMatch(t -> (!createTable(t, con)));
    }
    
    private static void readTextFileWithSqlInstructions(){
        try {
            String  x = FileUtils.readFileToString(new File("sqlInstructions.sql"), (Charset) null);
            sqlTables.addAll(Arrays.asList(x.split(";")));
        } catch (IOException ex) {
            Logger.getLogger(CreateDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
