//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.school.controller.admin;

import com.school.dto.FullUser;
import com.school.dto.SimplePage;
import com.school.dto.SimpleUser;
import com.school.exception.*;
import com.school.model.Pics;
import com.school.model.User;
import com.school.service.impl.*;
import com.school.utils.FileEnum;
import com.school.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Api(
        tags = {"高校信息管理，管理端用户"},
        value = "高校信息"
)
@RestController
@RequestMapping({"/api/admin/user"})
public class AdminUserController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private PicsServiceImpl picsService;
    @Value("${spring.file.path}")
    private String springFilePath;
    @Autowired
    private EmailServiceImpl emailService;
    @Autowired
    private LikeServiceImpl likeService;
    @Autowired
    private SignServiceImpl signService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AdminUserController() {
    }

    private static Set<String> skipFieldSet;

    static {
        skipFieldSet = new HashSet<>();
        skipFieldSet.add("password");
        skipFieldSet.add("location");
        skipFieldSet.add("add_time");
        skipFieldSet.add("update_time");
        skipFieldSet.add("lastloginip");
        skipFieldSet.add("lastlogintime");
        skipFieldSet.add("deleted");
        skipFieldSet.add("avatarurl");
        skipFieldSet.add("accountstatus");
        skipFieldSet.add("updatetime");
        skipFieldSet.add("addtime");
        skipFieldSet.add("id");

    }

    @GetMapping("/checkPassword")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public Object checkPassword(@RequestParam(value = "password") String rawPassword) {
        User user = userService.retrieveUserByToken();
        boolean matches = passwordEncoder.matches(rawPassword, user.getPassword());
        return ResponseUtil.build(HttpStatus.OK.value(), "匹配密码完成！", matches);
    }

    @GetMapping("/sendVertificationCode")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR')")
    public Object sendVertificationCode() {
        User user = userService.retrieveUserByToken();
        emailService.sendVerificationCode("pop3临时授权码", "获取查看pop3临时授权码成功(3分钟内有效)>", user.getUsername(), 3, TimeUnit.MINUTES);
        return ResponseUtil.build(HttpStatus.OK.value(), "获取查看pop3临时授权码成功！", null);
    }

    @GetMapping("/checkVertificationCode")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR')")
    public Object checkVertificationCode(@RequestParam("code") String code) throws EmailVerificationCodeIllegalArgumentException, EmailVerificationCodeNullPointerException {
        User user = userService.retrieveUserByToken();
        boolean b = emailService.checkVertificationCode(user.getUsername(), code);
        return ResponseUtil.build(HttpStatus.OK.value(), "检验查看pop3时授权码成功！", b);
    }


    @ApiOperation(
            value = "导出报名表",
            notes = "导出报名表(swagger-bootstarp无法下载，会直接显示内容，因此要测试可以直接浏览器访问该地址)"
    )
    @GetMapping({"/exportRegistrationForm"})
