package xyz.xuanlee.shiro_go.business.user_mgr;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import xyz.xuanlee.shiro_go.DO.OpResponseInfo;
import xyz.xuanlee.shiro_go.constant.InteractInfo;
import xyz.xuanlee.shiro_go.service.user_service.LoginService;
import xyz.xuanlee.shiro_go.service_impl.user_service_impl.LoginServiceImpl;
import xyz.xuanlee.shiro_go.util.CommonUtil;
import xyz.xuanlee.shiro_go.util.JDBCUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@WebServlet(name = "Login Servlet", urlPatterns = "/user/login")
public class LoginServlet extends HttpServlet {

    private LoginService loginService;
    private final Map<String, Integer> LOG_TIME = new HashMap<>(16);

    public LoginServlet() {
        JDBCUtil.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        OpResponseInfo info = loginService.login(username, password);

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("json/application");
        PrintWriter printWriter = resp.getWriter();

        String code = info.getInfoCode();
        String message = info.getInfo();

        if (code.equals("300")) {
            LOG_TIME.putIfAbsent(username, 0);
            LOG_TIME.computeIfPresent(username, (k, v) -> v + 1);

            if (LOG_TIME.get(username) == 3) {
                info = loginService.lock(username);
                code = info.getInfoCode();
                message = info.getInfo();
            } else if (LOG_TIME.get(username) > 3) {
                code = "500";
                message = InteractInfo.LOGIN_INTERACT_INFO.get(code);
            } else {
                code = "600";
                message = String.format(InteractInfo.LOGIN_INTERACT_INFO.get(code), LOG_TIME.get(username));
            }
        } else if (info.getInfoCode().equals("1")) {
            LOG_TIME.computeIfPresent(username, (k, v) -> 0);
            req.getSession().setAttribute("username", username);
        }

        printWriter.println(CommonUtil.groupRespData(code, message).toJSONString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = "";
        String message = "";

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("json/application");

        PrintWriter printWriter = resp.getWriter();

        try {
            req.setCharacterEncoding("UTF-8");
            JSONObject reqData = CommonUtil.getPostRequestData(req.getReader());

            String username = reqData.getString("username");
            String password = reqData.getString("password");

            OpResponseInfo info = loginService.login(username, password);

            code = info.getInfoCode();
            message = info.getInfo();

            if ("300".equals(code)) {
                LOG_TIME.putIfAbsent(username, 0);
                LOG_TIME.computeIfPresent(username, (k, v) -> v + 1);

                if (LOG_TIME.get(username) == 3) {
                    info = loginService.lock(username);
                    code = info.getInfoCode();
                    message = info.getInfo();
                } else if (LOG_TIME.get(username) > 3) {
                    code = "500";
                    message = InteractInfo.LOGIN_INTERACT_INFO.get(code);
                } else {
                    code = "600";
                    message = String.format(InteractInfo.LOGIN_INTERACT_INFO.get(code), LOG_TIME.get(username));
                }
            } else if (info.getInfoCode().equals("1")) {
                LOG_TIME.computeIfPresent(username, (k, v) -> 0);
                req.getSession().setAttribute("username", username);
            }
        } catch (JSONException e) {
            code = "-4";
            message = InteractInfo.GENERAL_ERROR_INFO.get(code);
        }

        printWriter.println(CommonUtil.groupRespData(code, message).toJSONString());
    }

    @Override
    public void destroy() {
        JDBCUtil.close();
    }

    @Override
    public void init() throws ServletException {
        loginService = new LoginServiceImpl();
    }
}
