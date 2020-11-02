//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.school.controller.admin;

import com.school.exception.*;
import com.school.model.*;
import com.school.service.impl.*;
import com.school.utils.AssertUtil;
import com.school.utils.ResponseUtil;
import com.school.utils.RoleEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(
        value = "系统参数配置",
        tags = {"系统参数配置"}
)
@RestController
@RequestMapping({"/api/admin/sys"})
public class AdminSystemController {
//    private static final String likeJobGroup = "LIKE_GROUP";
//    private static final String onLikeJobName = "LIKE_ON";
//    private static final String offLikeJobName = "LIKE_OFF";
//
//    private static final String signJobGroup = "SIGN_GROUP";
//    private static final String onSignJobName = "SIGN_ON";
//    private static final String offSignJobName = "SIGN_OFF";
//
//    private static final String mouDownloadJobGroup = "MOU_DOWNLOAD_GROUP";
//    private static final String onMouDownloadJobName = "MOU_DOWNLOAD_ON";
//    private static final String offMouDownloadJobName = "MOU_DOWNLOAD_OFF";

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    RoleToAuthoritiesServiceImpl roleToAuthoritiesService;
    //    @Autowired
//    SchedulerManager schedulerManager;
    @Autowired
    EmailServiceImpl emailService;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    PicsServiceImpl picsService;
    @Autowired
    SignServiceImpl signService;
    @Autowired
    LikeServiceImpl likeService;
    @Autowired
    UserToRoleServiceImpl userToRoleService;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    @ApiOperation(value = "配置系统邮箱", notes = "配置系统邮箱")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping("/sysConfig")
    public Object sysConfig(@ApiParam(example = "123@qq.com", value = "系统邮箱账号") @RequestParam(value = "username", required = false) String username,
                            @ApiParam(example = "123456", value = "开启POP3后获得的授权码") @RequestParam(value = "code", required = false) String code,
                            HttpServletRequest request) throws EmailWrongFormatException, UsernameNullPointerException {
        if (StringUtils.hasText(username) && StringUtils.hasText(code)) {
            modifySystemEmail(username, code, request);
            return ResponseUtil.build(HttpStatus.OK.value(), "系统配置成功！");
        }
        return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), "系统邮箱账号或授权码不能为空！");
    }

    @GetMapping("/sysConfig")
    @ApiOperation(value = "获取系统邮箱配置", notes = "获取系统邮箱配置")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public Object retrieveSysConfig() {
//        emailService.modifySystemEmail;
        EmailServiceImpl.EmailInfo emailInfo = emailService.retrieveSystemEmailInfo();
        return ResponseUtil.build(HttpStatus.OK.value(), "获取系统设置成功！", emailInfo);
    }


//    @PreAuthorize("hasRole('ADMINISTRATOR')")
//    @PostMapping("/sysConfig")
//    public Object sysConfig(@ApiParam(value = "意向开始时间", example = "yyyy-MM-dd HH:mm:ss") @RequestParam(value = "likeStartTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime likeStartTime,
//                            @ApiParam(value = "意向结束时间", example = "yyyy-MM-dd HH:mm:ss") @RequestParam(value = "likeEndTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime likeEndTime,
//                            @ApiParam(value = "签约查看开始时间", example = "yyyy-MM-dd HH:mm:ss") @RequestParam(value = "signStartTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime signStartTime,
//                            @ApiParam(value = "签约查看结束时间", example = "yyyy-MM-dd HH:mm:ss") @RequestParam(value = "signEndTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime signEndTime,
//                            @ApiParam(value = "mou下载开始时间", example = "yyyy-MM-dd HH:mm:ss") @RequestParam(value = "mouStartTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime mouStartTime,
//                            @ApiParam(value = "mou下载结束时间", example = "yyyy-MM-dd HH:mm:ss") @RequestParam(value = "mouEndTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime mouEndTime,
//                            @ApiParam(example = "123@qq.com", value = "系统邮箱账号") @RequestParam(value = "username", required = false) String username,
//                            @ApiParam(example = "123456", value = "开启POP3后获得的授权码") @RequestParam(value = "code", required = false) String code,
//                            HttpServletRequest request) {
//
//        try {
//            if (Objects.nonNull(likeStartTime) && Objects.nonNull(likeEndTime)) {
//                String s = controlLikes(likeStartTime, likeEndTime);
//            }
//            if (Objects.nonNull(signStartTime) && Objects.nonNull(signEndTime)) {
//                String s1 = controlSings(signStartTime, signEndTime);
//            }
//            if (Objects.nonNull(mouStartTime) && Objects.nonNull(mouEndTime)) {
//                String s2 = controlMouDownload(mouStartTime, mouEndTime);
//            }
//        } catch (SchedulerException e) {
//            e.printStackTrace();
//        }
//        try {
//            if (StringUtils.hasText(username) && StringUtils.hasText(code)) {
//                modifySystemEmail(username, code, request);
//            }
//        } catch (EmailWrongFormatException | UsernameNullPointerException e) {
//            e.printStackTrace();
//        }
//        return ResponseUtil.build(HttpStatus.OK.value(), "系统配置成功！");
//    }

    //    @PostMapping("/configLikesPeriod")
