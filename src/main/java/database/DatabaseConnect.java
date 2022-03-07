package database;

import java.sql.Connection;

public interface DatabaseConnect {
    public boolean connect();
    public boolean closeConnection();
    public Connection getConnection();
}
