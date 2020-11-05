package com.school.controller.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.util.BytePictureUtils;
import com.github.pagehelper.PageInfo;
import com.school.dto.*;
import com.school.dto.golden.picture;
import com.school.model.Likes;
import com.school.model.Pics;
import com.school.model.Sign;
import com.school.model.User;
import com.school.service.golden.userserviceimpl;
import com.school.service.impl.LikeServiceImpl;
import com.school.service.impl.PicsServiceImpl;
import com.school.service.impl.SignServiceImpl;
import com.school.service.impl.UserServiceImpl;
import com.school.utils.FileEnum;
import com.school.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Controller
@RequestMapping({"/api/client/signs"})
@Api(value = "我的意向及签约",
        tags = {"我的意向及签约"})
public class LikesAndSignController {
    @Value("${spring.file.path}")
    private String springFilePath;

    @Value("${file.path}")
    private String filePath;

    @Autowired
    userserviceimpl user_service;


    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private LikeServiceImpl likeService;
    @Autowired
    private SignServiceImpl signService;
    @Autowired
    private PicsServiceImpl picsService;
    @Value("${file.default.logo}")
    private String defaultLogo;


    @ResponseBody
    @GetMapping("/list")
    @ApiOperation(value = "我的意向及签约列表", notes = "我的意向及签约列表")
    @PreAuthorize("hasRole('USER')")
    public Object list(
            @ApiParam(example = "1", value = "分页使用，要第几页的数据") @RequestParam(value = "page", required = false) Integer page,
            @ApiParam(example = "10", value = "分页使用，要该页的几条数据") @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @ApiParam(example = "update_time", value = "排序方式，从数据库中要的数据使用什么进行排序，如 add_time,update_time") @RequestParam(defaultValue = "add_time", required = false) String sort,
            @ApiParam(example = "desc", value = "排序方式，升序asc还是降序desc") @RequestParam(defaultValue = "desc", required = false) String order) {
        User user = userService.retrieveUserByToken();
        List<Sign> signs = signService.queryBySignUserId(user.getId());
        List<Sign> signs2 = signService.queryBySignedUserId(user.getId());
        signs.addAll(signs2);
        int size = 0;
        List<List<Sign>> partition = ListUtils.partition(signs, pageSize);
        List<Sign> result = null;
        if (partition.size() >= page) {
            result = partition.get(page - 1);
        }
        List<FullSignWithUser> fullSignWithUsers = new LinkedList<>();
        for (Sign sign : result) {
            FullSignWithUser fullSignWithUser = new FullSignWithUser();
            User signUser = userService.queryById(sign.getSignUserId(), User.Column.id, User.Column.schoolName);
            User signedUser = userService.queryById(sign.getSignedUserId(), User.Column.id, User.Column.schoolName);
            fullSignWithUser.setSignId(sign.getId());
            fullSignWithUser.setSignUserId(signUser.getId());
            fullSignWithUser.setSignUserSchoolName(signUser.getSchoolName());
            fullSignWithUser.setSignedUserId(signedUser.getId());
            fullSignWithUser.setSignedUserSchoolName(signedUser.getSchoolName());
            List<Pics> signUserLogos = picsService.querySelective(null, signUser.getId(), FileEnum.LOGO.value());
            if (signUserLogos.size() == 0) {
                continue;
            }
            fullSignWithUser.setSignUserLogo(springFilePath + signUserLogos.get(0).getLocation());
            List<Pics> signedUserLogos = picsService.querySelective(null, signedUser.getId(), FileEnum.LOGO.value());
            if (signedUserLogos.size() == 0) {
                continue;
            }
            fullSignWithUser.setSignedUserLogo(springFilePath + signedUserLogos.get(0).getLocation());
            fullSignWithUsers.add(fullSignWithUser);
            size++;
        }
        SimplePage simplePage = new SimplePage(size, fullSignWithUsers);
        return ResponseUtil.build(HttpStatus.OK.value(), "获取签约证书信息成功！", simplePage);
    }

