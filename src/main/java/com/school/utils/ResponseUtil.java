//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.school.utils;

import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

public class ResponseUtil {
    public ResponseUtil() {
    }

    public static String build(int status, String message) {
        return build(status, message, (Object)null);
    }

    public static String build(int status, String message, Object data) {
        Map<String, Object> map = new HashMap();
        map.put("timestamp", System.currentTimeMillis());
        map.put("code", status);
        map.put("message", message);
        map.put("data", data);
        return JSONObject.toJSONString(map);
    }

    public static void printlnInfo(HttpServletResponse response, String build) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.println(build);
//        printWriter.flush();
        printWriter.close();
    }
}
