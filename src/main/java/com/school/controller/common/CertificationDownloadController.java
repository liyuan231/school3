package com.school.controller.common;

import com.school.dto.SimpleSign;
import com.school.exception.UserNotFoundException;
import com.school.model.Pics;
import com.school.model.Sign;
import com.school.model.User;
import com.school.service.impl.PicsServiceImpl;
import com.school.service.impl.SignServiceImpl;
import com.school.service.impl.UserServiceImpl;
import com.school.utils.FileEnum;
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
import java.util.LinkedList;
import java.util.List;

@Api(value = "签约证书下载", tags = {"签约证书下载"})
@RestController
@RequestMapping("/api/admin/certification")
public class CertificationDownloadController {
    @Autowired
    private SignServiceImpl signService;

    @Autowired
    FileUtil fileUtil;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    private PicsServiceImpl picsService;


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
        return fileUtil.downloadCertification(response, sign);
    }


    @GetMapping("/allSignIds")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public String allSignIds() {
        List<Sign> signs = signService.querySelective();
        List<SimpleSign> simpleSigns = new LinkedList<>();
        for (Sign sign : signs) {
            try {
                checkLogoAndSignature(sign.getSignUserId());
                checkLogoAndSignature(sign.getSignedUserId());
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
                continue;
            }
            SimpleSign simpleSign = new SimpleSign();
            simpleSign.setSignId(sign.getId());
            Integer signUserId = sign.getSignUserId();
            User user = userService.queryById(signUserId, User.Column.id, User.Column.schoolName);
            simpleSign.setSignSchoolName(user.getSchoolName());
            Integer signedUserId = sign.getSignedUserId();


            User signedUser = userService.queryById(signedUserId, User.Column.id, User.Column.schoolName);
            simpleSign.setSignedSchoolName(signedUser.getSchoolName());
            simpleSigns.add(simpleSign);
        }
        return ResponseUtil.build(HttpStatus.OK.value(), "获取证书id成功！", simpleSigns);
    }

    private void checkLogoAndSignature(Integer signUserId) throws NullPointerException {
        List<Pics> pics = picsService.querySelective(null, signUserId, FileEnum.LOGO.value());
        if (pics.size() == 0) {
            throw new NullPointerException("用户未上传logo！");
        }
        pics = picsService.querySelective(null, signUserId, FileEnum.SIGNATURE.value());
        if (pics.size() == 0) {
            throw new NullPointerException("用户未上传signature！");
        }
    }
}
