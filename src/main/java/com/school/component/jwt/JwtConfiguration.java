//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.school.component.jwt;

import com.school.model.User;
import com.school.model.Usertorole;
import com.school.service.impl.UserServiceImpl;
import com.school.service.impl.UserToRoleServiceImpl;
import com.school.utils.IpUtil;
import com.school.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EnableConfigurationProperties({JwtProperties.class})
@ConditionalOnProperty(
        prefix = "jwt.config",
        name = {"enabled"}
)
@Configuration
public class JwtConfiguration {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserToRoleServiceImpl userToRoleService;

    public JwtConfiguration() {
    }

    @Bean
    public JwtTokenGenerator jwtTokenGenerator(JwtProperties jwtProperties) {
        return new JwtTokenGenerator(new JwtPayloadBuilder(), jwtProperties);
    }

    @Bean
    public AuthenticationSuccessHandler jsonAuthenticationSuccessHandler(JwtTokenGenerator jwtTokenGenerator) {
        return (request, response, authentication) -> {
            Map<String, Object> map = new HashMap();
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            List<User> users = this.userService.querySelectiveLike((Integer) null, principal.getUsername(), (String) null, (String) null, (String) null, (String) null, (Integer) null, (String) null, (String) null, (Integer) null, (String) null, (String) null, (Integer) null, (String) null, (Integer) null, (Integer) null, (String) null, (String) null);
            User user = (User) users.get(0);
            List<Usertorole> usertoroles = this.userToRoleService.querySelective((Integer) null, user.getId(), (Integer) null);
            if (usertoroles.size() == 0) {
                throw new NullPointerException("该用户未设置角色!");
            } else {
                Usertorole usertorole = (Usertorole) usertoroles.get(0);
                String level = (String) request.getAttribute("level");
                if (level == null) {
                    level = request.getParameter("level");
                }
                if (level == null || !usertorole.getRoleid().toString().equals(level.trim())) {
                    String build = ResponseUtil.build(HttpStatus.BAD_GATEWAY.value(), "用户名登录错地方！", (Object) null);
                    ResponseUtil.printlnInfo(response, build);
                } else {
                    user.setLastloginip(request.getRemoteAddr());
                    user.setLastlogintime(LocalDateTime.now());
                    user.setLocation(IpUtil.retrieveCity(user.getLastloginip()));
                    user.setPassword((String) null);
                    this.userService.update(user);
                    JwtTokenPair jwtTokenPair = jwtTokenGenerator.jwtTokenPair(principal.getUsername(), principal.getAuthorities(), (Map) null);
                    map.put("access_token", jwtTokenPair.getAccessToken());
                    map.put("refresh_token", jwtTokenPair.getRefreshToken());
                    String buildx = ResponseUtil.build(HttpStatus.OK.value(), "登录成功！", map);
                    ResponseUtil.printlnInfo(response, buildx);
                }
            }
        };
    }

    @Bean
    public AuthenticationFailureHandler jsonAuthenticationFailureHandler() {
        return (request, response, exception) -> {
            String build = ResponseUtil.build(HttpStatus.UNAUTHORIZED.value(), exception.getMessage(), exception.getMessage());
            ResponseUtil.printlnInfo(response, build);
        };
    }
}
