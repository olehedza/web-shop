package mate.academy.webshop.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL Driver not found", e);
        }
    }

    private static final String DB_DRIVER_URI = "jdbc:mysql://localhost:3306/"
            + "%s?serverTimezone=UTC";
    private static final String DB_NAME = "webshop";

    public static Connection getConnection() {
        Properties dbProperties = new Properties();
        dbProperties.put("user", "root");
        dbProperties.put("password", "meshuggah");

        String url = String.format(DB_DRIVER_URI, DB_NAME);
        Connection connection;
        try {
            connection = DriverManager.getConnection(url, dbProperties);
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException("Can't establish connection to DB", e);
        }
    }
}
