//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.school.component.security;

import com.school.exception.JwtExpiredAuthenticationException;
import com.school.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SimpleAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public SimpleAuthenticationEntryPoint() {
    }

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        logger.warn("权限不足！" + authException.getMessage());
        if(authException instanceof JwtExpiredAuthenticationException){
            PrintWriter writer = response.getWriter();
            response.setContentType("application/json;charset=utf-8");
            String build = ResponseUtil.build(HttpStatus.UNAUTHORIZED.value(), authException.getMessage());
            writer.write(build);
        }
    }
}
