//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.school.component.security;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.school.component.jwt.JwtTokenGenerator;
import com.school.exception.InvalidTokenException;
import com.school.model.Role;
import com.school.model.Roletoauthorities;
import com.school.service.impl.RoleServiceImpl;
import com.school.service.impl.RoleToAuthoritiesServiceImpl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String AUTHENTICATION_PREFIX = "Bearer ";
    private AuthenticationEntryPoint authenticationEntryPoint = new SimpleAuthenticationEntryPoint();
    @Autowired
    @Lazy
    private JwtTokenGenerator jwtTokenGenerator;
    @Autowired
    private RoleServiceImpl roleService;
    @Autowired
    private RoleToAuthoritiesServiceImpl roleToAuthoritiesService;

    public JwtAuthenticationFilter() {
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
        } else {
            String header = request.getHeader("Authorization");
            if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
                String jwtToken = header.substring("Bearer ".length());
                if (StringUtils.hasText(jwtToken)) {
                    try {
                        this.authenticationTokenHandle(jwtToken, request);
                    } catch (AuthenticationException var7) {
                        this.authenticationEntryPoint.commence(request, response, var7);
                    }
                } else {
                    this.authenticationEntryPoint.commence(request, response, new AuthenticationCredentialsNotFoundException("token is missing!"));
                }
            }

            filterChain.doFilter(request, response);
        }
    }

    private void authenticationTokenHandle(String jwtToken, HttpServletRequest request) throws InvalidTokenException {
        JSONObject jsonObject = null;

        try {
            jsonObject = this.jwtTokenGenerator.decodeAndVerify(jwtToken);
        } catch (IllegalArgumentException var12) {
            throw new InvalidTokenException("token解析错误！");
        }

        if (!Objects.nonNull(jsonObject)) {
            throw new BadCredentialsException("token is invalid!");
        } else {
            Collection<GrantedAuthority> roles_ = new ArrayList();
            JSONArray authoritiesJSONArray = jsonObject.getJSONArray("roles");
            Iterator iterator = authoritiesJSONArray.iterator();

            String next;
            while(iterator.hasNext()) {
                next = (String)iterator.next();
                Role role = this.roleService.querySelective((Integer)null, next);
                List<Roletoauthorities> byRoleId = this.roleToAuthoritiesService.querySelective((Integer)null, role.getId(), (String)null);
                Iterator var10 = byRoleId.iterator();

                while(var10.hasNext()) {
                    Roletoauthorities roletoauthorities = (Roletoauthorities)var10.next();
                    roles_.add(new SimpleGrantedAuthority(roletoauthorities.getAuthority()));
                }
                String finalNext = next;
                roles_.add(() -> {
                    return "ROLE_" + finalNext;
                });
            }

            next = jsonObject.getString("audience");
            User user = new User(next, "[PASSWORD]", roles_);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, (Object)null, roles_);
            usernamePasswordAuthenticationToken.setDetails((new WebAuthenticationDetailsSource()).buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
    }
}
