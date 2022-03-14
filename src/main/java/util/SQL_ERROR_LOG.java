package util;

import java.sql.SQLException;

public class SQL_ERROR_LOG {
    public static void message(String m, SQLException ex){
        System.out.println("DB_LOG -> " + m);
        if(ex != null){
            System.out.println("Error Description: " + "SQL STATE: " + ex.getSQLState() + 
                "MESSAGE: " + ex.getLocalizedMessage());
        }
    }
}
