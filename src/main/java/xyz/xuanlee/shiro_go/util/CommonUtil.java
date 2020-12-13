package xyz.xuanlee.shiro_go.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpSession;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

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

    public static JSONObject getPostRequestData(Reader reader) {
        Scanner scanner = new Scanner(reader);
        StringBuilder builder = new StringBuilder();

        while (scanner.hasNextLine()) {
            builder.append(scanner.nextLine());
        }

        return JSON.parseObject(builder.toString());
    }
}
