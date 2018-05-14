package util;

import com.sun.rowset.CachedRowSetImpl;

import java.sql.*;

public class DBUtil {

    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String JDBC_STR = "jdbc:oracle:thin:@localhost:1521:XE"; //"jdbc:oracle:thin:@//localhost:1521/XE";

    private static final String USER = "system";
    private static final String PASSWORD = "systempassword";

    private static Connection connection = null;

    public static void dbConnect() throws ClassNotFoundException, SQLException {
        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("Oracle JDBC Driver Registered.");

            connection = DriverManager.getConnection(JDBC_STR, USER, PASSWORD);
            System.out.println("Got connection.");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC driver not found.");
            throw e;
        } catch (SQLException e) {
            System.out.println("Connection failed.");
            throw e;
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

    public static ResultSet dbExecuteSelect(String query) throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;
        CachedRowSetImpl cursor = null;

        try {
            dbConnect();

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            cursor = new CachedRowSetImpl();
            cursor.populate(resultSet);
        } catch (Exception e) {
            System.out.println("Problem occurred at execute SELECT operation.");
            e.printStackTrace();
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();

            dbDisconnect();
        }

        return cursor;
    }

    // TODO: dbExecuteUpdate

}
