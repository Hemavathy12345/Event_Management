package default_package;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/event_management1";
    private static final String USER = "root";
    private static final String PASSWORD = "Mahindra";
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
