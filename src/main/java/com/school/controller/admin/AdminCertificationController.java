package com.school.controller.admin;


import com.school.dto.Certification;
import com.school.dto.SimplePage;
import com.school.exception.UserNotFoundException;
import com.school.model.Sign;
import com.school.service.impl.PicsServiceImpl;
import com.school.service.impl.SignServiceImpl;
import com.school.utils.CommonUtil;
import com.school.utils.FileUtil;
import com.school.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections4.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Api(value = "签约证书浏览及全部下载", tags = {"签约证书浏览及全部下载，单个下载见签约证书下载"})
@RestController
@RequestMapping("/api/admin/certification")
public class AdminCertificationController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SignServiceImpl signService;

    @Value("${file.name.witness}")
    private String witness;
    @Value("${spring.file.path}")
    private String springFilePath;
    @Autowired
    private PicsServiceImpl picsService;

    @Autowired
    FileUtil fileUtil;
    @Autowired
    CommonUtil commonUtil;

    @GetMapping("/listSearch")
    @ApiOperation(value = "签约证书下载->获取列表（包括搜索）", notes = "签约证书下载->获取列表")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public String listSearch(
            @ApiParam(example = "广外", value = "schoolName") @RequestParam(value = "schoolName", required = false) String schoolName,
            @ApiParam(example = "1", value = "分页使用，要第几页的数据") @RequestParam(value = "page", required = false) Integer page,
            @ApiParam(example = "10", value = "分页使用，要该页的几条数据") @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @ApiParam(example = "update_time", value = "排序方式，从数据库中要的数据使用什么进行排序，如 add_time,update_time") @RequestParam(defaultValue = "add_time") String sort,
            @ApiParam(example = "desc", value = "排序方式，升序asc还是降序desc") @RequestParam(defaultValue = "desc") String order) throws UserNotFoundException {

//        List<Sign> signs = signService.querySelective(null, null, schoolName, null, null, null, page, pageSize, sort, order);
        List<Sign> signs = signService.querySelective(null, null, schoolName, null, null, null, null, null, sort, order);


//        signService.querySelective(null,);
//        List<SignWithUser> signWithUsers = signService.querySelective(null, schoolName, page, pageSize, sort, order);
//        List<SignWithUserExtend> signWithUserExtends = new LinkedList<>();
//        Integer size = signService.count(null, schoolName);
//        for (SignWithUser signWithUser : signWithUsers) {
//            SignWithUserExtend signWithUserExtend = new SignWithUserExtend();
//            signWithUserExtend.setSignWithUser(signWithUser);
//            List<Pics> signUserLogos = picsService.querySelective(null, signWithUser.getSignUserId(), FileEnum.LOGO.value());
//            signWithUserExtend.setSignUserLogo(signUserLogos.size() > 0);
//            List<Pics> signUserSignatures = picsService.querySelective(null, signWithUser.getSignUserId(), FileEnum.SIGNATURE.value());
//            //                signWithUser.setSignature(false);
//            signWithUserExtend.setSignUserSignature(signUserSignatures.size() > 0);
//            List<Pics> signedUserLogos = picsService.querySelective(null, signWithUser.getSignedUserId(), FileEnum.LOGO.value());
//
//            signWithUserExtend.setSignUserLogo(signedUserLogos.size() > 0);
//            List<Pics> signedUserSignatures = picsService.querySelective(null, signWithUser.getSignedUserId(), FileEnum.SIGNATURE.value());
//            signWithUserExtend.setSignedUserSignature(signedUserSignatures.size() > 0);
//            signWithUserExtends.add(signWithUserExtend);
//        }
        Integer count = signService.count(null, schoolName, null, null);
        List<Certification> certifications = new LinkedList<>();
        for (Sign sign : signs) {
            Certification certification = new Certification();
            certification.setSignId(sign.getId());
            certification.setWitness(springFilePath + witness);
            certification.setSignUserDate(sign.getUpdateTime().toLocalDate().toString());
            certification.setSignedUserDate(sign.getUpdateTime().toLocalDate().toString());
            try {
                commonUtil.fill(certification, sign.getSignUserId(), sign.getSignedUserId());
            } catch (NullPointerException e) {
                count--;
                System.out.println("当前用户未上传logo或signature");
                continue;
            }
            certifications.add(certification);
        }
        List<List<Certification>> partition = ListUtils.partition(certifications, pageSize);
        List<Certification> result = null;
        if(partition.size()>=page){
            result = partition.get(page - 1);
        }
        SimplePage<List<Certification>> simplePage = new SimplePage<>(count, result);
//        SimplePage<List<?>> simplePage = new SimplePage<>(size, signWithUserExtends);
        return ResponseUtil.build(HttpStatus.OK.value(), "获取签约证书信息成功!", simplePage);
    }

    @Value("${file.certifications.tmp}")
    private String fileCertificationsTmp;

    @Value("${file.path}")
    private String filePath;

    @GetMapping("/downloadAll")
    @ApiOperation(value = "返回了压缩包，前端记得接收下 xxx.zip文件签约证书下载->下载所有证书", notes = "下载所有证书")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public String download(HttpServletResponse response) {
        List<Sign> signs = signService.querySelective(null, null, null, null, null, null, null, null, null, null);
        //多线程方式会报错，之后再弄，先暂时这样
        //将所有的签约证书下载至本地
        for (Sign sign : signs) {
            try {
                fileUtil.downloadCertification(sign);
            } catch (Exception | UserNotFoundException e) {
                logger.error(e.getMessage());
            }
        }
        String directory = filePath + fileCertificationsTmp;
        try {
            String zipPath = FileUtil.folderToZip(directory, "签约证书", filePath);
            ResponseUtil.export(new File(zipPath), response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return ResponseUtil.build(HttpStatus.OK.value(), "下载全部合法的签约证书成功!");
    }
}





