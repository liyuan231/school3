package com.school.component.limit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebFilter(urlPatterns = "/*")
public class RequestFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(RequestFilter.class);
    private static final long LIMIT_TIME_MILLIS = 60 * 60 * 1000;
    private static final int LIMIT_TIMES = 1000;
    private static final int MIN_SAFE_TIME = 200;
    //若用户在MIN_SAFE_TIME时间 内访问超过LIMIT_TIMES，则判定为机器人
    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        ServletContext context = filterConfig.getServletContext();
        Map<String, Long> limitedIpMap = (Map<String, Long>) context.getAttribute("limitedIpMap");
        filterLimitedIpMap(limitedIpMap);
        String ip = request.getRemoteHost();
        if (limitedIpMap.containsKey(ip)) {
            long limitedTime = limitedIpMap.get(ip) - System.currentTimeMillis();
            request.setAttribute("remainingTime", (limitedTime / 1000) + (limitedTime % 1000 > 0 ? 1 : 0));
            logger.warn(ip + "->访问过于频繁！");
            request.getRequestDispatcher("/error/requestLimit").forward(request, response);
            return;
        }
        Map<String, Long[]> ipMap = (Map<String, Long[]>) context.getAttribute("ipMap");
        if (ipMap.containsKey(ip)) {
            Long[] ipInfos = ipMap.get(ip);
            ipInfos[0] = ipInfos[0] + 1;
//            logger.info(ip + "->第" + ipInfos[0] + "次访问！");
            if (ipInfos[0] > LIMIT_TIMES) {
                Long ipAccessTime = ipInfos[1];
                long currentTimeMillis = System.currentTimeMillis();
                if (currentTimeMillis - ipAccessTime <= MIN_SAFE_TIME) {
                    limitedIpMap.put(ip, currentTimeMillis + LIMIT_TIME_MILLIS);
                    request.setAttribute("remainingTime", LIMIT_TIME_MILLIS / 1000);
                    logger.error("ip访问过于频繁:" + ip);
                    request.getRequestDispatcher("/error/requestLimit").forward(request, response);
                    return;
                } else {
                    initIpVisitsNumber(ipMap, ip);
                }
            }
        } else {
            initIpVisitsNumber(ipMap, ip);
        }
        context.setAttribute("ipMap", ipMap);
        filterChain.doFilter(request, response);

    }

    private void initIpVisitsNumber(Map<String, Long[]> ipMap, String ip) {
        Long[] ipInfo = new Long[2];
        ipInfo[0] = 0L;//记录当前ip的访问次数
        ipInfo[1] = System.currentTimeMillis();
        ipMap.put(ip, ipInfo);
    }

    private void filterLimitedIpMap(Map<String, Long> map) {
        if (map == null) {
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        for (Map.Entry<String, Long> entry : map.entrySet()) {
            Long expireTimeMillis = entry.getValue();
            if (expireTimeMillis <= currentTimeMillis) {
                map.remove(entry.getKey());
            }
        }
    }
}