//    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public void exportRegistrationForm(@ApiParam(example = "[1,2,3,4]", value = "多个用户的id数组") @RequestParam(value = "userIds", required = false) Integer[] userIds, HttpServletResponse response) throws IOException {
        List<User> users = null;
        if (userIds == null || userIds.length == 0) {
            users = userService.querySelectiveLike((Integer) null, (String) null, (String) null, (String) null, (String) null, (String) null, (Integer) null, (String) null, (String) null, (Integer) null, (String) null, (String) null, (Integer) null, (String) null, (Integer) null, (Integer) null, (String) null, (String) null);
        } else {
            users = new LinkedList<>();
            for (Integer userId : userIds) {
                try {
                    User user = userService.findById(userId);
                    users.add(user);
                } catch (UserNotFoundException e) {
//                    e.printStackTrace();
                    logger.error("该用户id不存在->" + userId);
                }
            }
        }
        Workbook workbook = this.userService.exportRegistrationForm(users, skipFieldSet);
        String fileName = User.class.getSimpleName() + ".xls";
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        workbook.write(outputStream);
        outputStream.close();
        workbook.close();
    }

    @GetMapping({"/listSearch"})
    @ApiOperation(
            value = "高校信息管理->搜索/分页",
            notes = "输入搞高校名进行搜索"
    )
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public String search(@ApiParam(example = "1", value = "schoolName") @RequestParam(required = false) String schoolName,
                         @ApiParam(example = "1", value = "分页使用，要第几页的数据")
                         @RequestParam(value = "page", required = false) Integer page, @ApiParam(example = "10", value = "分页使用，要该页的几条数据") @RequestParam(value = "pageSize", required = false) Integer limit,
                         @ApiParam(example = "1", value = "排序方式，从数据库中要的数据使用什么进行排序，如 add_time,update_time")
                         @RequestParam(defaultValue = "add_time") String sort,
                         @ApiParam(example = "desc", value = "排序方式，升序asc还是降序desc") @RequestParam(defaultValue = "desc") String order) {
        List<User> users = this.userService.querySelectiveLike((Integer) null, (String) null, schoolName, (String) null, (String) null, (String) null, (Integer) null, (String) null, (String) null, (Integer) null, (String) null, (String) null, (Integer) null, (String) null, page, limit, sort, order);
        Integer size = this.userService.count(schoolName);
        clean(users);
//        List<SimpleUser> result = new LinkedList();
//        Iterator var9 = users.iterator();
//        while (var9.hasNext()) {
//            User user = (User) var9.next();
//            SimpleUser simpleUser = new SimpleUser();
//            this.fill(user, simpleUser);
//            result.add(simpleUser);
//        }
        SimplePage<List<SimpleUser>> simplePage = new SimplePage(size, users);
        return ResponseUtil.build(HttpStatus.OK.value(), "搜索成功,包括高校名关键字！", simplePage);
    }

    private void clean(List<User> users) {
        for (User user : users) {
            user.setUpdateTime(null);
            user.setUpdateTime(null);
//            user.setAvatarurl(null);
            user.setLastLoginIp(null);
            user.setLocation(null);
            user.setAccountStatus(null);
            user.setPassword(null);
            user.setDeleted(null);
        }
    }

    @PostMapping({"/importRegistrationForm"})
    @ApiOperation(
            value = "导入报名表",
            notes = "但对excel的字段名有严格要求，仅支持.xls以及.xlsx，请直接和我讨论这一块"
    )
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public String uploadFile(@ApiParam(value = "导入的excel文件", example = "test.xlsx") @RequestParam("registrationForm") MultipartFile file, HttpServletRequest request) {
        if (file.isEmpty()) {
            return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), "文件不能为空！", (Object) null);
        } else {
            new Thread(() -> {
                try {
                    this.userService.importRegistrationForm(file);
                } catch (FileFormattingException | IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | ExcelDataException | UsernameAlreadyExistException e) {
                    logger.warn(e.getMessage());
                }
            }).start();
            return ResponseUtil.build(HttpStatus.OK.value(), "录入excel数据成功！");
        }
    }

    @GetMapping({"/show/{userId}"})
    @ApiOperation(
            value = "查看报名表->通过id查询某一用户",
            notes = "通过id查询某一用户"
    )
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public String show(@PathVariable("userId") Integer id) throws UserNotFoundException {
//        User user = this.userService.findById(id);
        User user = this.userService.queryById(id, User.Column.id, User.Column.schoolName, User.Column.contact, User.Column.profession, User.Column.address, User.Column.telephone, User.Column.username, User.Column.address, User.Column.website);
        List<Pics> logos = this.picsService.querySelective((Integer) null, user.getId(), FileEnum.LOGO.value());
        String logo = logos.size() == 0 ? null : this.springFilePath + ((Pics) logos.get(0)).getLocation();
        List<Pics> signatures = this.picsService.querySelective((Integer) null, user.getId(), FileEnum.SIGNATURE.value());
        String signature = signatures.size() == 0 ? null : this.springFilePath + ((Pics) signatures.get(0)).getLocation();
        FullUser fullUser = new FullUser();
        fullUser.setUser(user);
        fullUser.setSignature(signature);
        fullUser.setLogo(logo);
        return ResponseUtil.build(HttpStatus.OK.value(), "获取用户信息成功!", fullUser);
    }

    private void fill(User user, SimpleUser simpleUser) {
        simpleUser.setUsername(user.getUsername());
        simpleUser.setAddress(user.getAddress());
        simpleUser.setContact(user.getContact());
        simpleUser.setId(user.getId());
        simpleUser.setSchoolName(user.getSchoolName());
        simpleUser.setTelephone(user.getTelephone());
    }

    @PostMapping({"/upload/logo/{userId}"})
    @ApiOperation(
            value = "上传logo",
            notes = "管理端上传某一学校logo"
    )
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public String uploadLogo(@RequestParam("logo") MultipartFile file, @ApiParam(example = "1", value = "被上传图片的用户的id") @PathVariable("userId") Integer userId) throws IOException, UserNotFoundException {
        if (file.isEmpty()) {
            return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), "上传文件不能为空！", (Object) null);
        } else {
            Pics upload = this.picsService.upload(userId, FileEnum.LOGO.value(), file);
            return ResponseUtil.build(HttpStatus.OK.value(), "上传学校logo成功！", this.springFilePath + upload.getLocation());
        }
    }

    @PostMapping({"/upload/signature/{userId}"})
    @ApiOperation(
            value = "上传校长签章",
            notes = "管理端上传校长签章"
    )
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public String uploadSignature(@RequestParam("signature") MultipartFile file, @ApiParam(example = "1", value = "被上传图片的用户的id") @PathVariable("userId") Integer userId) throws IOException, UserNotFoundException {
        if (file.isEmpty()) {
            return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), "上传文件不能为空！", (Object) null);
        } else {
            Pics upload = this.picsService.upload(userId, FileEnum.SIGNATURE.value(), file);
            return ResponseUtil.build(HttpStatus.OK.value(), "上传校长签章成功！", this.springFilePath + upload.getLocation());
        }
    }

    @PostMapping({"/add"})
    @ApiOperation(
            value = "导入单个高校信息",
            notes = "后台手动添加一个用户"
    )
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public Object create(@ApiParam(example = "123@qq.com", value = "用户名即为邮箱号") @RequestParam("username") String username,
                         @ApiParam(example = "GDUFS", value = "学校名") @RequestParam(value = "schoolName", required = false) String schoolName,
                         @ApiParam(example = "人名", value = "联系人") @RequestParam(value = "contact", required = false) String contact,
                         @ApiParam(example = "地址", value = "学校详细地址") @RequestParam(value = "address", required = false) String address,
                         @ApiParam(example = "111", value = "电话号码") @RequestParam(value = "telephone", required = false) String telephone,
                         @ApiParam(example = "111", value = "学校代码") @RequestParam(value = "schoolCode", required = false) String schoolCode,
                         @ApiParam(example = "XXX主任", value = "职务") @RequestParam(value = "profession", required = false) String profession,
                         @ApiParam(example = "website", value = "website") @RequestParam(value = "website", required = false) String website) throws UsernameAlreadyExistException, EmailNotFoundException {
        String password = String.valueOf(System.currentTimeMillis());
        List<User> users = userService.querySelectiveLike(null, username, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        if (users.size() > 0) {
            throw new UsernameAlreadyExistException("用户名已存在!");
        }
        this.userService.add(username, password, schoolName, contact, address, telephone, schoolCode, (String) null, (String) null, (LocalDateTime) null, "default.png", profession, 1, website);
        this.emailService.sendVerificationCode("签约系统", "用户临时授权码(三天内有效,如果过期了可以直接点击忘记密码重置密码~)", username, 3, TimeUnit.DAYS);
        return ResponseUtil.build(HttpStatus.OK.value(), "添加一个用户成功!", (Object) null);
    }


    @DeleteMapping({"/delete/{id}"})
    @ApiOperation(
            value = "删除用户",
            notes = "依据传入的id删除用户"
    )
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public Object delete(@PathVariable("id") Integer id) throws UserNotFoundException {
        this.userService.deleteById(id);
        this.likeService.deleteByUser(id);
        this.signService.deleteByUser(id);
        return ResponseUtil.build(HttpStatus.OK.value(), "删除一个用户成功!");
    }

    @PostMapping({"/update/{userId}"})
    @ApiOperation(
            value = "修改用户信息",
            notes = "更新用户信息"
    )
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public Object update(@ApiParam(example = "1", value = "待修改的用户的id") @PathVariable("userId") Integer id,
                         @ApiParam(example = "广外", value = "待修改的用户的学校名") @RequestParam(value = "schoolName", required = false) String schoolName,
                         @ApiParam(example = "联系人", value = "联系人") @RequestParam(value = "contact", required = false) String contact,
                         @ApiParam(example = "详细地址", value = "详细地址") @RequestParam(value = "address", required = false) String address,
                         @ApiParam(example = "电话", value = "电话") @RequestParam(value = "telephone", required = false) String telephone,
                         @ApiParam(example = "email", value = "用户名") @RequestParam(value = "username", required = false) String username,
                         @ApiParam(example = "profession", value = "职务") @RequestParam(value = "profession", required = false) String profession,
                         @ApiParam(example = "website", value = "网站") @RequestParam(value = "website", required = false) String website,
                         @ApiParam(example = "logo", value = "logo") @RequestParam(value = "logo", required = false) String logo,
                         @ApiParam(example = "signdature", value = "signature") @RequestParam(value = "signature", required = false) String signature) throws UserNotFoundException, UserLikesNotCorrespondException, LikesNotFoundException {
        User oldUser = this.userService.findById(id);
        //由于前期工作没做好，这里只能先这样处理，用户更新用户信息，signs表以及likes表也要变
        if (StringUtils.hasText(schoolName) && !oldUser.getSchoolName().equals(schoolName)) {
            likeService.updateSchoolName(oldUser.getId(), schoolName);
            signService.updateSchoolName(oldUser.getId(), schoolName);
        }

        User update = this.userService.update(id, username, (String) null, schoolName, contact, address, telephone, (String) null, (String) null, (String) null, (LocalDateTime) null, (Boolean) null, (String) null, (Integer) null, (String) profession, website);

        SimpleUser simpleUser = new SimpleUser();
        simpleUser.setLogo(logo);
        simpleUser.setSignature(signature);
        this.fill(update, simpleUser);
        return ResponseUtil.build(HttpStatus.OK.value(), "修改一个用户成功!", simpleUser);
    }

    @PostMapping("/openLogin")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @ApiOperation(
            value = "开放登录",
            notes = "开放登录"
    )
    public String openLogin() {
        new Thread(() -> {
            userService.openLogin();
        }).start();
        return ResponseUtil.build(HttpStatus.OK.value(), "开放登录！");
    }

    @PostMapping({"/listSearchUserLoginInfos"})
    @ApiOperation(
            value = "获取高校登陆信息",
            notes = "获取高校登陆信息"
    )
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public Object listSearchUserLoginInfos(@ApiParam(example = "1", value = "schoolName") @RequestParam(required = false) String schoolName,
                                           @ApiParam(example = "1", value = "分页使用，要第几页的数据") @RequestParam(value = "page", required = false) Integer page,
                                           @ApiParam(example = "10", value = "分页使用，要该页的几条数据") @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                           @ApiParam(example = "1", value = "排序方式，从数据库中要的数据使用什么进行排序，如 add_time,update_time") @RequestParam(defaultValue = "lastLoginTime", required = false) String sort,
                                           @ApiParam(example = "desc", value = "排序方式，升序asc还是降序desc") @RequestParam(defaultValue = "desc", required = false) String order) {
        List<User> users = userService.querySelectiveLike(null,
                null,
                schoolName,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                page,
                pageSize,
                sort,
                order);
        Integer size = userService.count(schoolName);
        for (User user : users) {
            user.setPassword(null);
            user.setAddTime(null);
            user.setUpdateTime(null);
//            user.setAvatarurl(null);
            user.setTelephone(null);
            user.setAddress(null);
            user.setDeleted(null);
            user.setContact(null);
            user.setAccountStatus(null);
        }
        SimplePage simplePage = new SimplePage(size, users);
        return ResponseUtil.build(HttpStatus.OK.value(), "获取高校信息成功！", simplePage);
    }


}
