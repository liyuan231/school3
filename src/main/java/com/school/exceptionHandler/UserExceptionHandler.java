//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.school.exceptionHandler;

import com.school.exception.UserNotCorrectException;
import com.school.exception.UserNotFoundException;
import com.school.exception.UsernameAlreadyExistException;
import com.school.utils.ResponseUtil;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public UserExceptionHandler() {
    }

    @ExceptionHandler({UserNotFoundException.class})
    public String userNotFoundException(HttpServletRequest request, UserNotFoundException e) {
        this.logger.info("[" + request.getRemoteAddr() + "] ERROR " + e.getMessage());
        return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler({UsernameAlreadyExistException.class})
    public String usernameAlreadyExistException(HttpServletRequest request, UsernameAlreadyExistException e) {
        this.logger.info("[" + request.getRemoteAddr() + "] ERROR " + e.getMessage());
        return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler({UserNotCorrectException.class})
    public String userNotCorrectException(HttpServletRequest request, UserNotCorrectException e) {
        this.logger.info("[" + request.getRemoteAddr() + "] ERROR " + e.getMessage());
        return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
}
