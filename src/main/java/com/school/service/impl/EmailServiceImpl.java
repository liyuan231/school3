//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.school.service.impl;

import com.school.exception.EmailVerificationCodeIllegalArgumentException;
import com.school.exception.EmailVerificationCodeNullPointerException;
import com.school.exception.UserNotFoundException;
import com.school.utils.AssertUtil;
import net.jodah.expiringmap.ExpiringMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class EmailServiceImpl {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ExpiringMap<String, String> usernameToCodeMap = ExpiringMap.builder().variableExpiration().build();
    @Autowired
    private JavaMailSenderImpl javaMailSenderImpl;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private MailProperties mailProperties;

    public EmailServiceImpl() {
    }

    public void sendVerificationCode(String subject, String message, String username, Integer duration, TimeUnit timeUnit) throws MailException{
        String code = this.generateCode();
        this.usernameToCodeMap.put(username, code, (long)duration, timeUnit);
        StringBuilder context = new StringBuilder();
        context.append(message).append(code);
        this.send(username, subject, context.toString());
    }

    private String generateCode() {
        StringBuilder code = new StringBuilder();
        for(int i = 0; i < 4; ++i) {
            code.append((int)(Math.random() * 10.0D));
        }

        return code.toString();
    }

    public void resetPassword(String username, String code, String newPassword) throws EmailVerificationCodeNullPointerException, EmailVerificationCodeIllegalArgumentException, UserNotFoundException {
        String codeInCache = (String)this.usernameToCodeMap.get(username);
        AssertUtil.emailVerificationCodeNotNull(codeInCache, "验证码不存在！");
        AssertUtil.emailVerificationCodeEquals(code.trim().equals(codeInCache), "验证码错误！");
        this.userService.update((Integer)null, username, newPassword, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null, (LocalDateTime)null, (Boolean)null, (String)null, (Integer)null, (String)null);
        this.usernameToCodeMap.remove(username);
    }

    public void modifySystemEmail(String username, String mailAuthorizationCode) {
        this.mailProperties.setUsername(username);
        this.mailProperties.setPassword(mailAuthorizationCode);
        this.javaMailSenderImpl = new JavaMailSenderImpl();
        this.applyProperties(this.mailProperties, this.javaMailSenderImpl);
    }

    private void applyProperties(MailProperties properties, JavaMailSenderImpl sender) {
        sender.setHost(properties.getHost());
        if (properties.getPort() != null) {
            sender.setPort(properties.getPort());
        }

        sender.setUsername(properties.getUsername());
        sender.setPassword(properties.getPassword());
        sender.setProtocol(properties.getProtocol());
        if (properties.getDefaultEncoding() != null) {
            sender.setDefaultEncoding(properties.getDefaultEncoding().name());
        }

        if (!properties.getProperties().isEmpty()) {
            sender.setJavaMailProperties(this.asProperties(properties.getProperties()));
        }

    }

    private Properties asProperties(Map<String, String> source) {
        Properties properties = new Properties();
        properties.putAll(source);
        return properties;
    }

    public void send(String to, String subject, String context) throws MailException {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(this.mailProperties.getUsername());
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(context);
        simpleMailMessage.setTo(to);
        this.javaMailSenderImpl.send(simpleMailMessage);
    }

    public EmailInfo retrieveSystemEmailInfo() {
        String username = this.mailProperties.getUsername();
        String password = this.mailProperties.getPassword();
        return new EmailInfo(username,password);
    }
    public  static class EmailInfo{
        public EmailInfo() {
        }

        public EmailInfo(String username, String password) {
            this.username = username;
            this.password = password;
        }

        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
