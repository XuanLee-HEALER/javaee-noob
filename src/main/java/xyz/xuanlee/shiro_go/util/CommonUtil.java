package xyz.xuanlee.shiro_go.util;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpSession;

public class CommonUtil {

    public static JSONObject groupRespData(String key, String value) {
        JSONObject json = new JSONObject();
        json.put("resp_code", key);
        json.put("resp_message", value);
        return json;
    }

    public static boolean verifyIsLogin(HttpSession session) {
        return session.getAttribute("username") != null;
    }
}
