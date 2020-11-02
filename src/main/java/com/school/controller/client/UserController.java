package com.school.controller.client;

import com.school.dao.UserMapper;
import com.school.dto.SimplePage;
import com.school.dto.SimpleUser;
import com.school.dto.UserPro;
import com.school.exception.*;
import com.school.model.Pics;
import com.school.model.User;
import com.school.model.UserExample;
import com.school.service.golden.userservice;
import com.school.service.impl.EmailServiceImpl;
import com.school.service.impl.PicsServiceImpl;
import com.school.service.impl.UserServiceImpl;
import com.school.utils.AssertUtil;
import com.school.utils.FileEnum;
import com.school.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Api(tags = {"参会学校|(选择签约->勾选参会学校中高校搜索)"}, value = "参会学校")
@RestController
@RequestMapping("/api/client/user")
public class UserController {
    @Autowired
    private EmailServiceImpl emailService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${file.path}")
    private String filePath;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private PicsServiceImpl picsService;
    @Value("${spring.file.path}")
    private String springFilePath;
    @Resource
    private UserMapper userMapper;

    @Autowired
    userservice user_service;
    String springFiles = "/home/springboot/files/";

    /**
     * 依据 retrieveVerificationCode发过去的验证码重新设置密码
     */
    @PostMapping("/resetPassword")
    @ApiOperation(value = "用户重置密码", notes = "需要username以及发给该账号邮箱的code（时限3分钟），以及newPassword")
    public String resetPassword(@ApiParam(example = "123@qq.com", value = "待重置密码的用户名，即邮箱号") @RequestParam("userName") String username,
                                @ApiParam(example = "123", value = "新密码，前端加过密的") @RequestParam("newPassword") String newPassword,
                                @ApiParam(example = "1234", value = "发给该用户邮箱的验证码") @RequestParam("code") String code,
                                HttpServletRequest request) throws EmailVerificationCodeNullPointerException, EmailWrongFormatException, UsernameNullPointerException, EmailVerificationCodeIllegalArgumentException, UserNotFoundException {
        logger.info("[" + request.getRemoteAddr() + "] " + "is resetting his password!");
        AssertUtil.isValidMail(username, "用户名邮箱格式有误！");
        AssertUtil.emailVerificationCodeNotNull(code, "验证码不应为空！");
        Assert.notNull(newPassword, "新密码不应为空！");
        emailService.resetPassword(username, code, newPassword);
        return ResponseUtil.build(HttpStatus.OK.value(), "重设密码成功！", null);
    }

    @PostMapping("/forgetPassword")
    @ApiOperation(value = "用户忘记密码", notes = "传入一个邮箱账号username")
    public String retrieveVerificationCode(@ApiParam(example = "123@qq.com", value = "谁忘记了密码（用户名）") @RequestParam String username,
                                           HttpServletRequest request) throws EmailNotFoundException, UsernameNullPointerException, EmailWrongFormatException {
        logger.info("[" + request.getRemoteAddr() + "] is retrieving a verificationCode for his account!");
        AssertUtil.usernameNotNull(username, "邮箱号不应为空！");
        AssertUtil.isValidMail(username, "邮箱格式错误!");
        emailService.sendVerificationCode("签约系统验证码", "签约系统验证码（用于重置密码，3分钟内有效）", username, 180, TimeUnit.SECONDS);//忘记密码
        return ResponseUtil.build(HttpStatus.OK.value(), "获取邮箱验证码成功！", null);
    }


    @GetMapping("/listSearch")
    @ApiOperation(
            value = "参会学校->所有参会学校",
            notes = "输入高校名进行搜索"
    )
    @PreAuthorize("hasRole('USER')")
    public String listSearch(@ApiParam(example = "广外", value = "schoolName") @RequestParam(required = false) String schoolName,
                             @ApiParam(example = "1", value = "分页使用，要第几页的数据") @RequestParam(value = "page", required = false) Integer page, @ApiParam(example = "10", value = "分页使用，要该页的几条数据") @RequestParam(value = "pageSize", required = false) Integer pageSize,
                             @ApiParam(example = "update_time", value = "排序方式，从数据库中要的数据使用什么进行排序，如 add_time,update_time") @RequestParam(defaultValue = "add_time") String sort,
                             @ApiParam(example = "desc", value = "排序方式，升序asc还是降序desc") @RequestParam(defaultValue = "desc") String order) {
        List<User> users = this.userService.querySelectiveLike((Integer) null, (String) null, schoolName, (String) null, (String) null, (String) null, (Integer) null, (String) null, (String) null, (Integer) null, (String) null, (String) null, (Integer) null, (String) null, page, pageSize, sort, order);
        Integer size = this.userService.count(schoolName);
        List<User> result = new LinkedList();
        Iterator var9 = users.iterator();
        while (var9.hasNext()) {
            UserPro user = (UserPro) var9.next();
            user.setLogo(springFiles + user_service.get_logo(user.getId()));
//            SimpleUser simpleUser = new SimpleUser();
//            fill(user, simpleUser);
            clean(user);
            result.add(user);
        }
        SimplePage<List<SimpleUser>> simplePage = new SimplePage(size, result);
        return ResponseUtil.build(HttpStatus.OK.value(), "搜索成功,包括高校名关键字！", simplePage);
    }

    private void clean(User user) {
        user.setAccountStatus(null);
//        user.setUsername(null);
        user.setPassword(null);
        user.setUpdateTime(null);
        user.setLocation(null);
        user.setAddTime(null);
        user.setLastLoginIp(null);
        user.setLastLoginTime(null);
//        user.setAvatarurl(null);
        user.setDeleted(null);
    }

