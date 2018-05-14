package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;

public class DBUtil {

    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String JDBC_STR = "jdbc:oracle:thin:@localhost:1521:XE"; //"jdbc:oracle:thin:@//localhost:1521/XE";

    private static final String USER = "system";
    private static final String PASSWORD = "systempassword";

    private static Connection connection = null;

    public static void dbConnect() {
        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("Oracle JDBC Driver Registered.");

            connection = DriverManager.getConnection(JDBC_STR, USER, PASSWORD);
            System.out.println("Got connection.");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failed.");
            e.printStackTrace();
        }
    }

    public static void dbDisconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("Connection closing failed.");
            e.printStackTrace();
        }
    }

    // TODO: dbExecuteQuery

    // TODO: dbExecuteUpdate

}
