package com.school.controller.client;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Api(value = "MOU模板", tags = {"Mou模板"})
@RestController
@RequestMapping("/api/client/mou")
public class UserMouDownloadController {
    @Value("${file.path}")
    private String filePath;

    @GetMapping("/download")
    @ApiOperation(value = "下载mou-sampel.docx模板", notes = "客户端下载管理端的mou模板")
    @PreAuthorize("(hasRole('USER'))or hasRole('ADMINISTRATOR')")
    public void download(HttpServletResponse response) throws IOException {
        try {
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding("UTF-8");
            BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            response.setHeader("Content-disposition", String.format("attachment;filename=\"%s\"", "mou-sample.docx"));
            String file = filePath + "mou-sample.docx";
            InputStream input = new FileInputStream(file);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                outputStream.write(buf, 0, bytesRead);
            }
            outputStream.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @GetMapping("/download2")
    @ApiOperation(value = "下载mou-sample.pdf模板", notes = "客户端下载管理端的mou模板")
    @PreAuthorize("(hasRole('USER'))or hasRole('ADMINISTRATOR')")
    public void download2(HttpServletResponse response) throws IOException {
        try {
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding("UTF-8");
            BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            response.setHeader("Content-disposition", String.format("attachment;filename=\"%s\"", "mou-sample.pdf"));
//            BufferedReader bre = null;
            String file = filePath + "mou-sample.pdf";
            InputStream input = null;
            input = new FileInputStream(file);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                outputStream.write(buf, 0, bytesRead);
            }
            outputStream.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