    @ResponseBody
    @GetMapping("/signsList")
    @ApiOperation(value = "当前用户的签约结果", notes = "当前用户的签约结果")
    @PreAuthorize("hasRole('USER')")
    public Object signsList(
            @ApiParam(example = "1", value = "分页使用，要第几页的数据") @RequestParam(value = "page", required = false) Integer page,
            @ApiParam(example = "10", value = "分页使用，要该页的几条数据") @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @ApiParam(example = "update_time", value = "排序方式，从数据库中要的数据使用什么进行排序，如 add_time,update_time") @RequestParam(defaultValue = "add_time") String sort,
            @ApiParam(example = "desc", value = "排序方式，升序asc还是降序desc") @RequestParam(defaultValue = "desc") String order) {
        User user = userService.retrieveUserByToken();
        List<Sign> signs = signService.queryBySignUserId(user.getId());
        List<Sign> signs1 = signService.queryBySignedUserId(user.getId());
        signs.addAll(signs1);
        int i = 0;
        int[] signUserIds = new int[signs.size()];
        for (Sign sign : signs) {
            if (sign.getSignUserId().equals(user.getId())) {
                signUserIds[i++] = sign.getSignedUserId();
            } else {
                signUserIds[i++] = sign.getSignUserId();
            }
        }
        List<User> users = new LinkedList<>();
        for (int id : signUserIds) {
            User u = userService.queryById(id, User.Column.id, User.Column.schoolName, User.Column.updateTime);
            users.add(u);
        }
        return ResponseUtil.build(HttpStatus.OK.value(), "当前用户的签约结果！", users);
    }

    @ResponseBody
    @GetMapping("/retrieveNotLikeOrSignUser")
    @ApiOperation(value = "获取当前用户未选意向未签约列表", notes = "未选意向未签约列表")
    @PreAuthorize("hasRole('USER')")
    public Object retrieveNotLikeOrSignUser() {
        User user = userService.retrieveUserByToken();
        PageInfo<User> userPageInfo = userService.querySelective(User.Column.id, User.Column.schoolName, User.Column.contact, User.Column.address, User.Column.telephone, User.Column.profession, User.Column.country, User.Column.website, User.Column.location, User.Column.schoolCode, User.Column.username);
        List<User> users = userPageInfo.getList();
        List<Sign> signs = signService.queryBySignedUserId(user.getId());
        List<Sign> signs1 = signService.queryBySignUserId(user.getId());
        PageInfo<Likes> likesPageInfo = likeService.querySelective(null, user.getId(), null, null, null, null, null, null, null, null, null);
        List<Likes> likesList = likesPageInfo.getList();
        int[] likeUserIds = new int[likesList.size()];
        int j = 0;
        for (Likes likes : likesList) {
            likeUserIds[j++] = likes.getLikedUserId();
        }
        Arrays.sort(likeUserIds);
        signs.addAll(signs1);
        int[] signUserIds = new int[signs.size()];
        int i = 0;
        for (Sign sign : signs) {
            if (sign.getSignUserId().equals(user.getId())) {
                signUserIds[i++] = sign.getSignedUserId();
            } else {
                signUserIds[i++] = sign.getSignUserId();
            }
        }
        Arrays.sort(signUserIds);

        Stream<User> userStream = users.stream().filter(new Predicate<User>() {
            @Override
            public boolean test(User u1) {
                if (Arrays.binarySearch(signUserIds, (int) u1.getId()) >= 0 || u1.getId().equals(user.getId())) {
                    return false;
                }
                return true;
            }
        });
        List<User> collect = userStream.filter(new Predicate<User>() {
            @Override
            public boolean test(User user) {
                if (Arrays.binarySearch(likeUserIds, user.getId()) >= 0) {
                    return false;
                }
                return true;
            }
        }).collect(Collectors.toList());

        List<FullUser> fullUsers = new LinkedList<>();
        for (User u : collect) {
            FullUser fullUser = new FullUser();
            fullUser.setUser(u);
            List<Pics> pics = picsService.querySelective(null, u.getId(), FileEnum.LOGO.value());
            if (pics.size() > 0) {
                fullUser.setLogo(springFilePath + pics.get(0).getLocation());
            } else {
//                fullUser.setLogo(springFilePath + defaultLogo);
            }
            fullUsers.add(fullUser);
        }
        return ResponseUtil.build(HttpStatus.OK.value(), "获取未签约未标明意向的用户成功！", fullUsers);
    }


