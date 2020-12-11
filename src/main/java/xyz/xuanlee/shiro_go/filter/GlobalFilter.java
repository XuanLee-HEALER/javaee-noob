package xyz.xuanlee.shiro_go.filter;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebFilter(filterName = "Global Filter", urlPatterns = "/*")
public class GlobalFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest actualReq = (HttpServletRequest) servletRequest;
        HttpServletResponse actualResp = (HttpServletResponse) servletResponse;
        String reqUrl = actualReq.getRequestURI();
        Pattern pattern = Pattern.compile("\\/shiro_go_war_exploded\\/(\\S*)\\??");
        Matcher matcher = pattern.matcher(reqUrl);

        JSONObject json = new JSONObject();

        actualResp.setCharacterEncoding("UTF-8");
//        actualResp.setContentType("application/json");
        PrintWriter writer = actualResp.getWriter();

        if (matcher.find()) {
            switch (matcher.group(1)) {
                case "":
                case "user/login":
                    filterChain.doFilter(servletRequest, servletResponse);
                    break;
                default:
                    filterChain.doFilter(servletRequest, servletResponse);
//                    if (actualReq.getSession().getAttribute("username") == null) {
//                        OpResponseInfo respInfo =
//                                new OpResponseInfo("-2", InteractInfo.GENERAL_ERROR_INFO.get("-2"));
//                        writer.println(CommonUtil.groupRespData(respInfo.getInfoCode(), respInfo.getInfo()));
//                    } else {
//                        filterChain.doFilter(servletRequest, servletResponse);
//                    }
            }
        }
    }
}
