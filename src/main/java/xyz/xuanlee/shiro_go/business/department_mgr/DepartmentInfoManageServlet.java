package xyz.xuanlee.shiro_go.business.department_mgr;

import xyz.xuanlee.shiro_go.DO.OpResponseInfo;
import xyz.xuanlee.shiro_go.service.department_service.DepartmentManageService;
import xyz.xuanlee.shiro_go.service_impl.department_service_impl.DepartmentManageServiceImpl;
import xyz.xuanlee.shiro_go.util.CommonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "Department information manage", urlPatterns = "/department/*")
public class DepartmentInfoManageServlet extends HttpServlet {

    private DepartmentManageService departmentManageService;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String requestURI = req.getRequestURI();
        String regex = "\\/department\\/(\\w+)\\??";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(requestURI);

        if (matcher.find()) {
            String dispatchURI = matcher.group(1);
            switch (dispatchURI) {
                case "createDepartment":
                    doGetCreateNewDepartment(req, resp);
                default:
                    break;
            }
        }
    }

    private void doGetCreateNewDepartment(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String departmentName = req.getParameter("departmentName");

        OpResponseInfo info = departmentManageService.createDepartment(departmentName);

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("json/application");
        PrintWriter printWriter = resp.getWriter();

        String code = info.getInfoCode();
        String message = info.getInfo();

        printWriter.println(CommonUtil.groupRespData(code, message).toJSONString());
    }

    @Override
    public void init() throws ServletException {
        departmentManageService = new DepartmentManageServiceImpl();
    }
}
