package xyz.xuanlee.shiro_go.sql;

public class OfficialSupplyClassSQL {

    public static final String QUERY_FIRST_CLASS_EXIST =
            "select count(*) from t_official_supply_first_class where f_c_name=?";
    public static final String QUERY_SECOND_CLASS_EXIST =
            "select count(*) from t_official_supply_second_class where s_c_name=?";
    public static final String QUERY_FIRST_CLASS_EXIST_BY_ID =
            "select count(*) from t_official_supply_first_class where id=?";
    public static final String QUERY_FIRST_CLASS_BY_ID =
            "select f_c_name from t_official_supply_first_class where id=?";
    public static final String QUERY_ALL_SECOND_WITH_FIRST =
            "select t1.s_c_name, t2.f_c_name from t_official_supply_second_class t1\n" +
                    "left join t_official_supply_first_class t2\n" +
                    "on t1.s_id = t2.id";
    public static final String QUERY_SECOND_WITH_FIRST_BY_ID =
            "select t1.s_c_name, t2.f_c_name from t_official_supply_second_class t1\n" +
                    "left join t_official_supply_first_class t2\n" +
                    "on t1.s_id = t2.id\n" +
                    "where t1.id=?";
    public static final String QUERY_ALL_FIRST_WITH_SECOND =
            "select t1.s_c_name, t2.f_c_name, t1.s_id from t_official_supply_second_class t1\n" +
                    "right join t_official_supply_first_class t2\n" +
                    "on t1.s_id = t2.id";
    public static final String QUERY_SECOND_NAME_WITH_FIRST_BY_ID =
            "select s_c_name from t_official_supply_second_class where s_id=?";
    public static final String QUERY_SECOND_NAME_BY_ID =
            "select s_c_name from t_official_supply_second_class where id=?";
    public static final String QUERY_ALL_SECOND_CLASS_OPERATION_RECORD =
            "select t2.u_name, t3.s_c_name from t_official_supply_class_operation_record t1 \n" +
                    "left join t_user t2\n" +
                    "on t1.u_id=t2.id\n" +
                    "left join t_official_supply_second_class t3\n" +
                    "on t1.b_id=t3.id";
    public static final String QUERY_ALL_SECOND_CLASS_OPERATION_RECORD_BY_ID =
            "select t2.u_name, t3.s_c_name from t_official_supply_class_operation_record t1 \n" +
                    "left join t_user t2\n" +
                    "on t1.u_id=t2.id\n" +
                    "left join t_official_supply_second_class t3\n" +
                    "on t1.b_id=t3.id\n" +
                    "where t1.id=?";
    public static final String QUERY_ALL_SECOND_CLASS_ID_BY_FIRST_CLASS_ID =
            "select id from t_official_supply_second_class where s_id=?";
    public static final String INSERT_FIRST_CLASS =
            "insert into t_official_supply_first_class(f_c_name) values (?)";
    public static final String INSERT_SECOND_CLASS =
            "insert into t_official_supply_second_class(s_c_name, s_id) values (?, ?)";
    public static final String INSERT_SECOND_CLASS_OPERATION_RECORD =
            "insert into t_official_supply_class_operation_record(u_id, b_id, b_name) values (?, ?, ?)";
    public static final String DELETE_FIRST_CLASS_BY_ID =
            "delete from t_official_supply_first_class where id=?";
    public static final String DELETE_SECOND_CLASS_BY_FIRST_CLASS_ID =
            "delete from t_official_supply_second_class where s_id=?";
    public static final String DELETE_SECOND_CLASS_OPERATION_RECORD_BY_SECOND_CLASS_ID =
            "delete from t_official_supply_class_operation_record where b_id=?";
}
