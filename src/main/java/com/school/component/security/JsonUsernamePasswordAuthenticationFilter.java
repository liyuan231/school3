//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.school.component.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JsonUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public JsonUsernamePasswordAuthenticationFilter() {
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String contentType = request.getContentType().toLowerCase();
        if (!contentType.equals("application/json") && !contentType.equals("application/json;charset=utf-8")) {
            return super.attemptAuthentication(request, response);
        } else {
            String username = "";
            String password = "";
            String level = "2";//默认未用户登录
            ObjectMapper objectMapper = new ObjectMapper();
            UsernamePasswordAuthenticationToken authenticationToken = null;

            try {
                ServletInputStream inputStream = request.getInputStream();

                try {
                    JsonNode body = objectMapper.readTree(inputStream);
                    JsonNode usernameJsonNode = body.get("username");
                    if (usernameJsonNode != null) {
                        username = usernameJsonNode.asText();
                    }

                    JsonNode passwordJsonNode = body.get("password");
                    if (passwordJsonNode != null) {
                        password = passwordJsonNode.asText();
                    }
                    JsonNode levelJsonNode = body.get("level");
                    if (levelJsonNode != null) {
                        level = levelJsonNode.asText();
                    }
                    request.setAttribute("level", level);

                    authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
                } catch (Throwable var18) {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (Throwable var17) {
                            var18.addSuppressed(var17);
                        }
                    }

                    throw var18;
                }

                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException var19) {
            } finally {
                this.setDetails(request, authenticationToken);
                return this.getAuthenticationManager().authenticate(authenticationToken);
            }
        }
    }
}
