//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.school.configuration;

import com.school.component.security.JsonUsernamePasswordAuthenticationFilter;
import com.school.component.security.JwtAuthenticationFilter;
import com.school.component.security.SimpleAccessDeniedHandler;
import com.school.component.security.SimpleAuthenticationEntryPoint;
import com.school.component.security.SimpleLogoutSuccessHandler;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer.AuthorizedUrl;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        jsr250Enabled = true,
        securedEnabled = true
)
@ConditionalOnWebApplication(
        type = Type.SERVLET
)
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final String LOGIN_PROCESSING_URL = "/api/login";
    @Autowired
    AuthenticationFailureHandler jsonLoginFailureHandler;
    @Autowired
    AuthenticationSuccessHandler jsonLoginSuccessHandler;
    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;

    public SpringSecurityConfiguration() {
    }

    @Bean
    @Primary
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    protected void configure(HttpSecurity http) throws Exception {
        ((HttpSecurity)((HttpSecurity)((AuthorizedUrl)((HttpSecurity)((HttpSecurity)((HttpSecurity)http.csrf().disable()).cors(Customizer.withDefaults()).sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()).exceptionHandling().authenticationEntryPoint(new SimpleAuthenticationEntryPoint()).accessDeniedHandler(new SimpleAccessDeniedHandler()).and()).authorizeRequests().anyRequest()).permitAll().and()).formLogin().loginPage("/login.html").and()).logout().logoutSuccessHandler(new SimpleLogoutSuccessHandler());
        http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterAt(this.jsonUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordAuthenticationFilter() throws Exception {
        JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordAuthenticationFilter = new JsonUsernamePasswordAuthenticationFilter();
        jsonUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(this.jsonLoginSuccessHandler);
        jsonUsernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(this.jsonLoginFailureHandler);
        jsonUsernamePasswordAuthenticationFilter.setAuthenticationManager(this.authenticationManagerBean());
        jsonUsernamePasswordAuthenticationFilter.setFilterProcessesUrl("/api/login");
        return jsonUsernamePasswordAuthenticationFilter;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
        corsConfiguration.setAllowedMethods(Arrays.asList("*"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
