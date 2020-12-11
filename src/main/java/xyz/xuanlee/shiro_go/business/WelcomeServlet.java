package xyz.xuanlee.shiro_go.business;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Welcome", urlPatterns = "/hi")
public class WelcomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject data = new JSONObject();
        JSONArray subData = new JSONArray();
        JSONObject sd1 = new JSONObject();
        sd1.put("id", 555);
        sd1.put("name", "hi");
        JSONObject sd2 = new JSONObject();
        sd2.put("id", 444);
        sd2.put("name", "nihao");
        JSONObject sd3 = new JSONObject();
        sd3.put("id", 333);
        sd3.put("name", "woshi");
        JSONObject sd4 = new JSONObject();
        sd4.put("id", 222);
        sd4.put("name", "zhende");
        JSONObject sd5 = new JSONObject();
        sd5.put("id", 111);
        sd5.put("name", "haoren");
        subData.add(sd1);
        subData.add(sd2);
        subData.add(sd3);
        subData.add(sd4);
        subData.add(sd5);
        data.put("subData", subData);

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("json/application");
        resp.getWriter().println(data);
    }
}
