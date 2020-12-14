package xyz.xuanlee.shiro_go.service_impl.user_service_impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import xyz.xuanlee.shiro_go.DO.OpResponseInfo;
import xyz.xuanlee.shiro_go.DO.user.User;
import xyz.xuanlee.shiro_go.constant.DateTimeFormatPattern;
import xyz.xuanlee.shiro_go.constant.InteractInfo;
import xyz.xuanlee.shiro_go.service.user_service.UserManageService;
import xyz.xuanlee.shiro_go.sql.UserSQL;
import xyz.xuanlee.shiro_go.util.CommonUtil;
import xyz.xuanlee.shiro_go.util.JDBCUtil;

import java.lang.reflect.Constructor;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class UserManageServiceImpl implements UserManageService {

    private static final String ENTITY_NAME = "用户";

    @Override
    public OpResponseInfo signup(String username, String password) {
        String code;
        if (username == null || password == null) {
            code = "100";
            return new OpResponseInfo(code, InteractInfo.REGISTER_INTERACT_INFO.get(code));
        }
        if ((username.length() < 6 || username.length() > 20) ||
                password.length() < 6 || password.length() > 20) {
            code = "200";
            return new OpResponseInfo(code, InteractInfo.REGISTER_INTERACT_INFO.get(code));
        }

        try {
            Constructor<Integer> integerConstructor = Integer.class.getConstructor(int.class);
            Integer rec = JDBCUtil.executeQueryStatement(integerConstructor,
                    UserSQL.QUERY_USER_QTY_BY_USERNAME,
                    username);

            assert rec != null;
            if (rec == 1) {
                code = "300";
            } else {
                if (JDBCUtil.executeModifyStatement(UserSQL.INSERT_USER, username, password)) {
                    code = "1";
                } else {
                    code = "400";
                }
            }
            return new OpResponseInfo(code, InteractInfo.REGISTER_INTERACT_INFO.get(code));
        } catch (NoSuchMethodException e) {
            code = "-1";
            e.printStackTrace();
            return new OpResponseInfo(code, InteractInfo.GENERAL_ERROR_INFO.get(code));
        }
    }

    @Override
    public OpResponseInfo retrieveAllUserInfo() {
        String code;

        try {
            Constructor<User> userConstructor =
                    User.class.getConstructor(String.class, String.class, String.class);
            List<User> users = JDBCUtil.executeQueryBatchStatement(userConstructor, UserSQL.QUERY_USERS_INFO);
            JSONArray usersInfo = new JSONArray();
            for (User user: users) {
                JSONObject tmpObject = new JSONObject();
                tmpObject.put("username", user.getUsername());
                tmpObject.put("password", user.getPassword());

                String departmentName = user.getBelongDepartment().getDepartmentName();
                tmpObject.put("department_name", departmentName == null ? "" : departmentName);
                usersInfo.add(tmpObject);
            }
            code = "1";
            JSONObject json = CommonUtil.groupRespData(code, InteractInfo.GENERAL_MODIFY_INFO.get(code));
            json.put("user_list", usersInfo);
            return new OpResponseInfo(code, json.toJSONString());
        } catch (NoSuchMethodException e) {
            code = "-1";
            e.printStackTrace();
            return new OpResponseInfo(code, InteractInfo.GENERAL_ERROR_INFO.get(code));
        }
    }

    @Override
    public OpResponseInfo retrieveUserInfoByLoginTime(String startTime, String endTime) {
        String code;
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(DateTimeFormatPattern.GENERAL_PATTERN);
        if (startTime == null || endTime == null) {
            code = "400";
            String reason = "请输入开始时间和结束时间";
            return new OpResponseInfo(code,
                    String.format(InteractInfo.GENERAL_MODIFY_INFO.get(code), ENTITY_NAME, reason));
        }

        try {
            LocalDateTime start = LocalDateTime.parse(startTime,
                    DateTimeFormatter.ofPattern(DateTimeFormatPattern.GENERAL_PATTERN));
            LocalDateTime end = LocalDateTime.parse(endTime,
                    DateTimeFormatter.ofPattern(DateTimeFormatPattern.GENERAL_PATTERN));

            Constructor<User> userConstructor =
                    User.class.getConstructor(String.class, String.class);
            List<User> users = JDBCUtil.executeQueryBatchStatement(userConstructor,
                    UserSQL.QUERY_USER_BY_LOG_TIME, startTime, endTime);
            JSONArray usersInfo = new JSONArray();
            for (User user: users) {
                JSONObject tmpObject = new JSONObject();
                tmpObject.put("username", user.getUsername());
                tmpObject.put("password", user.getPassword());
                usersInfo.add(tmpObject);
            }
            code = "1";
            JSONObject json = CommonUtil.groupRespData(code, InteractInfo.GENERAL_MODIFY_INFO.get(code));
            json.put("user_list", usersInfo);
            return new OpResponseInfo(code, json.toJSONString());
        } catch (DateTimeParseException e) {
            code = "400";
            String reason = "请按正确的时间格式输入（例-1999-11-22 12:22:22）";
            e.printStackTrace();
            return new OpResponseInfo(code,
                    String.format(InteractInfo.GENERAL_MODIFY_INFO.get(code), ENTITY_NAME, reason));
        } catch (NoSuchMethodException e) {
            code = "-1";
            e.printStackTrace();
            return new OpResponseInfo(code, InteractInfo.GENERAL_ERROR_INFO.get(code));
        }
    }

    @Override
    public OpResponseInfo retrieveUserInfoByIdAndUsername(String id, String username) {
        String code;

        try {
            Constructor<User> userConstructor =
                    User.class.getConstructor(String.class, String.class, String.class);
            List<User> users = JDBCUtil.executeQueryBatchStatement(userConstructor,
                    UserSQL.QUERY_USER_BY_ID_AND_USERNAME, "%" + id + "%", "%" + username + "%");
            JSONArray usersInfo = new JSONArray();
            for (User user: users) {
                JSONObject tmpObject = new JSONObject();
                tmpObject.put("username", user.getUsername());
                tmpObject.put("password", user.getPassword());
                tmpObject.put("department_name", user.getBelongDepartment().getDepartmentName());
                usersInfo.add(tmpObject);
            }
            code = "1";
            JSONObject json = CommonUtil.groupRespData(code, InteractInfo.GENERAL_MODIFY_INFO.get(code));
            json.put("user_list", usersInfo);
            return new OpResponseInfo(code, json.toJSONString());
        } catch (NoSuchMethodException e) {
            code = "-1";
            e.printStackTrace();
            return new OpResponseInfo(code, InteractInfo.GENERAL_ERROR_INFO.get(code));
        }
    }

    @Override
    public OpResponseInfo modifyUsernameById(Long id, String newUsername) {
        String code;

        if (id == null || newUsername == null) {
            code = "200";
            String reason = "请输入要修改的用户id和新用户名";
            return new OpResponseInfo(code,
                    String.format(InteractInfo.GENERAL_MODIFY_INFO.get(code), ENTITY_NAME, reason));
        }

        if (JDBCUtil.executeModifyStatement(UserSQL.UPDATE_USER_USERNAME, newUsername, id)) {
            code = "1";
            return new OpResponseInfo(code, InteractInfo.GENERAL_MODIFY_INFO.get(code));
        } else {
            code = "100";
            return new OpResponseInfo(code, InteractInfo.MODIFY_INTERACT_INFO.get(code));
        }
    }

    @Override
    public OpResponseInfo modifyUserDepartmentById(Long id, Long departmentId) {
        String code;

        if (id == null || departmentId == null) {
            code = "200";
            String reason = "请输入要修改的用户id和新的部门id";
            return new OpResponseInfo(code,
                    String.format(InteractInfo.GENERAL_MODIFY_INFO.get(code), ENTITY_NAME, reason));
        }

        try {
            Constructor<Integer> integerConstructor = Integer.class.getConstructor(int.class);
            Integer rec = JDBCUtil.executeQueryStatement(integerConstructor,
                    UserSQL.QUERY_DEPARTMENT_IS_EXIST,
                    departmentId);

            assert rec != null;
            if (rec == 1) {
                if (JDBCUtil.executeModifyStatement(UserSQL.UPDATE_USER_DEPARTMENT_ID_BY_ID, departmentId, id)) {
                    code = "1";
                } else {
                    code = "200";
                    String reason = "修改的用户ID不存在";
                    return new OpResponseInfo(code,
                            String.format(InteractInfo.GENERAL_MODIFY_INFO.get(code), ENTITY_NAME, reason));
                }
            } else {
                code = "200";
                String reason = "提供的部门ID不存在";
                return new OpResponseInfo(code,
                        String.format(InteractInfo.GENERAL_MODIFY_INFO.get(code), ENTITY_NAME, reason));
            }
            return new OpResponseInfo(code, InteractInfo.GENERAL_MODIFY_INFO.get(code));
        } catch (NoSuchMethodException e) {
            code = "-1";
            e.printStackTrace();
            return new OpResponseInfo(code, InteractInfo.GENERAL_ERROR_INFO.get(code));
        }
    }

    @Override
    public OpResponseInfo deleteUserById(Long id) {
        String code;

        if (id == null) {
            code = "300";
            String reason = "请输入要删除的用户id";
            return new OpResponseInfo(code,
                    String.format(InteractInfo.GENERAL_MODIFY_INFO.get(code), ENTITY_NAME, reason));
        }

        if (JDBCUtil.executeModifyStatement(UserSQL.DELTE_USER, id)) {
            code = "1";
            return new OpResponseInfo(code, InteractInfo.GENERAL_MODIFY_INFO.get(code));
        } else {
            code = "100";
            return new OpResponseInfo(code, InteractInfo.MODIFY_INTERACT_INFO.get(code));
        }
    }
}
