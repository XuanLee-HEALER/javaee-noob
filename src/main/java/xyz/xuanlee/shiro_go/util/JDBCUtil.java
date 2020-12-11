package xyz.xuanlee.shiro_go.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mouseLee
 * @Description JDBC工具类，跟数据库交互
 * @date 11/27/2020
 */
public class JDBCUtil {

    private static Connection connection;

    public static void init() {
        connection = JDBCConnection.getConnection();
    }

    private static boolean executeInsertStatement(String sql, String ... parameters) {
        return false;
    }

    private static boolean executeBatchInsertStatement(String sql, String[][] parameters) {
        return false;
    }

    public static boolean executeModifyStatement(String sql, Object ... parameters) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < parameters.length; i++) {
                SQLParameterMatchHelper.setParameterByType(preparedStatement, i+1, parameters[i]);
            }
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return false;
    }

    public static <T> T executeQueryStatement(Constructor<T> constructor, String sql, Object ... parameters) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < parameters.length; i++) {
                SQLParameterMatchHelper.setParameterByType(preparedStatement,
                        i+1,
                        parameters[i]);
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return SQLParameterMatchHelper.getResultByType(constructor, resultSet);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static <T> List<T> executeQueryBatchStatement(Constructor<T> constructor, String sql, Object ... parameters) {
        List<T> ts = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < parameters.length; i++) {
                SQLParameterMatchHelper.setParameterByType(preparedStatement,
                        i+1,
                        parameters[i]);
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ts.add(SQLParameterMatchHelper.getResultByType(constructor, resultSet));
            }
            return ts;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return ts;
    }

    public static void startTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }

    public static void endTransaction() throws SQLException {
        connection.setAutoCommit(true);
    }

    public static void rollBackTransaction() throws SQLException {
        connection.rollback();
    }

    public static void commitTransaction() throws SQLException {
        connection.commit();
    }

    public static void close() {
        try {
            JDBCConnection.closeConnection();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
