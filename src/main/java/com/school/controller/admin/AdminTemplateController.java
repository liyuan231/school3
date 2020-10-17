package com.school.controller.admin;

import com.school.service.impl.UserServiceImpl;
import com.school.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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

    //上传的签约模板文档简易处理下,默认模板名为template，当然可通过
    @PostMapping("/upload")
    @ApiOperation(value = "上传模板",notes = "管理端上传模板，为避免歧义，模板名称默认为'template',放在相应路径下")
    public Object upload(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return ResponseUtil.build(HttpStatus.OK.value(), "签约模板为空");
        }
        String fileName = file.getOriginalFilename();
        String format = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        fileName =  "template" + "."+format;
        File fileInServer = new File(this.filePath +fileName);
        file.transferTo(fileInServer);
        return ResponseUtil.build(HttpStatus.OK.value(), "上传模板成功!",springFilePath+fileName);
    }

}
