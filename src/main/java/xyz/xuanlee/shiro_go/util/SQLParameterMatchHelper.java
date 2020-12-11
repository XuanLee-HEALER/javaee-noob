package xyz.xuanlee.shiro_go.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class SQLParameterMatchHelper {

    public static void setParameterByType(PreparedStatement statement, int idx, Object o) throws SQLException {
        if (o.getClass().equals(LocalDateTime.class)) {
            statement.setTimestamp(idx, Timestamp.valueOf((LocalDateTime) o));
            return;
        }
        if (o.getClass().equals(String.class)) {
            statement.setString(idx, (String) o);
            return;
        }
        if (o.getClass().equals(Integer.class)) {
            statement.setInt(idx, (Integer) o);
            return;
        }
        if (o.getClass().equals(Long.class)) {
            statement.setLong(idx, (Long) o);
        }
    }

    public static <T> T getResultByType(Constructor<T> constructor, ResultSet resultSet)
            throws SQLException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?>[] paramTypes = constructor.getParameterTypes();
        Object[] initParams = new Object[constructor.getParameterCount()];
        int idx = 0;
        for (Class<?> paramType: paramTypes) {
            if (paramType.equals(LocalDateTime.class)) {
                if (resultSet.getTimestamp(idx+1) != null) {
                    initParams[idx++] = LocalDateTime.ofInstant(
                            resultSet.getTimestamp(idx).toInstant(),
                            ZoneId.of("UTC"));
                } else {
                    initParams[idx++] = null;
                }
            }
            if (paramType.equals(String.class)) {
                initParams[idx++] = resultSet.getString(idx);
            }
            if (paramType.equals(Integer.class) || paramType.equals(int.class)) {
                initParams[idx++] = resultSet.getInt(idx);
            }
            if (paramType.equals(Long.class) || paramType.equals(long.class)) {
                initParams[idx++] = resultSet.getLong(idx);
            }
        }
        return constructor.newInstance(initParams);
    }
}
