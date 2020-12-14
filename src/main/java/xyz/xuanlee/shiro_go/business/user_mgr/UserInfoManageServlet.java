package xyz.xuanlee.shiro_go.business.user_mgr;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import xyz.xuanlee.shiro_go.DO.OpResponseInfo;
import xyz.xuanlee.shiro_go.constant.InteractInfo;
import xyz.xuanlee.shiro_go.service.user_service.UserManageService;
import xyz.xuanlee.shiro_go.service_impl.user_service_impl.UserManageServiceImpl;
import xyz.xuanlee.shiro_go.util.CommonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "User information manage", urlPatterns = "/user/*")
public class UserInfoManageServlet extends HttpServlet {

    private UserManageService userManageService;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        String regex = "\\/user\\/(\\w+)\\??";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(requestURI);

        if (matcher.find()) {
            String dispatchURI = matcher.group(1);
            switch (dispatchURI) {
                case "signup":
                    doGetSignup(req, resp);
                    break;
                case "selectByDate":
                    doGetSelectByDate(req, resp);
                    break;
                case "selectByIdAndUsername":
                    doGetSelectByIdAndUsername(req, resp);
                    break;
                case "userList":
                    doGetUserList(req, resp);
                    break;
                case "updateById":
                    doGetUpdateByID(req, resp);
                    break;
                case "updateDepartmentById":
                    doGetUpdateDepartmentById(req, resp);
                    break;
                case "updateUserInfoByUsername":
                    doPostUpdateUserInfoByUsername(req, resp);
                    break;
                case "deleteById":
                    doPostDeleteByID(req, resp);
                    break;
                case "deleteByUser":
                    doPostDeleteByUsername(req, resp);
                    break;
                default:
                    break;
            }
        }
    }

    private void doGetSignup(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        OpResponseInfo info = userManageService.signup(username, password);

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("json/application");
        PrintWriter printWriter = resp.getWriter();

        String code = info.getInfoCode();
        String message = info.getInfo();

        printWriter.println(CommonUtil.groupRespData(code, message).toJSONString());
    }

    private void doGetUserList(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        JSONObject json;

        OpResponseInfo info = userManageService.retrieveAllUserInfo();

        String code = info.getInfoCode();
        String message = info.getInfo();
        json = CommonUtil.groupRespData(code, message);

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("json/application");
        PrintWriter printWriter = resp.getWriter();

        printWriter.println(json.toJSONString());
    }

    private void doGetSelectByDate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String startTime = req.getParameter("startDate");
        String endTime = req.getParameter("endDate");

        JSONObject json;

        OpResponseInfo info = userManageService.retrieveUserInfoByLoginTime(startTime, endTime);

        String code = info.getInfoCode();
        String message = info.getInfo();
        json = CommonUtil.groupRespData(code, message);

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("json/application");
        PrintWriter printWriter = resp.getWriter();

        printWriter.println(json.toJSONString());
    }

    private void doGetSelectByIdAndUsername(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        String username = req.getParameter("username");

        JSONObject json;

        OpResponseInfo info = userManageService.retrieveUserInfoByIdAndUsername(id, username);

        String code = info.getInfoCode();
        String message = info.getInfo();
        json = CommonUtil.groupRespData(code, message);

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("json/application");
        PrintWriter printWriter = resp.getWriter();

        printWriter.println(json.toJSONString());
    }

    private void doGetUpdateByID(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject json;

        try {
            Long id = Long.parseLong(req.getParameter("id"));
            String newUsername = req.getParameter("username");

            OpResponseInfo info = userManageService.modifyUsernameById(id, newUsername);

            json = CommonUtil.groupRespData(info.getInfoCode(), info.getInfo());
        } catch (NumberFormatException e) {
            String code = "-3";
            e.printStackTrace();
            json = CommonUtil.groupRespData(code,
                    String.format(InteractInfo.GENERAL_ERROR_INFO.get(code), "数字ID"));
        }

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("json/application");
        PrintWriter printWriter = resp.getWriter();
        printWriter.println(json.toJSONString());
    }

    private void doGetUpdateDepartmentById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject json;

        try {
            Long dId = Long.parseLong(req.getParameter("dId"));
            Long id = Long.parseLong(req.getParameter("id"));

            OpResponseInfo info = userManageService.modifyUserDepartmentById(id, dId);

            json = CommonUtil.groupRespData(info.getInfoCode(), info.getInfo());
        } catch (NumberFormatException e) {
            String code = "-3";
            e.printStackTrace();
            json = CommonUtil.groupRespData(code,
                    String.format(InteractInfo.GENERAL_ERROR_INFO.get(code), "数字ID"));
        }

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("json/application");
        PrintWriter printWriter = resp.getWriter();
        printWriter.println(json.toJSONString());
    }

    private void doPostUpdateUserInfoByUsername(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject json;

        try {
            req.setCharacterEncoding("UTF-8");
            JSONObject reqData = CommonUtil.getPostRequestData(req.getReader());
            String oldUsername = reqData.getString("oldUsername");
            String newUsername = reqData.getString("newUsername");
            String newPassword = reqData.getString("newPassword");

            OpResponseInfo info = userManageService.modifyUserInfoByUsername(oldUsername, newUsername, newPassword);

            json = CommonUtil.groupRespData(info.getInfoCode(), info.getInfo());
        } catch (JSONException e) {
            String code = "-4";
            e.printStackTrace();
            json = CommonUtil.groupRespData(code,
                    String.format(InteractInfo.GENERAL_ERROR_INFO.get(code), "数字ID"));
        }

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("json/application");
        PrintWriter printWriter = resp.getWriter();
        printWriter.println(json.toJSONString());
    }

    private void doPostDeleteByID(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        JSONObject json;

        try {
            Long id = Long.parseLong(req.getParameter("id"));

            OpResponseInfo info = userManageService.deleteUserById(id);

            json = CommonUtil.groupRespData(info.getInfoCode(), info.getInfo());
        } catch (NumberFormatException e) {
            String code = "-3";
            e.printStackTrace();
            json = CommonUtil.groupRespData(code,
                    String.format(InteractInfo.GENERAL_ERROR_INFO.get(code), "数字ID"));
        }

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("json/application");
        PrintWriter printWriter = resp.getWriter();
        printWriter.println(json.toJSONString());
    }

    private void doPostDeleteByUsername(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject json;

        try {
            req.setCharacterEncoding("UTF-8");
            JSONObject reqData = CommonUtil.getPostRequestData(req.getReader());
            JSONArray tmpArr = reqData.getJSONArray("usernames");
            String[] users = new String[tmpArr.toArray().length];
            tmpArr.toArray(users);

            OpResponseInfo info = userManageService.deleteUserByUsername(users);

            json = CommonUtil.groupRespData(info.getInfoCode(), info.getInfo());
        } catch (JSONException e) {
            String code = "-4";
            e.printStackTrace();
            json = CommonUtil.groupRespData(code,
                    String.format(InteractInfo.GENERAL_ERROR_INFO.get(code), "数字ID"));
        }

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("json/application");
        PrintWriter printWriter = resp.getWriter();
        printWriter.println(json.toJSONString());
    }

    @Override
    public void init() throws ServletException {
        userManageService = new UserManageServiceImpl();
    }
}
