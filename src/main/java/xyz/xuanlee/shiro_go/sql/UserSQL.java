package xyz.xuanlee.shiro_go.sql;

public class UserSQL {
    public static final String QUERY_USER_ID_BY_USERNAME =
            "select id from t_user where u_name=?";
    public static final String INSERT_USER =
            "insert into t_user(u_name, u_pswd) values(?,?)";
    public static final String QUERY_USER_LOCK_BY_USERNAME =
            "select u_name, u_pswd, lastLoginDateTime, isLock from t_user where u_name=? and u_pswd=?";
    public static final String QUERY_USER_QTY_BY_USERNAME =
            "select count(*) as qty from t_user where u_name=?";
    public static final String QUERY_DEPARTMENT_IS_EXIST =
            "select count(*) as qty from t_department where id=?";
    public static final String QUERY_USERS_INFO =
            "select t1.u_name, t1.u_pswd, t2.d_name\n" +
                    "from t_user t1\n" +
                    "left join t_department t2\n" +
                    "on t1.d_id=t2.id";
    public static final String QUERY_USER_BY_LOG_TIME =
            "select u_name, u_pswd from t_user " +
                    "where lastLoginDateTime >= ? " +
                    "and lastLoginDateTime <= ?";
    public static final String UPDATE_USER_LOGIN_DATETIME =
            "update t_user set lastLoginDateTime=now() where u_name=?";
    public static final String UPDATE_USER_USERNAME =
            "update t_user set u_name=? where id=?";
    public static final String UPDATE_USER_DEPARTMENT_ID_BY_ID =
            "update t_user set d_id=? where id=?";
    public static final String LOCK_USER =
            "update t_user set isLock='1' where u_name=?";
    public static final String DELTE_USER =
            "delete from t_user where id=cast(? as signed)";
}
