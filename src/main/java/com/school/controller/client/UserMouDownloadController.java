package com.school.controller.client;

import com.school.model.Pics;
import com.school.service.impl.PicsServiceImpl;
import com.school.utils.FileEnum;
import com.school.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@Api(value = "MOU模板", tags = {"Mou模板"})
@RestController
@RequestMapping("/api/client/mou")
public class UserMouDownloadController {
    @Value("${file.path}")
    private String filePath;

    @Autowired
    private PicsServiceImpl picsService;

    @GetMapping("/download")
    @ApiOperation(value = "下载mou模板", notes = "客户端下载管理端的mou模板")
    @PreAuthorize("(hasRole('USER') and hasAuthority('user::mouDownload'))or hasRole('ADMINISTRATOR')")
    public Object download(HttpServletResponse response) throws IOException {

        List<Pics> pics = picsService.querySelective(null, null, FileEnum.TEMPLATE.value(), "update_time", "desc");
        if (pics.size() == 0) {
            return ResponseUtil.build(HttpStatus.OK.value(), "管理员还未配置mou模板，请等待管理员配置");
        }
        Pics file1 = pics.get(pics.size()-1);

        response.addHeader("Content-Disposition", "attachment;filename=" + file1.getLocation());
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        File file = new File(filePath + file1.getLocation());
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(outputStream));
        int val=-1;
        while ((val=dataInputStream.read())!=-1){
            dataOutputStream.write(val);
        }
//        workbook.write(outputStream);
        dataInputStream.close();
        dataInputStream.close();
        outputStream.close();
//        workbook.close();
        return ResponseUtil.build(HttpStatus.OK.value(), "下载mou模板成功！");

    }
}
