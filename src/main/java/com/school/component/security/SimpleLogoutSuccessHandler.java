//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.school.component.security;

import com.school.utils.ResponseUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

public class SimpleLogoutSuccessHandler implements LogoutSuccessHandler {
    public SimpleLogoutSuccessHandler() {
    }

    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String build = ResponseUtil.build(HttpStatus.OK.value(), "退出成功！");
        ResponseUtil.printlnInfo(response, build);
    }
}
