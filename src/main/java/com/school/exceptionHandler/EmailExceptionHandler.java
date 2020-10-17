//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.school.exceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.school.exception.EmailNotFoundException;
import com.school.exception.EmailVerificationCodeIllegalArgumentException;
import com.school.exception.EmailVerificationCodeNullPointerException;
import com.school.exception.EmailWrongFormatException;
import com.school.exception.UsernameNullPointerException;
import com.school.utils.ResponseUtil;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class EmailExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public EmailExceptionHandler() {
    }

    @ExceptionHandler({MailException.class})
    public String mailException(HttpServletRequest request, Exception e){
        this.logger.info("[" + request.getRemoteAddr() + "] ERROR " + e.getMessage());
        return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler({EmailVerificationCodeIllegalArgumentException.class})
    public String emailVerificationCodeIllegalArgumentException(HttpServletRequest request, Exception e) throws JsonProcessingException {
        this.logger.info("[" + request.getRemoteAddr() + "] ERROR " + e.getMessage());
//        return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), "验证码错误!");
    }

    @ExceptionHandler({EmailVerificationCodeNullPointerException.class})
    public String emailVerificationCodeNullPointerException(HttpServletRequest request, Exception e) throws JsonProcessingException {
        Throwable cause = e.getCause();
        String appendix = null;
        if(Objects.nonNull(cause)){
            appendix = cause.getMessage();
        }
        this.logger.info("[" + request.getRemoteAddr() + "] ERROR " + appendix+"->"+e.getMessage());
//        return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), appendix+"->"+e.getMessage());
        return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), "验证码错误!");

    }

    @ExceptionHandler({UsernameNullPointerException.class})
    public String usernameNullPointerException(HttpServletRequest request, Exception e) throws JsonProcessingException {
        this.logger.info("[" + request.getRemoteAddr() + "] ERROR " + e.getMessage());
        return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler({EmailWrongFormatException.class})
    public String emailWrongFormatException(HttpServletRequest request, Exception e) throws JsonProcessingException {
        this.logger.info("[" + request.getRemoteAddr() + "] ERROR " + e.getMessage());
        return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler({EmailNotFoundException.class})
    public String emailNotFoundException(HttpServletRequest request, Exception e) throws JsonProcessingException {
        this.logger.info("[" + request.getRemoteAddr() + "] ERROR " + e.getMessage());
        return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
}
