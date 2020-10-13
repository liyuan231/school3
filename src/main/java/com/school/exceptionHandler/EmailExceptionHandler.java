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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EmailExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public EmailExceptionHandler() {
    }

    @ExceptionHandler({EmailVerificationCodeIllegalArgumentException.class})
    public String emailVerificationCodeIllegalArgumentException(HttpServletRequest request, Exception e) throws JsonProcessingException {
        this.logger.info("[" + request.getRemoteAddr() + "] ERROR " + e.getMessage());
        return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler({EmailVerificationCodeNullPointerException.class})
    public String emailVerificationCodeNullPointerException(HttpServletRequest request, Exception e) throws JsonProcessingException {
        this.logger.info("[" + request.getRemoteAddr() + "] ERROR " + e.getMessage());
        return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), e.getMessage());
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
