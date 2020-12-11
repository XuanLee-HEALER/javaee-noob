package xyz.xuanlee.shiro_go.sql;

public class DepartmentSQL {

    public static final String QUERY_DEPARTMENT_EXIST =
            "select count(*) from t_department where d_name=?";
    public static final String INSERT_DEPARTMENT =
            "insert into t_department(d_name) values (?)";
}
