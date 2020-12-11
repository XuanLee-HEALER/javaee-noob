package xyz.xuanlee.shiro_go.service_impl.user_service_impl;

import xyz.xuanlee.shiro_go.DO.OpResponseInfo;
import xyz.xuanlee.shiro_go.DO.user.User;
import xyz.xuanlee.shiro_go.constant.InteractInfo;
import xyz.xuanlee.shiro_go.service.user_service.LoginService;
import xyz.xuanlee.shiro_go.sql.UserSQL;
import xyz.xuanlee.shiro_go.util.JDBCUtil;

import java.lang.reflect.Constructor;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoginServiceImpl implements LoginService {

    @Override
    public OpResponseInfo login(String username, String password) {
        String code;

        if (username == null || password == null) {
            code = "100";
            return new OpResponseInfo(code, InteractInfo.LOGIN_INTERACT_INFO.get(code));
        }

        try {
            // 返回用户名在数据库中出现的次数
            Constructor<Integer> integerConstructor = Integer.class.getConstructor(int.class);
            Integer rec = JDBCUtil.executeQueryStatement(integerConstructor,
                    UserSQL.QUERY_USER_QTY_BY_USERNAME,
                    username);

            assert rec != null;
            if (rec < 1) {
                code = "200";
                return new OpResponseInfo(code, InteractInfo.LOGIN_INTERACT_INFO.get(code));
            } else {
                Constructor<User> userConstructor = User.class.getConstructor(String.class,
                        String.class, LocalDateTime.class, String.class);
                User user = JDBCUtil.executeQueryStatement(userConstructor,
                        UserSQL.QUERY_USER_LOCK_BY_USERNAME,
                        username,
                        password);
                if (user == null) {
                    code = "300";
                    return new OpResponseInfo(code, InteractInfo.LOGIN_INTERACT_INFO.get(code));
                } else if (user.locked()) {
                    code = "400";
                    return new OpResponseInfo(code, InteractInfo.LOGIN_INTERACT_INFO.get(code));
                } else {
                    code = "1";
                    JDBCUtil.executeModifyStatement(UserSQL.UPDATE_USER_LOGIN_DATETIME, username);
                    return new OpResponseInfo(code, String.format(InteractInfo.LOGIN_INTERACT_INFO.get(code),
                            user.getLastLoginDatetime() == null
                                    ? "没有记录"
                                    : DateTimeFormatter
                                        .ofPattern("yyyy-MM-dd HH:mm:ss")
                                        .format(user.getLastLoginDatetime())));
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return new OpResponseInfo("-1", InteractInfo.GENERAL_ERROR_INFO.get("-1"));
        }
    }

    @Override
    public OpResponseInfo lock(String username) {
        String code = "-1";
        if (JDBCUtil.executeModifyStatement(UserSQL.LOCK_USER, username)) {
            code = "500";
            return new OpResponseInfo(code, InteractInfo.LOGIN_INTERACT_INFO.get(code));
        }
        return new OpResponseInfo(code, InteractInfo.GENERAL_ERROR_INFO.get(code));
    }
}
