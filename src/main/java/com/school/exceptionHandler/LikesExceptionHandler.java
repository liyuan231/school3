//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.school.exceptionHandler;

import com.school.exception.LikesAlreadyExistException;
import com.school.exception.LikesNotFoundException;
import com.school.exception.UserLikesNotCorrespondException;
import com.school.utils.ResponseUtil;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LikesExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public LikesExceptionHandler() {
    }

    @ExceptionHandler({UserLikesNotCorrespondException.class})
    public String userLikesNotCorrespondException(HttpServletRequest request, Exception e) {
        this.logger.info("[" + request.getRemoteAddr() + "] ERROR " + e.getMessage());
        return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler({LikesNotFoundException.class})
    public String likesNotFoundException(HttpServletRequest request, LikesNotFoundException e) {
        this.logger.info("[" + request.getRemoteAddr() + "] ERROR " + e.getMessage());
        return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler({LikesAlreadyExistException.class})
    public String likesAlreadyExistException(HttpServletRequest request, LikesAlreadyExistException e) {
        this.logger.info("[" + request.getRemoteAddr() + "] ERROR " + e.getMessage());
        return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
}
