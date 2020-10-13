//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.school.exceptionHandler;

import com.school.exception.FileFormattingException;
import com.school.utils.ResponseUtil;
import javax.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@RestControllerAdvice
public class FileExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public FileExceptionHandler() {
    }

    @ExceptionHandler({FileFormattingException.class})
    public String fileFormattingException(HttpServletRequest request, FileFormattingException e) {
        this.logger.info("[" + request.getRemoteAddr() + "] ERROR " + e.getMessage());
        return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler({MissingServletRequestPartException.class})
    public String missingServletRequestPartException(HttpServletRequest request, MissingServletRequestPartException e) {
        this.logger.info("[" + request.getRemoteAddr() + "] ERROR " + e.getMessage());
        return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler({FileSizeLimitExceededException.class})
    public String fileSizeLimitExceededException(HttpServletRequest request, MissingServletRequestPartException e) {
        this.logger.info("[" + request.getRemoteAddr() + "] ERROR " + e.getMessage());
        return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
}
