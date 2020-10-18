package com.school.controller.admin;


import com.school.dto.Certification;
import com.school.dto.SimplePage;
import com.school.exception.UserNotFoundException;
import com.school.model.Pics;
import com.school.model.Sign;
import com.school.model.User;
import com.school.service.impl.PicsServiceImpl;
import com.school.service.impl.SignServiceImpl;
import com.school.service.impl.UserServiceImpl;
import com.school.utils.CommonUtil;
import com.school.utils.FileEnum;
import com.school.utils.FileUtil;
import com.school.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.List;

@Api(value = "签约证书浏览及全部下载", tags = {"签约证书浏览及全部下载，单个下载见签约证书下载"})
@RestController
@RequestMapping("/api/admin/certification")
public class AdminCertificationController {
    @Autowired
    private SignServiceImpl signService;

    @Value("${file.name.witness}")
    private String witness;
    @Value("${spring.file.path}")
    private String springFilePath;

    @Autowired
    FileUtil fileUtil;
    @Autowired
    CommonUtil commonUtil;

    @GetMapping("/listSearch")
    @ApiOperation(value = "签约证书下载->获取列表（包括搜索）", notes = "签约证书下载->获取列表")
    public String listSearch(
            @ApiParam(example = "广外", value = "schoolName") @RequestParam(value = "schoolName", required = false) String schoolName,
            @ApiParam(example = "1", value = "分页使用，要第几页的数据") @RequestParam(value = "page", required = false) Integer page,
            @ApiParam(example = "10", value = "分页使用，要该页的几条数据") @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @ApiParam(example = "update_time", value = "排序方式，从数据库中要的数据使用什么进行排序，如 add_time,update_time") @RequestParam(defaultValue = "add_time") String sort,
            @ApiParam(example = "desc", value = "排序方式，升序asc还是降序desc") @RequestParam(defaultValue = "desc") String order) throws UserNotFoundException {

        List<Sign> signs = signService.querySelective(null, null, schoolName, null, null, null, page, pageSize, sort, order);
        Integer count = signService.count(schoolName, null, null);
        List<Certification> certifications = new LinkedList<>();
        for (Sign sign : signs) {
            Certification certification = new Certification();
            certification.setSignId(sign.getId());
            certification.setWitness(springFilePath + witness);
            certification.setSignUserDate(sign.getUpdateTime().toLocalDate().toString());
            certification.setSignedUserDate(sign.getUpdateTime().toLocalDate().toString());
            try {
                commonUtil.fill(certification, sign.getSignuserid(), sign.getSigneduserid());
            } catch (NullPointerException e) {
                System.out.println("当前用户未上传logo或signature");
                continue;
            }
            certifications.add(certification);
        }
        SimplePage<List<Certification>> simplePage = new SimplePage<>(count, certifications);
        return ResponseUtil.build(HttpStatus.OK.value(), "获取签约证书信息成功!", simplePage);
    }


    @GetMapping("/downloadAll")
    @ApiOperation(value = "签约证书下载->下载所有证书", notes = "下载所有证书")
    public String download(HttpServletResponse response) {
        List<Sign> signs = signService.querySelective(null, null, null, null, null, null, null, null, null, null);
        //多线程方式会报错，之后再弄，先暂时这样
        for (Sign sign : signs) {
//            System.out.println(sign.getId());
//            new Thread(() -> {
            try {
                fileUtil.downloadCertification(response, sign);
            } catch (Throwable e) {
                System.out.println(e);
            }
//            }).start();

        }
        return ResponseUtil.build(HttpStatus.OK.value(), "下载全部合法的签约证书成功!");
    }

}





