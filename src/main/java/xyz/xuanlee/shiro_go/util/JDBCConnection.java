package xyz.xuanlee.shiro_go.util;

import xyz.xuanlee.shiro_go.constant.ApplicationConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnection {

    public static Connection getConnection() {
        return StaticJDBCConnection.connection;
    }

    public static void closeConnection() throws SQLException {
        if (StaticJDBCConnection.connection != null) {
            StaticJDBCConnection.connection.close();
        }
    }

    private static class StaticJDBCConnection {

        private static final Connection connection = initConnection();

        private static Connection initConnection() {
            String MYSQL_CONN_URL = ApplicationConfig.DB_CONNECT_URL;
            Connection connection = null;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(MYSQL_CONN_URL);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
            return connection;
        }
    }
}