//    @ApiOperation(value = "配置用户意向时段", notes = "配置用户可以进行意向的时段")
//    @PreAuthorize("hasAnyRole('ADMINISTRATOR')")
//    private String controlLikes(@ApiParam(value = "开始时间", example = "yyyy-MM-dd HH:mm:ss") @RequestParam("startTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
//                                @ApiParam(value = "结束时间", example = "yyyy-MM-dd HH:mm:ss") @RequestParam("endTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) throws SchedulerException {
//        schedulerManager.startJob(startTime, onLikeJobName, likeJobGroup, LikeOperationOnJob.class);
//        schedulerManager.startJob(endTime, offLikeJobName, likeJobGroup, LikeOperationOffJob.class);
//        return ResponseUtil.build(HttpStatus.OK.value(), "限制用户意向期间的成功", null);
//    }

    //    @ApiOperation(value = "配置用户签约时段", notes = "配置用户可以进行签约的时间")
//    @PostMapping("/configSignPeriod")
    //    @PreAuthorize("hasAnyRole('ADMINISTRATOR')")
//    private String controlSings(@ApiParam(value = "开始时间", example = "yyyy-MM-dd HH:mm:ss") @RequestParam("startTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
//                                @ApiParam(value = "结束时间", example = "yyyy-MM-dd HH:mm:ss") @RequestParam("endTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) throws SchedulerException {
//        schedulerManager.startJob(startTime, onSignJobName, signJobGroup, SignOperationOnJob.class);
//        schedulerManager.startJob(endTime, offSignJobName, signJobGroup, SignOperationOffJob.class);
//        return ResponseUtil.build(HttpStatus.OK.value(), "限制用户意向期间的成功", null);
//    }

    //    @ApiOperation(value = "配置用户MOU下载时段", notes = "配置用户MOU下载时段")
//    @PostMapping("/configMouDownloadPeriod")
    //    @PreAuthorize("hasAnyRole('ADMINISTRATOR')")
//    private String controlMouDownload(@ApiParam(value = "开始时间", example = "yyyy-MM-dd HH:mm:ss") @RequestParam("startTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
//                                      @ApiParam(value = "结束时间", example = "yyyy-MM-dd HH:mm:ss") @RequestParam("endTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) throws SchedulerException {
//        schedulerManager.startJob(startTime, onMouDownloadJobName, mouDownloadJobGroup, MouDownloadOperationOnJob.class);
//        schedulerManager.startJob(endTime, offMouDownloadJobName, mouDownloadJobGroup, MouDownloadOperationOffJob.class);
//        return ResponseUtil.build(HttpStatus.OK.value(), "限制用户意向期间的成功", null);
//    }

    //    @PostMapping("/modifySystemEmail")
