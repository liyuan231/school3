package com.school.component.limit;

import com.school.utils.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class RequestLimitController {
    @RequestMapping("/error/requestLimit")
    public String requestLimit(HttpServletRequest request) {
        Object remainingTime = request.getAttribute("remainingTime");
        return ResponseUtil.build(HttpStatus.FORBIDDEN.value(), "IP访问过于频繁!", remainingTime);
    }
}
