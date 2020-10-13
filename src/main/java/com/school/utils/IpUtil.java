//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.school.utils;

import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.util.StringUtils;
import java.net.InetAddress;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class IpUtil {
    private static final String key = "P5EBZ-ITH3I-5A3GT-5QIXG-36QW5-O2FS7";
    private static final String url = "https://apis.map.qq.com/ws/location/v1/ip";

    public IpUtil() {
    }

    public static String retrieveCity(String ip) {
        String requestUrl = "https://apis.map.qq.com/ws/location/v1/ip?ip=" + ip + "&key=" + "P5EBZ-ITH3I-5A3GT-5QIXG-36QW5-O2FS7";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<JSONObject> responseEntity = restTemplate.getForEntity(requestUrl, JSONObject.class, new Object[0]);
        String city = null;

        try {
            city = ((JSONObject)responseEntity.getBody()).getJSONObject("result").getJSONObject("ad_info").getString("city");
        } catch (Exception var6) {
            return "ip无法定位！";
        }

        return city == null ? "ip无法定位" : city;
    }

    public static String retrieveIp(HttpServletRequest request) {
        String ip = null;

        try {
            ip = request.getHeader("x-forwarded-for");
            if (!isValidIp(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }

            if (!isValidIp(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }

            if (ip != null && !isValidIp(ip) && ip.equalsIgnoreCase("127.0.0.1")) {
                InetAddress inetAddress = InetAddress.getLocalHost();
                ip = inetAddress.getHostAddress();
            }

            if (ip != null && ip.length() > 15) {
                ip = ip.substring(0, ip.indexOf(","));
            }

            if (StringUtils.isEmptyOrWhitespaceOnly(ip)) {
                ip = request.getRemoteAddr();
            }
        } catch (Exception var3) {
            ip = "";
        }

        return ip;
    }

    private static boolean isValidIp(String ip) {
        return !StringUtils.isEmptyOrWhitespaceOnly(ip) && !"unknown".equalsIgnoreCase(ip);
    }
}