//    @ApiOperation(value = "修改系统邮箱", notes = "修改系统邮箱，须有邮箱号以及该邮箱号的授权码")
    private void modifySystemEmail(@ApiParam(example = "123@qq.com", value = "系统邮箱账号") @RequestParam("username") String username,
                                   @ApiParam(example = "123456", value = "开启POP3后获得的授权码") @RequestParam("code") String code,
                                   HttpServletRequest request) throws EmailWrongFormatException, UsernameNullPointerException {
        logger.info("[" + request.getRemoteAddr() + "] 管理员修改默认系统邮箱！");
        Assert.notNull(code, "管理端设置系统的邮箱的授权码不应为空！");
        AssertUtil.isValidMail(username, "设置的系统邮箱不能为空！");
        emailService.modifySystemEmail(username, code);
        ResponseUtil.build(HttpStatus.OK.value(), "修改系统邮箱成功！");
    }

    @PostMapping("/clean")
    @ApiOperation(value = "清除数据", notes = "清除所有数据")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public String cleanData() throws UserNotFoundException, UserSignCorrespondException, SignNotFoundException, UserLikesNotCorrespondException, LikesNotFoundException {
        List<User> users = userService.querySelectiveLike(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        for (User user : users) {
            List<Usertorole> usertoroles = userToRoleService.querySelective(null, user.getId(), null);
            if (usertoroles.size() != 0) {
                Usertorole usertorole = usertoroles.get(0);
                if (usertorole.getRoleId().equals(RoleEnum.ADMINISTRATOR.value())) {
                    continue;
                }
            }
            userService.delete(user);
        }
        return ResponseUtil.build(HttpStatus.OK.value(), "删除所有数据成功！");

    }
//    @Component
//    class MouDownloadOperationOnJob implements Job {
//        @Autowired
//        private RoleToAuthoritiesServiceImpl roleToAuthoritiesService;
//        @Override
//        public void execute(JobExecutionContext context) throws JobExecutionException {
//            roleToAuthoritiesService.addAuthority(RoleEnum.USER.value(), "user::mouDownload");
//        }
//    }
//
//    @Component
//    class MouDownloadOperationOffJob implements Job {
//        @Autowired
//        private RoleToAuthoritiesServiceImpl roleToAuthoritiesService;
//        @Override
//        public void execute(JobExecutionContext context) throws JobExecutionException {
//            roleToAuthoritiesService.addAuthority(RoleEnum.USER.value(), "user::mouDownload");
//        }
//    }
//
//    @Component
//    class LikeOperationOnJob implements Job {
//        @Autowired
//        private RoleToAuthoritiesServiceImpl roleToAuthoritiesService;
//
//        @Override
//        public void execute(JobExecutionContext context) {
//            roleToAuthoritiesService.addAuthority(RoleEnum.USER.value(), "user::like");
//        }
//    }
//
//    @Component
//    class LikeOperationOffJob implements Job {
//        @Autowired
//        private RoleToAuthoritiesServiceImpl roleToAuthoritiesService;
//
//        @Override
//        public void execute(JobExecutionContext context) throws JobExecutionException {
//            roleToAuthoritiesService.removeAuthority(RoleEnum.USER.value(), "user::like");
//        }
//    }
//
//    @Component
//    class SignOperationOnJob implements Job {
//        @Autowired
//        private RoleToAuthoritiesServiceImpl roleToAuthoritiesService;
//
//        @Override
//        public void execute(JobExecutionContext context) throws JobExecutionException {
//            roleToAuthoritiesService.addAuthority(RoleEnum.USER.value(), "user::sign");
//        }
//    }
//
//    @Component
//    class SignOperationOffJob implements Job {
//        @Autowired
//        private RoleToAuthoritiesServiceImpl roleToAuthoritiesService;
//
//        @Override
//        public void execute(JobExecutionContext context) throws JobExecutionException {
//            roleToAuthoritiesService.removeAuthority(RoleEnum.USER.value(), "user::sign");
//        }
//    }
}