    @ResponseBody
    @GetMapping("/unreceived")
    @ApiOperation(value = "未接受我的邀约", notes = "未接受我的邀约")
    @PreAuthorize("hasRole('USER')")
    public String unReceived(
            @ApiParam(example = "1", value = "分页使用，要第几页的数据") @RequestParam(value = "page", required = false) Integer page,
            @ApiParam(example = "10", value = "分页使用，要该页的几条数据") @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @ApiParam(example = "update_time", value = "排序方式，从数据库中要的数据使用什么进行排序，如 add_time,update_time") @RequestParam(defaultValue = "add_time", required = false) String sort,
            @ApiParam(example = "desc", value = "排序方式，升序asc还是降序desc") @RequestParam(defaultValue = "desc", required = false) String order) {
        User user = userService.retrieveUserByToken();
        PageInfo<Likes> likesPageInfo = likeService.querySelective(null, user.getId(), null, null, null, page, pageSize, sort, order, null, null);
        List<Likes> likes = likesPageInfo.getList();
        List<LikeWithUser> likeWithUsers = new LinkedList<>();
        for (Likes like : likes) {
            LikeWithUser likeWithUser = new LikeWithUser();
            User u = userService.queryById(like.getLikedUserId(), User.Column.id, User.Column.schoolName, User.Column.addTime);
            likeWithUser.setLikeId(like.getId());
            likeWithUser.setLikedSchoolName(u.getSchoolName());
            likeWithUser.setAddTime(like.getAddTime());
            likeWithUsers.add(likeWithUser);
        }
        return ResponseUtil.build(HttpStatus.OK.value(), "未接受我的邀约", likeWithUsers);
    }


