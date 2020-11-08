package com.school.utils;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.util.BytePictureUtils;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.school.exception.UserNotFoundException;
import com.school.model.Pics;
import com.school.model.Sign;
import com.school.model.User;
import com.school.service.impl.PicsServiceImpl;
import com.school.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class FileUtil {
    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private PicsServiceImpl picsService;

    @Value("${file.path}")
    private String filePath;
    @Value("${file.certifications.tmp}")
    private String fileCertificationsTmp;

    public String downloadCertification(HttpServletResponse response, Sign sign) throws UserNotFoundException {
        List<Pics> userLogos = picsService.querySelective(null, sign.getSignUserId(), FileEnum.LOGO.value());
        if (userLogos.size() == 0) {
            return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), "还未上传学校logo！");
        }
        List<Pics> userSignatures = picsService.querySelective(null, sign.getSignUserId(), FileEnum.SIGNATURE.value());
        if (userSignatures.size() == 0) {
            return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), "还未上传学校校长签章！");
        }
        List<Pics> useredLogos = picsService.querySelective(null, sign.getSignedUserId(), FileEnum.LOGO.value());
        if (useredLogos.size() == 0) {
            return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), "目标学校还未上传学校logo！");
        }
        List<Pics> useredSignatures = picsService.querySelective(null, sign.getSignedUserId(), FileEnum.SIGNATURE.value());
        if (useredSignatures.size() == 0) {
            return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), "目标学校还未上传学校校长签章！");
        }
//        List<Pics> witnesses = picsService.querySelective(null, null, FileEnum.WITNESS_LOGO.value());
//        if (witnesses.size() == 0) {
//            return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), "见证人的logo不存在！");
//        }
        User user = userService.findById(sign.getSignUserId());
        User usered = userService.findById(sign.getSignedUserId());

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        String country1 = user.getCountry();
        String logo1 = filePath + userLogos.get(0).getLocation();
        String signature1 = filePath + userSignatures.get(0).getLocation();
        String school1 = user.getSchoolName();
        String job1 = user.getProfession();
        String time1 = dateTimeFormatter.format(LocalDateTime.now());
//        String name1 = user.getContact();

        String country2 = usered.getCountry();
        String logo2 = filePath + useredLogos.get(0).getLocation();
        String signature2 = filePath + useredSignatures.get(0).getLocation();
        String school2 = usered.getSchoolName();
        String job2 = usered.getProfession();
        String time2 = dateTimeFormatter.format(LocalDateTime.now());
//        String name2 = usered.getContact();

//        String witness = filePath + witnesses.get(witnesses.size() - 1).getLocation();
//        String witness = filePath + witnessFileName;

