package xyz.xuanlee.shiro_go.business.official_supply_mgr;

import com.alibaba.fastjson.JSONObject;
import xyz.xuanlee.shiro_go.DO.OpResponseInfo;
import xyz.xuanlee.shiro_go.constant.InteractInfo;
import xyz.xuanlee.shiro_go.service.official_supply_service.OfficialSupplyClassManageService;
import xyz.xuanlee.shiro_go.service_impl.official_supply_service_impl.OfficialSupplyClassManageServiceImpl;
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

@WebServlet(name = "Official supply classes manage", urlPatterns = "/official_supply_class/*")
public class OfficialSupplyClassManageServlet extends HttpServlet {

    private OfficialSupplyClassManageService officialSupplyClassManageService;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        String regex = "\\/official_supply_class\\/(\\w+)\\??";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(requestURI);

        if (matcher.find()) {
            String dispatchURI = matcher.group(1);
            switch (dispatchURI) {
                case "createFirst":
                    doGetCreateFirst(req, resp);
                    break;
                case "createSecond":
                    doGetCreateSecond(req, resp);
                    break;
                case "firstClassList":
                    doGetFirstClassList(req, resp);
                    break;
                case "secondClassList":
                    doGetSecondClassList(req, resp);
                    break;
                case "secondClassOperationRecordList":
                    doGetSecondClassOperationRecordList(req, resp);
                    break;
                case "firstClassById":
                    doGetFirstClassById(req, resp);
                    break;
                case "secondClassById":
                    doGetSecondClassById(req, resp);
                    break;
                case "secondClassOperationRecordById":
                    doGetSecondClassOperationRecordById(req, resp);
                    break;
                case "deleteFirstClass":
                    doGetDeleteFirstClass(req, resp);
                    break;
                default:
                    break;
            }
        }
    }

    private void doGetCreateFirst(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String firstClassName = req.getParameter("firstClassName");

        OpResponseInfo info = officialSupplyClassManageService.createSupplyFirstClass(firstClassName);

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("json/application");
        PrintWriter printWriter = resp.getWriter();

        String code = info.getInfoCode();
        String message = info.getInfo();

        printWriter.println(CommonUtil.groupRespData(code, message).toJSONString());
    }

    private void doGetCreateSecond(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject json;

        try {
            Long superId = Long.parseLong(req.getParameter("firstClassId"));
            String secondClassName = req.getParameter("secondClassName");

            OpResponseInfo info = officialSupplyClassManageService.createSupplySecondClass(superId, secondClassName);

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

    private void doGetFirstClassList(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject json;

        OpResponseInfo info = officialSupplyClassManageService.retrieveAllFirstClass();

        String code = info.getInfoCode();
        String message = info.getInfo();
        json = CommonUtil.groupRespData(code, message);

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("json/application");
        PrintWriter printWriter = resp.getWriter();

        printWriter.println(json.toJSONString());
    }

    private void doGetSecondClassList(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject json;

        OpResponseInfo info = officialSupplyClassManageService.retrieveAllSecondClass();

        String code = info.getInfoCode();
        String message = info.getInfo();
        json = CommonUtil.groupRespData(code, message);

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("json/application");
        PrintWriter printWriter = resp.getWriter();

        printWriter.println(json.toJSONString());
    }

    private void doGetSecondClassOperationRecordList(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject json;

        OpResponseInfo info = officialSupplyClassManageService.retrieveAllSecondClassOperationRecord();

        String code = info.getInfoCode();
        String message = info.getInfo();
        json = CommonUtil.groupRespData(code, message);

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("json/application");
        PrintWriter printWriter = resp.getWriter();

        printWriter.println(json.toJSONString());
    }

    private void doGetFirstClassById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject json;

        try {
            Long firstClassId = Long.parseLong(req.getParameter("firstClassId"));

            OpResponseInfo info = officialSupplyClassManageService.retrieveFirstClassById(firstClassId);

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

    private void doGetSecondClassById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject json;

        try {
            Long secondClassId = Long.parseLong(req.getParameter("secondClassId"));

            OpResponseInfo info = officialSupplyClassManageService.retrieveSecondClassById(secondClassId);

            if ("1".equals(info.getInfoCode())) {
                String username = (String) req.getSession().getAttribute("username");
                OpResponseInfo recordRespInfo =
                    officialSupplyClassManageService.recordUserOperation(username, secondClassId);
                System.out.println(recordRespInfo);
            }

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

    private void doGetSecondClassOperationRecordById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject json;

        try {
            Long secondClassRecordId = Long.parseLong(req.getParameter("secondClassRecordId"));

            OpResponseInfo info = officialSupplyClassManageService.retrieveSecondClassOperationRecordById(secondClassRecordId);

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

    private void doGetDeleteFirstClass(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject json;

        if (CommonUtil.verifyIsLogin(req.getSession())) {
            try {
                Long firstClassId = Long.parseLong(req.getParameter("firstClassId"));

                OpResponseInfo info = officialSupplyClassManageService.deleteSupplyFirstClass(firstClassId);

                json = CommonUtil.groupRespData(info.getInfoCode(), info.getInfo());
            } catch (NumberFormatException e) {
                String code = "-3";
                e.printStackTrace();
                json = CommonUtil.groupRespData(code,
                        String.format(InteractInfo.GENERAL_ERROR_INFO.get(code), "数字ID"));
            }
        } else {
            String code = "-2";
            json = CommonUtil.groupRespData(code, InteractInfo.GENERAL_ERROR_INFO.get(code));
        }

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("json/application");
        PrintWriter printWriter = resp.getWriter();
        printWriter.println(json.toJSONString());
    }

    @Override
    public void init() throws ServletException {
        officialSupplyClassManageService = new OfficialSupplyClassManageServiceImpl();
    }
}
