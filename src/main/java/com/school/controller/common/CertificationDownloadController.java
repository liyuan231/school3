package com.school.controller.common;

import com.school.exception.UserNotFoundException;
import com.school.model.Sign;
import com.school.service.impl.SignServiceImpl;
import com.school.utils.FileUtil;
import com.school.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(value = "签约证书下载", tags = {"签约证书下载"})
@RestController
@RequestMapping("/api/admin/certification")
public class CertificationDownloadController {
    @Autowired
    private SignServiceImpl signService;

    @Autowired
    FileUtil fileUtil;


    @GetMapping("/download/{signId}")
    @PreAuthorize("hasAnyRole('USER','ADMINISTRATOR')")
    @ApiOperation(value = "下载签约证书", notes = "下载签约证书")
    public String downloadCertification(@PathVariable("signId") Integer signId,
                                        HttpServletResponse response) throws UserNotFoundException {

        List<Sign> signs = signService.querySelective(signId, null, null, null, null, null, null, null, null, null);
        if (signs.size() == 0) {
            return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), "该则签约id不存在！");
        }
        Sign sign = signs.get(0);
//        User user = userService.retrieveUserByToken();
//        if (!sign.getSignuserid().equals(user.getId())) {
//            return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), "当前用户与目标的签约id不符！");
//        }
        return fileUtil.downloadCertification(response, sign);
    }


}