//        File file = new File(witness);
//        if (!file.exists()) {
//            return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), "见证者的logo未上传，且需命名为${file.name.witness}！");
//        }
        Map<String, Object> datas = fillData(
                country1, logo1, school1, job1, time1, signature1,
                country2, logo2, school2, job2, time2, signature2,
                null
        );
        try {
            render(datas, response);
        } catch (IOException | NullPointerException e) {
            return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), e.getMessage() + "生成签约证书失败！");
        }
        return ResponseUtil.build(HttpStatus.OK.value(), "生成签约证书成功！");
    }

    private void render(Map<String, Object> datas, HttpServletResponse response) throws IOException, NullPointerException {
//        List<Pics> templates = picsService.querySelective(null, null, FileEnum.TEMPLATE.value(), null, null);

//        if (templates.size() == 0) {
//            throw new NullPointerException("管理员尚未上传签约证书模板！");
//        }
//        Pics theTemplate = templates.get(0);
        String templateLocation = filePath + "tmp.docx";
        XWPFTemplate template = XWPFTemplate.compile(new File(templateLocation))
                .render(datas);

        String school1 = (String) datas.get("school1");
        String school2 = (String) datas.get("school2");
        String name = "签约证书(" + school1 + "-" + school2 + ")";
        response.setHeader("Content-disposition", "attachment;filename=" + new String(name.getBytes(StandardCharsets.UTF_8), "ISO8859-1") + ".docx");
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

                String formatLogo1 = logo1.substring(logo1.lastIndexOf("."));
                String formatLogo2 = logo2.substring(logo2.lastIndexOf("."));

                put("logo1", new PictureRenderData(90, 90, formatLogo1, BytePictureUtils.getLocalBufferedImage(new File(logo1))));
                put("logo2", new PictureRenderData(90, 90, formatLogo2, BytePictureUtils.getLocalBufferedImage(new File(logo2))));

                //最顶部公用的图片
                String formatSignature1 = logo1.substring(logo1.lastIndexOf("."));
                String formatSignature2 = logo2.substring(logo2.lastIndexOf("."));
                put("name1", new PictureRenderData(120, 50, formatSignature1, BytePictureUtils.getLocalBufferedImage(new File(signature1))));
                put("name2", new PictureRenderData(120, 50, formatSignature2, BytePictureUtils.getLocalBufferedImage(new File(signature2))));

//                put("logo3", "http://124.156.153.105/files/AUPFLOGO_1.jpg");
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String time = sdf.format(date);
                put("date1", time);
                put("date2", time);
                put("logo3", new PictureRenderData(90, 90, ".jpg", BytePictureUtils.getLocalBufferedImage(new File(filePath + "AUPFLOGO_1.jpg"))));
                put("logo4", new PictureRenderData(520, 90, ".jpg", BytePictureUtils.getLocalBufferedImage(new File(filePath + "AUPFLOGO_2.jpg"))));
//
//                put("name1", name1);
//                put("name2", name2);
            }
        };
        return datas;
    }


    //此方法其实和上面那个作用差不多，但由于若抽取处相同的方法，会使得不知道如何处理上述的弱智异常判断，不信自己试试，我也了解得到了，要多抛异常，少自己判断，学到了！！！
    public void downloadCertification(Sign sign) throws IllegalArgumentException, UserNotFoundException, IOException {
        List<Pics> userLogos = picsService.querySelective(null, sign.getSignUserId(), FileEnum.LOGO.value());
        if (userLogos.size() == 0) {
            throw new IllegalArgumentException("主动签约方未上传学校logo");
        }
        List<Pics> userSignatures = picsService.querySelective(null, sign.getSignUserId(), FileEnum.SIGNATURE.value());
        if (userSignatures.size() == 0) {
            throw new IllegalArgumentException("还未上传学校校长签章");
        }
        List<Pics> useredLogos = picsService.querySelective(null, sign.getSignedUserId(), FileEnum.LOGO.value());
        if (useredLogos.size() == 0) {
            throw new IllegalArgumentException("目标学校还未上传学校logo");
        }
        List<Pics> useredSignatures = picsService.querySelective(null, sign.getSignedUserId(), FileEnum.SIGNATURE.value());
        if (useredSignatures.size() == 0) {
            throw new IllegalArgumentException("目标学校还未上传学校校长签章");
        }
        User user = userService.findById(sign.getSignUserId());
        User usered = userService.findById(sign.getSignedUserId());

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        String country1 = user.getCountry();
        String logo1 = filePath + userLogos.get(0).getLocation();
        String signature1 = filePath + userSignatures.get(0).getLocation();
        String school1 = user.getSchoolName();
        String job1 = user.getProfession();
        String time1 = dateTimeFormatter.format(LocalDateTime.now());
//        String name1 = user.getContact();

        String country2 = usered.getCountry();
        String logo2 = filePath + useredLogos.get(0).getLocation();
        String signature2 = filePath + useredSignatures.get(0).getLocation();
        String school2 = usered.getSchoolName();
        String job2 = usered.getProfession();
        String time2 = dateTimeFormatter.format(LocalDateTime.now());
//        String name2 = usered.getContact();

//        String witness = filePath + witnesses.get(witnesses.size() - 1).getLocation();
//        String witness = filePath + witnessFileName;

//        File file = new File(witness);
//        if (!file.exists()) {
//            return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), "见证者的logo未上传，且需命名为${file.name.witness}！");
//        }
        Map<String, Object> datas = fillData(
                country1, logo1, school1, job1, time1, signature1,
                country2, logo2, school2, job2, time2, signature2,
                null
        );

        String templateLocation = filePath + "tmp.docx";
        XWPFTemplate template = XWPFTemplate.compile(new File(templateLocation))
                .render(datas);

        String name = "签约证书(" + school1 + "-" + school2 + ")";
        //写到本地中
        String directory = filePath + fileCertificationsTmp;
        File file = new File(directory);
        if (!file.exists()) {
            file.mkdir();
        }
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(new File(filePath + fileCertificationsTmp + name + ".docx")));
        template.write(outputStream);
