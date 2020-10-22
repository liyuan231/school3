package com.school.component.limit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebListener
public class RequestLimitApplicationListener implements ServletContextListener {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("RequestLimitApplicationListener初始化成功！");
        ServletContext servletContext = sce.getServletContext();
        Map<String, Long[]> ipMap = new ConcurrentHashMap<>();
        servletContext.setAttribute("ipMap", ipMap);
        Map<String, Long> limitedIpMap = new HashMap<>();
        servletContext.setAttribute("limitedIpMap", limitedIpMap);
    }
}