    @ResponseBody
    @GetMapping("/unchoosed")
    @ApiOperation(value = "获取当前用户未选意向未签约列表", notes = "未选意向未签约列表")
    @PreAuthorize("hasRole('USER')")
    public JSON get_not_like() {
        JSONObject result = new JSONObject();
        String msg = null;
        String code = null;
        User user = userService.retrieveUserByToken();
        List<User> user_list = new ArrayList<User>();
        List<UserPro> userpro_list = new ArrayList<UserPro>();
        List<Likes> like_list = new ArrayList<Likes>();
        List<Sign> signs = signService.queryBySignUserId(user.getId());
        List<Sign> signs1 = signService.queryBySignedUserId(user.getId());
        signs.addAll(signs1);
        user_list = user_service.get_all_user(user.getId());
        like_list = user_service.get_all_likes(user.getId());
        try {
            int len_1 = user_list.size();
            int len_2 = like_list.size();
            int flag = 0;
            for (int i = 0; i < len_1 - flag; i++) {
                for (int j = 0; j < len_2; j++) {
//    			System.out.println(i);
                    if (user_list.get(i).getId().equals(like_list.get(j).getLikedUserId())) {
                        user_list.remove(i);
                        flag++;//长度减一
                        i--;//下标减一
                        break;
                    }
                }
            }
            int len_3 = user_list.size();
            for (int i = 0; i < len_3; i++) {
                UserPro tmp = new UserPro();
                tmp.setId(user_list.get(i).getId());
                tmp.setUsername(user_list.get(i).getUsername());
                tmp.setSchoolName(user_list.get(i).getSchoolName());
                tmp.setContact(user_list.get(i).getContact());
                tmp.setAddress(user_list.get(i).getAddress());
                tmp.setTelephone(user_list.get(i).getTelephone());
                tmp.setUpdateTime(user_list.get(i).getUpdateTime());
                tmp.setSchoolCode(user_list.get(i).getSchoolCode());
                tmp.setLocation(user_list.get(i).getLocation());
                tmp.setAddTime(user_list.get(i).getAddTime());
                tmp.setLastLoginIp(user_list.get(i).getLastLoginIp());
                tmp.setLastLoginTime(user_list.get(i).getLastLoginTime());
                tmp.setDeleted(user_list.get(i).getDeleted());
                tmp.setAccountStatus(user_list.get(i).getAccountStatus());
                tmp.setProfession(user_list.get(i).getProfession());
                tmp.setCountry(user_list.get(i).getCountry());
                tmp.setWebsite(user_list.get(i).getWebsite());
                tmp.setLogo("/files/" + user_service.get_logo(user_list.get(i).getId()));
                userpro_list.add(tmp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = "未知错误，请联系服务器管理员";
            code = "-1";
            result.put("msg", msg);
            result.put("code", code);
        }
        msg = "success";
        code = "200";
        result.put("msg", msg);
        result.put("code", code);
        result.put("data", userpro_list);
        return result;
    }


    @ResponseBody
    @GetMapping("/retrieveTheUsersThatLikeMe")
    @ApiOperation(value = "获取对我有意向的用户成功", notes = "获取对我有意向的用户成功")
    @PreAuthorize("hasRole('USER')")
    public String theUserThatLikesMe() {
        User user = userService.retrieveUserByToken();
        List<Likes> likes = likeService.queryByLikedUserId(user.getId());
        List<User> users = new LinkedList<>();
        for (Likes like : likes) {
            User u = userService.queryById(like.getLikeUserId(), User.Column.id, User.Column.schoolName);
            users.add(u);
        }
        return ResponseUtil.build(HttpStatus.OK.value(), "获取对我有意向的用户成功", users);
    }

    @ResponseBody
    @GetMapping("/choosedme")
    @ApiOperation(value = "获取已选当前用户但是当前用户未选该校的列表", notes = "获取已选当前用户但是当前用户未选该校的列表")
    @PreAuthorize("hasRole('USER')")
    public JSON choosedme() {
        String msg = null;
        String code = null;
        JSONObject result = new JSONObject();
        User user = userService.retrieveUserByToken();
        List<Likes> my_like = new ArrayList<Likes>();
        List<Likes> like_me = new ArrayList<Likes>();
        try {
            my_like = user_service.get_all_likes(user.getId());
            like_me = user_service.get_like_me(user.getId());
            int len_1 = like_me.size();
            int len_2 = my_like.size();
            System.out.println(len_1);
            System.out.println(len_2);
            int flag = 0;
            for (int i = 0; i < len_1 - flag; i++) {
                for (int j = 0; j < len_2; j++) {
                    if (like_me.get(i).getLikeUserId().equals(my_like.get(j).getLikedUserId())) {
                        //		System.out.println(like_me.get(i).getLikeuserid().equals(my_like.get(j).getLikeduserid()));
                        //			System.out.println(like_me.get(i).getLikeuserid());
                        //				System.out.println(my_like.get(j).getLikeduserid());
                        like_me.remove(i);
                        //  				System.out.println(flag);
                        flag++;//移除后list长度-1
                        i--;//移除后后面元素下标-1
                        break;
                    }
                }
            }
        } catch (Exception e) {
            msg = "未知错误，请联系服务器管理员";
            code = "-1";
            result.put("msg", msg);
            result.put("code", code);
            e.printStackTrace();
            return result;
        }
        msg = "success";
        code = "200";
        result.put("msg", msg);
        result.put("code", code);
        result.put("data", like_me);
        return result;
    }


//    @ResponseBody
//    @GetMapping("/show/{signId}")
//    @ApiOperation(value = "我的意向及签约->签约证书查看", notes = "签约证书查看")
//    @PreAuthorize("hasRole('USER')")
//    public String show(@ApiParam(value = "签约证书的id", example = "1") @PathVariable("signId") Integer signId) throws UserNotFoundException {
//        Sign sign = signService.findById(signId);
//        if (sign == null) {
//            return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), "该则签约id不存在！");
//        }
//        Certification certification = new Certification();
//        certification.setSignId(sign.getId());
//        certification.setWitness(springFilePath + witness);
//        certification.setSignUserDate(sign.getUpdateTime().toLocalDate().toString());
//        certification.setSignedUserDate(sign.getUpdateTime().toLocalDate().toString());
//        commonUtil.fill(certification, sign.getSignuserid(), sign.getSigneduserid());
//        return ResponseUtil.build(HttpStatus.OK.value(), "查看该则签约成功！",certification);
//    }
//
//    private void clean(Likes like) {
//        like.setAddTime(null);
//        like.setDeleted(null);
//        like.setLikeuserid(null);
//        like.setLikeschoolname(null);
//        like.setUpdateTime(null);
//    }		

//    @ResponseBody
//    @GetMapping("/download/{signId}")
//    @PreAuthorize("hasRole('USER')")
//    @ApiOperation(value = "下载签约证书", notes = "下载签约证书")
//    public String download(@PathVariable("signId") Integer signId) throws UserNotFoundException {
//        List<Sign> signs = signService.querySelective(signId, null, null, null, null, null, null, null, null, null);
//        if (signs.size() == 0) {
//            return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), "该则签约id不存在！");
//        }
//        Sign sign = signs.get(0);
//        User user = userService.retrieveUserByToken();
//        if (!sign.getSignuserid().equals(user.getId())) {
//            return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), "当前用户与目标的签约id不符！");
//        }
//
//        List<Pics> userLogos = picsService.querySelective(null, user.getId(), FileEnum.LOGO.value());
//        if (userLogos.size() == 0) {
//            return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), "还未上传学校logo！");
//        }
//
//        List<Pics> useredLogos = picsService.querySelective(null, sign.getSigneduserid(), FileEnum.LOGO.value());
//        if (useredLogos.size() == 0) {
//            return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), "目标学校还未上传学校logo！");
//        }
//
//        User usered = userService.findById(sign.getSigneduserid());
//
//        String country1 = user.getCountry();
//        String logo1 = userLogos.get(0).getLocation();
//        String school1 = user.getSchoolname();
//        String job1 = user.getProfession();
//
//        String country2 = usered.getCountry();
//        String logo2 = useredLogos.get(0).getLocation();
//        String school2 = usered.getSchoolname();
//        String job2 = usered.getProfession();
//
//        Map<String, Object> datas = new HashMap<String, Object>() {
//            {
//
//                put("country1", country1);
//                put("country2", country2);
//                put("school1", school1);
//                put("school2", school2);
//                put("job1", job1);
//                put("job2", job2);
//
//                put("logo1", new PictureRenderData(90, 90, ".jpg", BytePictureUtils.getUrlByteArray(logo1)));
//                put("logo2", new PictureRenderData(90, 90, ".jpg", BytePictureUtils.getUrlByteArray(logo2)));
////                put("logo3", new PictureRenderData(90, 90, ".jpg", BytePictureUtils.getUrlByteArray(logo3)));
////                put("name1", new PictureRenderData(100, 50, ".jpg", BytePictureUtils.getUrlByteArray(name1)));
////                put("name2", new PictureRenderData(100, 50, ".jpg", BytePictureUtils.getUrlByteArray(name2)));
//                Date date = new Date();
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                String time = sdf.format(date);
//                put("date1", time);
//                put("date2", time);
//            }
//        };
//        return null;
//    }


    @ResponseBody
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/get_Word")
    @ApiOperation(value = "下载签约证书", notes = "下载签约证书")
    public JSON generateWord
            (@ApiParam(example = "http://175.24.4.196/files/ddcc226b-ee92-4a39-84ed-2842ecd400c1.jpg", value = "当前用户logo访问地址") String
                     logo1,
             @ApiParam(example = "http://175.24.4.196/files/ddcc226b-ee92-4a39-84ed-2842ecd400c1.jpg", value = "被签约用户logo访问地址") String
                     logo2,
             @ApiParam(example = "GDUFS", value = "当前用户学校名称") String school1,
             @ApiParam(example = "HUA NAN LI GONG", value = "被签约用户学校名称") String school2,
             @ApiParam(example = "http://175.24.4.196/files/ddcc226b-ee92-4a39-84ed-2842ecd400c1.jpg", value = "当前用户签章访问地址") String
                     name1,
             @ApiParam(example = "http://175.24.4.196/files/ddcc226b-ee92-4a39-84ed-2842ecd400c1.jpg", value = "被签约用户签章访问地址") String
                     name2,
             @ApiParam(example = "headmaster", value = "当前用户职务") String job1,
             @ApiParam(example = "headmaster", value = "被签约用户职务") String job2,
             @ApiParam(example = "PRC", value = "当前用户所属国家") String country1,
             @ApiParam(example = "USA", value = "被签约用户所属国家") String country2,
             HttpServletRequest request,
             HttpServletResponse response) throws IOException {
        //ͼƬ·������ע������linux����windows
        String msg = null;
        String code = null;
        JSONObject result = new JSONObject();
        String path = request.getServletContext().getRealPath("");//��ȡ��Ŀ��̬����·��
        System.out.println(path);
        String wordtmp = filePath + "tmp.docx";//TODO
//        String wordtmp = "d:/tmp.docx";//TODO

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

        //协会的图片logo
        //TODO 改正ip 以及 images,  files
        String logo3 = "http://124.156.153.105/files/AUPFLOGO_1.jpg";
        String logo4 = "http://124.156.153.105/files/AUPFLOGO_2.jpg";
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
                put("logo4", new PictureRenderData(520, 70, ".jpg", BytePictureUtils.getUrlByteArray(logo4)));
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

//    @ResponseBody
//    @GetMapping("/select_likes")
//    @ApiOperation(value = "获取当前用户所有的意向列表,其他意向也可使用，",notes = "获取当前用户所有的意向列表，且返回对方是否喜欢自己")
//    public JSON select_sch(@ApiParam(example = "1", value = "当前用户id") Integer id) {
//        String msg = null;
//        String code = null;
//        int flag = 0;
//        JSONObject result = new JSONObject();
//        if (id == null) {
//            msg = "school_id missing";
//            code = "-1";
//            result.put("msg", msg);
//            result.put("code", code);
//            return result;
//        }
//        List<likelist> list = new ArrayList<likelist>();
//        try {
//            list = user_service.select_likes(id);
//        } catch (java.lang.NullPointerException e) {
//            e.printStackTrace();
//            msg = "null like";
//            code = "-2";
//            result.put("msg", msg);
//            result.put("code", code);
//            return result;
//        }
//        int len = list.size();
//        for (int i = 0; i < len; i++) {
//            if (user_service.select_both(list.get(i).getSch_name(), id)) {
//                list.get(i).setFlag(true);
//            }
//        }
//        msg = "success";
//        code = "200";
//        result.put("msg", msg);
//        result.put("code", code);
//        result.put("list", list);
//        return result;
//    }

    @ResponseBody
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/get_certi")
    @ApiOperation(value = "在线查看签约证书", notes = "在线查看签约证书")
    public JSON get_certi(@ApiParam(example = "1", value = "当前用户id") @RequestParam("host_id") Integer host_id,
                          @ApiParam(example = "2", value = "被签约用户的id") @RequestParam("liked_id") Integer liked_id) throws UnknownHostException {
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
        } catch (NullPointerException e) {
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
        } catch (NullPointerException e) {
            e.printStackTrace();
            msg = "null picture";
            code = "-3";
            result.put("msg", msg);
            result.put("code", code);
            return result;
        }
        try {
            if (pic_1.get(0).getType().equals(2)) {
                logo1 = "http://" + ia.getHostAddress() +
                        springFilePath +
                        pic_1.get(0).getLocation();
                name1 = "http://" + ia.getHostAddress() +
                        springFilePath +
                        pic_1.get(1).getLocation();
            } else {
                logo1 = "http://" + ia.getHostAddress() +
                        springFilePath +
                        pic_1.get(1).getLocation();
                name1 = "http://" + ia.getHostAddress() +
                        springFilePath +
                        pic_1.get(0).getLocation();
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            msg = "logo or signature picture missing";
            code = "-4";
            result.put("msg", msg);
            result.put("code", code);
            return result;
        }
        try {
            if (pic_2.get(0).getType().equals(2)) {
                logo2 = "http://" + ia.getHostAddress() +
                        springFilePath +
                        pic_2.get(0).getLocation();
                name2 = "http://" + ia.getHostAddress() +
                        springFilePath +
                        pic_2.get(1).getLocation();
            } else {
                logo2 = "http://" + ia.getHostAddress() +
                        springFilePath +
                        pic_2.get(1).getLocation();
                name2 = "http://" + ia.getHostAddress() +
                        springFilePath +
                        pic_2.get(0).getLocation();
            }
        } catch (IndexOutOfBoundsException e) {
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



