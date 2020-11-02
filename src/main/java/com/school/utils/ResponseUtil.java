//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.school.utils;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {
    public ResponseUtil() {
    }

    public static String build(int status, String message) {
        return build(status, message, (Object) null);
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

    public static void export(File file, HttpServletResponse response) throws IOException {
        String name = file.getName();
//        String suffix = file.getName().split("\\.")[1];
        response.setHeader("Content-disposition", "attachment;filename=" + new String(name.getBytes(StandardCharsets.UTF_8), "ISO8859-1"));
        response.setContentType("application/x-download");
        response.setCharacterEncoding("UTF-8");
        try (BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream());) {
            try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));) {
                byte[] buf = new byte[1024 * 2];
                int len;
                while ((len=bufferedInputStream.read(buf, 0, buf.length)) != -1) {
                    outputStream.write(buf,0,len);
                }
            }
        }
    }

}