//        BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
//        template.write(outputStream);
        template.close();
        outputStream.close();
    }

    public static String folderToZip(String src, String fileName, String target) throws IOException {
        File srcFile = new File(src);
        if (!srcFile.exists()) {
            logger.error(src + "不存在！");
            throw new FileNotFoundException(src + "->文件不存在！");
        }
        File[] files = srcFile.listFiles();
        assert files != null;
        if (files.length == 0) {
            logger.error(src + "文件夹中内容为空！");
            throw new FileNotFoundException(src + "->文件夹内容为空");
        }
        File zipFile = new File(target + fileName + ".zip");
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)))) {
            compress(zipOutputStream, srcFile, srcFile.getName());
        }
        return zipFile.getAbsolutePath();
    }

    private static void compress(ZipOutputStream zipOutputStream, File srcFile, String base) throws IOException {
        if (srcFile.isDirectory()) {
            File[] files = srcFile.listFiles();
            if (files.length == 0) {
                zipOutputStream.putNextEntry(new ZipEntry(base));
            } else {
                for (File file : files) {
                    compress(zipOutputStream, file, base + "/" + file.getName());
                }
            }
        } else {
            zipOutputStream.putNextEntry(new ZipEntry(base));
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(srcFile));
            byte[] bytes = new byte[1024 * 2];
            while (bufferedInputStream.read(bytes, 0, bytes.length) != -1) {
                zipOutputStream.write(bytes);
            }
            bufferedInputStream.close();
        }
    }

    public void downloadCertification2(HttpServletResponse response, Sign sign) throws IOException, DocumentException {
        User u1 = userService.queryById(sign.getSignUserId(), User.Column.id, User.Column.schoolName);
        User u2 = userService.queryById(sign.getSignedUserId(), User.Column.id, User.Column.schoolName);
        List<Pics> u1Logos = picsService.querySelective(u1.getId(), FileEnum.LOGO.value());
        if (u1Logos.size() == 0) {
            throw new NullPointerException("logo不存在！");
        }
        List<Pics> u2Logos = picsService.querySelective(u2.getId(), FileEnum.LOGO.value());
        if (u2Logos.size() == 0) {
            throw new NullPointerException("logo不存在！");
        }
        String mouPdf = filePath + "mou.pdf";
        String logo1 = filePath + u1Logos.get(0).getLocation();
        String logo2 = filePath + u2Logos.get(0).getLocation();
        String schoolName1 = u1.getSchoolName();
        String schoolName2 = u2.getSchoolName();
//        String targetPath = filePath + fileCertificationsTmp + schoolName1 + "-" + schoolName2 + ".pdf";
//        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(targetPath)));
        String name = "签约证书(" + schoolName1 + "-" + schoolName2 + ")" + ".pdf";
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "filename=" + new String(name.getBytes(), "iso8859-1"));
        try (DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(response.getOutputStream()));) {
            PdfStamper pdfStamper = new PdfStamper(new PdfReader(mouPdf), dataOutputStream);
            PdfContentByte pdfContentByte = pdfStamper.getOverContent(1);
            insertImages(logo1, logo2, pdfContentByte);
            insertText(schoolName1, schoolName2, pdfContentByte);
            pdfStamper.close();
        }
    }

    private static void insertImages(String logo1, String logo2, PdfContentByte pdfContentByte) throws IOException, DocumentException {

        byte[] bytesLogo1 = transferAlpha(logo1);
        byte[] bytesLogo2 = transferAlpha(logo2);
        Image imageLogo1 = Image.getInstance(bytesLogo1);
        Image imageLogo2 = Image.getInstance(bytesLogo2);
//        Image imageLogo1 = Image.getInstance(logo1);
//        Image imageLogo2 = Image.getInstance(logo2);
        imageLogo1.setCompressionLevel(9);
        imageLogo2.setCompressionLevel(9);
        int widthLogo1 = 130;
        int heightLogo1 = 130;
        int widthLogo2 = 130;
        int heightLogo2 = 130;
        if(imageLogo1.getWidth()>=2*imageLogo1.getHeight()){
            widthLogo1 = (int) (1.5*widthLogo1);
            heightLogo1 = (int) (1.5*heightLogo1);
        }
        if(imageLogo2.getWidth()>=2*imageLogo2.getHeight()){
            widthLogo2 = (int) (1.5*widthLogo2);
            heightLogo2 = (int) (1.5*heightLogo2);
        }

        imageLogo1.scaleToFit(widthLogo1, heightLogo1);
        imageLogo2.scaleToFit(widthLogo2, heightLogo2);
//        imageLogo1.setScaleToFitHeight(true);
//        imageLogo2.setScaleToFitHeight(true);
        imageLogo1.setScaleToFitLineWhenOverflow(true);
        imageLogo2.setScaleToFitLineWhenOverflow(true);
        imageLogo1.setAbsolutePosition(200, 237);
        imageLogo2.setAbsolutePosition(470, 237);

        pdfContentByte.addImage(imageLogo1);
        pdfContentByte.addImage(imageLogo2);
    }

    private static void insertText(String schoolName1, String schoolName2, PdfContentByte pdfContentByte) throws IOException, DocumentException {
        BaseFont font = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        pdfContentByte.setColorFill(BaseColor.RED);
        pdfContentByte.beginText();

        int logo1X = 325 - (((schoolName1.length() >> 1) * 37)) % 325;
        pdfContentByte.setFontAndSize(font, 24);
        pdfContentByte.setTextMatrix(logo1X, 153);
        pdfContentByte.showText(schoolName1);


        pdfContentByte.setFontAndSize(font, 24);
        pdfContentByte.setTextMatrix(440, 153);
        pdfContentByte.showText(schoolName2);

        pdfContentByte.endText();
    }

    private static byte[] transferAlpha(String imagePath) throws IOException {
        String format = imagePath.substring(imagePath.lastIndexOf(".") + 1);
        BufferedImage b = ImageIO.read(new FileInputStream(imagePath));
        ImageIcon imageIcon = new ImageIcon(b);
        BufferedImage bufferedImage = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D graphics2D = (Graphics2D) bufferedImage.getGraphics();
        graphics2D.drawImage(imageIcon.getImage(), 0, 0, imageIcon.getImageObserver());
        int alpha = 0;
        for (int y = bufferedImage.getMinY(); y < bufferedImage.getHeight(); y++) {
            for (int x = bufferedImage.getMinX(); x < bufferedImage.getWidth(); x++) {
                int rgb = bufferedImage.getRGB(x, y);

                int R = (rgb & 0xff0000) >> 16;
                int G = (rgb & 0xff00) >> 8;
                int B = (rgb & 0xff);
                if (((255 - R) < 30) && ((255 - G) < 30) && ((255 - B) < 30)) {
                    rgb = ((alpha + 1) << 24) | (rgb & 0x00ffffff);
                }
                bufferedImage.setRGB(x, y, rgb);
            }
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        graphics2D.drawImage(bufferedImage, 0, 0, imageIcon.getImageObserver());
//        return bufferedImage;
//        ImageIO.write(bufferedImage, "png", new File("C:\\Users\\Administrator\\Desktop\\" + System.currentTimeMillis() + "." + format));
        boolean png = ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
