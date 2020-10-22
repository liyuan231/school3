package com.school.utils;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.util.BytePictureUtils;
import com.school.exception.UserNotFoundException;
import com.school.model.Pics;
import com.school.model.Sign;
import com.school.model.User;
import com.school.service.impl.PicsServiceImpl;
import com.school.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FileUtil {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private PicsServiceImpl picsService;

    @Value("${file.path}")
    private String filePath;

    @Value("${file.name.witness}")
    private String witnessFileName;

    public String downloadCertification(HttpServletResponse response, Sign sign) throws UserNotFoundException {
        List<Pics> userLogos = picsService.querySelective(null, sign.getSignuserid(), FileEnum.LOGO.value());
        if (userLogos.size() == 0) {
            return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), "还未上传学校logo！");
        }
        List<Pics> userSignatures = picsService.querySelective(null, sign.getSignuserid(), FileEnum.SIGNATURE.value());
        if (userSignatures.size() == 0) {
            return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), "还未上传学校校长签章！");
        }
        List<Pics> useredLogos = picsService.querySelective(null, sign.getSigneduserid(), FileEnum.LOGO.value());
        if (useredLogos.size() == 0) {
            return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), "目标学校还未上传学校logo！");
        }
        List<Pics> useredSignatures = picsService.querySelective(null, sign.getSigneduserid(), FileEnum.SIGNATURE.value());
        if (useredSignatures.size() == 0) {
            return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), "目标学校还未上传学校校长签章！");
        }

//        List<Pics> witnesses = picsService.querySelective(null, null, FileEnum.WITNESS_LOGO.value());
//        if (witnesses.size() == 0) {
//            return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), "见证人的logo不存在！");
//        }
        User user = userService.findById(sign.getSignuserid());
        User usered = userService.findById(sign.getSigneduserid());

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        String country1 = user.getCountry();
        String logo1 = filePath + userLogos.get(0).getLocation();
        String signature1 = filePath + userSignatures.get(0).getLocation();
        String school1 = user.getSchoolname();
        String job1 = user.getProfession();
        String time1 = dateTimeFormatter.format(LocalDateTime.now());

        String country2 = usered.getCountry();
        String logo2 = filePath + useredLogos.get(0).getLocation();
        String signature2 = filePath + useredSignatures.get(0).getLocation();
        String school2 = usered.getSchoolname();
        String job2 = usered.getProfession();
        String time2 = dateTimeFormatter.format(LocalDateTime.now());

//        String witness = filePath + witnesses.get(witnesses.size() - 1).getLocation();
        String witness = filePath + witnessFileName;

        File file = new File(witness);
        if (!file.exists()) {
            return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), "见证者的logo未上传，且需命名为${file.name.witness}！");
        }
        Map<String, Object> datas = fillData(
                country1, logo1, school1, job1, time1, signature1,
                country2, logo2, school2, job2, time2, signature2,
                witness
        );
        try {
            render(datas, response);
        } catch (IOException | NullPointerException e) {
            return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), e.getMessage() + "生成签约证书失败！");
        }
        return ResponseUtil.build(HttpStatus.OK.value(), "生成签约证书成功！");
    }

    private void render(Map<String, Object> datas, HttpServletResponse response) throws IOException, NullPointerException {
        List<Pics> templates = picsService.querySelective(null, null, FileEnum.TEMPLATE.value(), null, null);
        if (templates.size() == 0) {
            throw new NullPointerException("管理员尚未上传签约证书模板！");
        }
        Pics theTemplate = templates.get(0);
        String templateLocation = filePath + theTemplate.getLocation();
        XWPFTemplate template = XWPFTemplate.compile(templateLocation)
                .render(datas);
        response.setHeader("Content-disposition", String.format("attachment;filename=\"%s\"", System.currentTimeMillis() + ".docx"));
        response.setContentType("multipart/form-data");
        response.setCharacterEncoding("UTF-8");
        BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        template.write(outputStream);
        template.close();
        outputStream.close();
    }

    private Map<String, Object> fillData(String country1, String logo1, String school1, String job1, String time1, String signature1,
                                         String country2, String logo2, String school2, String job2, String time2, String signature2,
                                         String logo3) {
        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                put("country1", country1);
                put("country2", country2);
                put("school1", school1);
                put("school2", school2);
                put("job1", job1);
                put("job2", job2);

                put("logo1", new PictureRenderData(90, 90, ".jpg", BytePictureUtils.getLocalBufferedImage(new File(logo1))));

                put("logo2", new PictureRenderData(90, 90, ".jpg", BytePictureUtils.getLocalBufferedImage(new File(logo2))));

                //最顶部公用的图片
                put("witness", new PictureRenderData(90, 90, ".jpg", BytePictureUtils.getLocalBufferedImage(new File(logo3))));
                put("signature1", new PictureRenderData(120, 50, ".jpg", BytePictureUtils.getLocalBufferedImage(new File(signature1))));
                put("signature2", new PictureRenderData(120, 50, ".jpg", BytePictureUtils.getLocalBufferedImage(new File(signature2))));

                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String time = sdf.format(date);
                put("date1", time);
                put("date2", time);
            }
        };
        return datas;
    }

}
