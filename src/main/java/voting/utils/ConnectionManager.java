package voting.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionManager {

    private static  final String PASSWORD = "postgres";
    private static final String USERNAME = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5433/voting_system";

    private ConnectionManager () {

    };

    public static Connection get()  {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
