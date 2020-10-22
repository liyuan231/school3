package com.school.controller.admin;

import com.school.exception.UserNotFoundException;
import com.school.model.Pics;
import com.school.model.User;
import com.school.service.impl.PicsServiceImpl;
import com.school.service.impl.UserServiceImpl;
import com.school.utils.FileEnum;
import com.school.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Api(value = "签约模板配置", tags = {"签约模板配置"})
@RestController
@RequestMapping("/api/admin/template")
public class AdminTemplateController {
    @Autowired
    private UserServiceImpl userService;
    @Value("${file.path}")
    private String filePath;
    @Value("${spring.file.path}")
    private String springFilePath;

    @Autowired
    private PicsServiceImpl picsService;

    //TODO 由于前期没想到用户上传的静态资源应该统一管理，所以先暂时使用PicsServiceImpl表示该资源
    @PostMapping("/upload")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR')")
    @ApiOperation(value = "上传模板",notes = "管理端上传模板，为避免歧义，模板名称默认为'template',放在相应路径下")
    public Object upload(@RequestParam("file") MultipartFile file) throws IOException, UserNotFoundException {
        if (file.isEmpty()) {
            return ResponseUtil.build(HttpStatus.OK.value(), "签约模板为空");
        }
//        String fileName = file.getOriginalFilename();
        User user = userService.retrieveUserByToken();
        Pics upload = picsService.upload(user.getId(), FileEnum.TEMPLATE.value(), file);
        return ResponseUtil.build(HttpStatus.OK.value(), "上传模板成功!",springFilePath+upload.getLocation());
    }





}
