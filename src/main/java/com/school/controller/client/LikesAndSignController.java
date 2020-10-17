package com.school.controller.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.util.BytePictureUtils;
import com.school.dto.golden.likelist;
import com.school.dto.golden.picture;
import com.school.model.Pics;
import com.school.model.User;
import com.school.service.golden.userserviceimpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping({"/api"})
@Api(value = "")
public class LikesAndSignController {
    @Value("${spring.file.path}")
    private String springFilePath;

    @Autowired
    userserviceimpl user_service;

    @ResponseBody
    @RequestMapping("/get_Word")
    public JSON generateWord(String logo1,
                             String logo2,
                             String school1,
                             String school2,
                             String name1,
                             String name2,
                             String job1,
                             String job2,
                             String country1,
                             String country2,
                             HttpServletRequest request,
                             HttpServletResponse response) throws IOException {
        //ͼƬ·������ע������linux����windows
        String msg = null;
        String code = null;
        JSONObject result = new JSONObject();
        String path = request.getServletContext().getRealPath("");//��ȡ��Ŀ��̬����·��
        System.out.println(path);
        String wordtmp = path + "tmp.docx";
        if (logo1 == null || logo1.equals("")) {
            msg = "logo1 missing";
            code = "-1";
            result.put("msg", msg);
            result.put("code", code);
            return result;
        }
        if (logo2 == null || logo2.equals("")) {
            msg = "logo2 missing";
            code = "-2";
            result.put("msg", msg);
            result.put("code", code);
            return result;
        }
        if (name1 == null || name1.equals("")) {
            msg = "name1 missing";
            code = "-3";
            result.put("msg", msg);
            result.put("code", code);
            return result;
        }
        if (name2 == null || name2.equals("")) {
            msg = "name2 missing";
            code = "-4";
            result.put("msg", msg);
            result.put("code", code);
            return result;
        }
        if (job1 == null || job1.equals("")) {
            msg = "job1 missing";
            code = "-5";
            result.put("msg", msg);
            result.put("code", code);
            return result;
        }
        if (job2 == null || job2.equals("")) {
            msg = "job2 missing";
            code = "-6";
            result.put("msg", msg);
            result.put("code", code);
            return result;
        }
        if (school1 == null || school1.equals("")) {
            msg = "school1 missing";
            code = "-7";
            result.put("msg", msg);
            result.put("code", code);
            return result;
        }
        if (school2 == null || school2.equals("")) {
            msg = "school2 missing";
            code = "-8";
            result.put("msg", msg);
            result.put("code", code);
            return result;
        }
        if (country1 == null || country1.equals("")) {
            msg = "country1 missing";
            code = "-9";
            result.put("msg", msg);
            result.put("code", code);
            return result;
        }
        if (country2 == null || country2.equals("")) {
            msg = "country2 missing";
            code = "-10";
            result.put("msg", msg);
            result.put("code", code);
            return result;
        }
        String logo3 = "http://175.24.4.196/images/ddcc226b-ee92-4a39-84ed-2842ecd400c1.jpg";
        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                //����ͼƬ
                put("country1", country1);
                put("country2", country2);
                put("school1", school1);
                put("school2", school2);
                put("job1", job1);
                put("job2", job2);
                //��·ͼƬ
                put("logo1", new PictureRenderData(90, 90, ".jpg", BytePictureUtils.getUrlByteArray(logo1)));
                put("logo2", new PictureRenderData(90, 90, ".jpg", BytePictureUtils.getUrlByteArray(logo2)));
                put("logo3", new PictureRenderData(90, 90, ".jpg", BytePictureUtils.getUrlByteArray(logo3)));
                put("name1", new PictureRenderData(100, 50, ".jpg", BytePictureUtils.getUrlByteArray(name1)));
                put("name2", new PictureRenderData(100, 50, ".jpg", BytePictureUtils.getUrlByteArray(name2)));
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String time = sdf.format(date);
                put("date1", time);
                put("date2", time);
            }
        };

        try {
            XWPFTemplate template = XWPFTemplate.compile(wordtmp)
                    .render(datas);

            FileOutputStream out;
            //��������ļ�������
//	        String userAgent = request.getHeader("User-Agent");
            //IE�ں������
            //�����ļ�����ͷ
            response.setHeader("Content-disposition", String.format("attachment;filename=\"%s\"", "sign_up.docx"));//�̶�ģʽ�������ļ��������ͣ��пղ�������������ɶ��˼
            //�����ļ�ContentType���ͣ��������ã����Զ��ж������ļ�����
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding("UTF-8");
            BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            template.write(outputStream);
            template.close();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }
        msg = "success";
        code = "200";
        result.put("msg", msg);
        result.put("code", code);
        return result;
    }

    @ResponseBody
    @RequestMapping("/select_likes")
    public JSON select_sch(Integer id) {
        String msg = null;
        String code = null;
        int flag = 0;
        JSONObject result = new JSONObject();
        if (id == null) {
            msg = "school_id missing";
            code = "-1";
            result.put("msg", msg);
            result.put("code", code);
            return result;
        }
        List<likelist> list = new ArrayList<likelist>();
        try {
            list = user_service.select_likes(id);
        } catch (java.lang.NullPointerException e) {
            e.printStackTrace();
            msg = "null like";
            code = "-2";
            result.put("msg", msg);
            result.put("code", code);
            return result;
        }
        int len = list.size();
        for (int i = 0; i < len; i++) {
            if (user_service.select_both(list.get(i).getSch_name(), id)) {
                list.get(i).setFlag(true);
            }
        }
        msg = "success";
        code = "200";
        result.put("msg", msg);
        result.put("code", code);
        result.put("list", list);
        return result;
    }

    @ResponseBody
    @RequestMapping("/get_certi")
    public JSON get_certi(Integer host_id, Integer liked_id) throws UnknownHostException {
        String msg = null;
        String code = null;
        JSONObject result = new JSONObject();
        if (host_id == null || liked_id == null) {
            msg = "id missing";
            code = "-1";
            result.put("msg", msg);
            result.put("code", code);
            return result;
        }
        String logo1, logo2, name1, name2;
        InetAddress ia = InetAddress.getLocalHost();
        User user_1 = new User();
        User user_2 = new User();
        try {
            user_1 = user_service.select_user(host_id);
            user_2 = user_service.select_user(liked_id);
        } catch (java.lang.NullPointerException e) {
            e.printStackTrace();
            msg = "null user";
            code = "-2";
            result.put("msg", msg);
            result.put("code", code);
            return result;
        }
        List<Pics> pic_1 = new ArrayList<Pics>();
        List<Pics> pic_2 = new ArrayList<Pics>();
        try {
            pic_1 = user_service.select_pic(host_id);
            pic_2 = user_service.select_pic(liked_id);
        } catch (java.lang.NullPointerException e) {
            e.printStackTrace();
            msg = "null picture";
            code = "-3";
            result.put("msg", msg);
            result.put("code", code);
            return result;
        }
        try {
            if (pic_1.get(0).getType().equals(2)) {
                logo1 = "http://" + ia.getHostAddress() + "/" +
                        springFilePath +
                        "/" + pic_1.get(0).getLocation();
                name1 = "http://" + ia.getHostAddress() + "/" +
                        springFilePath +
                        "/" + pic_1.get(1).getLocation();
            } else {
                logo1 = "http://" + ia.getHostAddress() + "/" +
                        springFilePath +
                        "/" + pic_1.get(1).getLocation();
                name1 = "http://" + ia.getHostAddress() + "/" +
                        springFilePath +
                        "/" + pic_1.get(0).getLocation();
            }
        } catch (java.lang.IndexOutOfBoundsException e) {
            e.printStackTrace();
            msg = "logo or signature picture missing";
            code = "-4";
            result.put("msg", msg);
            result.put("code", code);
            return result;
        }
        try {
            if (pic_2.get(0).getType().equals(2)) {
                logo2 = "http://" + ia.getHostAddress() + "/" +
                        springFilePath +
                        "/" + pic_2.get(0).getLocation();
                name2 = "http://" + ia.getHostAddress() + "/" +
                        springFilePath +
                        "/" + pic_2.get(1).getLocation();
            } else {
                logo2 = "http://" + ia.getHostAddress() + "/" +
                        springFilePath +
                        "/" + pic_2.get(1).getLocation();
                name2 = "http://" + ia.getHostAddress() + "/" +
                        springFilePath +
                        "/" + pic_2.get(0).getLocation();
            }
        } catch (java.lang.IndexOutOfBoundsException e) {
            e.printStackTrace();
            msg = "logo or signature picture missing";
            code = "-4";
            result.put("msg", msg);
            result.put("code", code);
            return result;
        }
        picture pic1 = new picture();
        picture pic2 = new picture();
        pic1.setLogo(logo1);
        pic1.setSignature(name1);
        pic2.setLogo(logo2);
        pic2.setSignature(name2);
        result.put("picture1", pic1);
        result.put("picture2", pic2);
        result.put("user_info1", user_1);
        result.put("user_info2", user_2);
        msg = "success";
        code = "200";
        result.put("msg", msg);
        result.put("code", code);
        result.put("AUDFLOGO", "http://175.24.4.196/images/ddcc226b-ee92-4a39-84ed-2842ecd400c1.jpg");
        return result;
    }
}