    private void fill(User user, SimpleUser simpleUser) {
//        simpleUser.setUsername(user.getUsername());
//        simpleUser.setAddress(user.getAddress());
        simpleUser.setContact(user.getContact());
        simpleUser.setId(user.getId());
        simpleUser.setSchoolName(user.getSchoolName());
        simpleUser.setProfession(user.getProfession());
//        simpleUser.setTelephone(user.getTelephone());
    }

//    @PostMapping("/upload")
//    @PreAuthorize("hasAnyRole('USER')")
//    @ApiOperation(value = "上传头像", notes = "用户上传头像")
//    public Object upload(@ApiParam(example = "file", value = "该文件流,参数名应该为file") @RequestParam("file") MultipartFile file) throws IOException, FileFormattingException, UserNotFoundException {
//        if (!file.isEmpty()) {
//            String fileName = file.getOriginalFilename();
//            String format = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
//            AssertUtil.isPicture(format);
//            //上传图片并插入数据库中
//            upload(file, format, FileEnum.AVATAR_URL);
//            return ResponseUtil.build(HttpStatus.OK.value(), "上传图片成功！", null);
//        }
//        return ResponseUtil.build(HttpStatus.OK.value(), "图片为空！", null);
//    }

//    private void upload(MultipartFile file, String format, FileEnum fileEnum) throws IOException, UserNotFoundException {
//        User user = userService.retrieveUserByToken();
//        picsService.upload(user.getId(), fileEnum.value(), file);
//    }

    @PostMapping("/upload/logo")
    @ApiOperation(
            value = "提交签约材料->logo",
            notes = "上传学校logo"
    )
    @PreAuthorize("hasRole('USER')")
    public String uploadLogo(@RequestParam("logo") MultipartFile file) throws IOException, UserNotFoundException {
        if (file.isEmpty()) {
            return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), "上传文件不能为空！", (Object) null);
        } else {
            User user = userService.retrieveUserByToken();
            Pics upload = this.picsService.upload(user.getId(), FileEnum.LOGO.value(), file);
            return ResponseUtil.build(HttpStatus.OK.value(), "上传学校logo成功！", this.springFilePath + upload.getLocation());
        }
    }

    @PostMapping({"/upload/signature"})
    @ApiOperation(
            value = "提交签约材料->校长签章",
            notes = "上传校长签章"
    )
    @PreAuthorize("hasRole('USER')")
    public String uploadSignature(@RequestParam("signature") MultipartFile file) throws IOException, UserNotFoundException {
        if (file.isEmpty()) {
            return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), "上传文件不能为空！", (Object) null);
        } else {
            User user = userService.retrieveUserByToken();
            Pics upload = this.picsService.upload(user.getId(), FileEnum.SIGNATURE.value(), file);
            return ResponseUtil.build(HttpStatus.OK.value(), "上传校长签章成功！", this.springFilePath + upload.getLocation());
        }
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR','USER')")
    @ApiOperation(value = "获取当前用户信息", notes = "获取用户信息")
    @GetMapping("/retrieveUserInfo")
    public Object retrieveUserInfo() {
        User user = userService.retrieveUserByToken();

        List<Pics> logos = this.picsService.querySelective(null,user.getId(), FileEnum.LOGO.value());
        String logo = logos.size() == 0 ? null : this.springFilePath + ((Pics) logos.get(0)).getLocation();
        List<Pics> signatures = this.picsService.querySelective(null,user.getId(), FileEnum.SIGNATURE.value());
        String signature = signatures.size() == 0 ? null : this.springFilePath + ((Pics) signatures.get(0)).getLocation();
        SimpleUser simpleUser = new SimpleUser();

        this.fillNexessaryUserInfo(user, simpleUser);
        simpleUser.setLogo(logo);
        simpleUser.setSignature(signature);
        return ResponseUtil.build(HttpStatus.OK.value(), "获取用户信息成功!", simpleUser);
    }


    private void fillNexessaryUserInfo(User user, SimpleUser simpleUser) {
        simpleUser.setProfession(user.getProfession());
        simpleUser.setUsername(user.getUsername());
        simpleUser.setAddress(user.getAddress());
        simpleUser.setContact(user.getContact());
        simpleUser.setId(user.getId());
        simpleUser.setSchoolName(user.getSchoolName());
        simpleUser.setTelephone(user.getTelephone());
    }

//    @PreAuthorize("hasAnyRole('ADMINISTRATOR','USER')")
//    @ApiOperation(value = "④　参会学校展示", notes = "，获取所有的用户信息，")
//    @GetMapping("/retrieveAllUserInfo")
//    public Object retrieveAllUserInfo() {
//        List<User> users = userService.querySelectiveLike(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
//        clearPassword(users);
//        updateAvatarUrl(users);
//        return ResponseUtil.build(HttpStatus.OK.value(), "获取用户信息成功!", users);
//    }

//    private void updateAvatarUrl(List<User> users) {
//        for (User user : users) {
//            user.setAvatarurl(springFilePath + user.getAvatarurl());
//        }
//    }

    //    private void clearPassword(List<User> users) {
//        for (User user : users) {
//            user.setPassword("[PASSWORD]");
//        }
//    }
    //-------------------------------
    public Integer count(String schoolName) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        if (StringUtils.hasText(schoolName)) {
            criteria.andSchoolNameLike("%" + schoolName + "%");
        }

        return Math.toIntExact(this.userMapper.countByExample(userExample));
    }


}
