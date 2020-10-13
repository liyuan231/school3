//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.school.controller.admin;

import com.school.component.schedule.SchedulerManager;
import com.school.exception.*;
import com.school.model.Likes;
import com.school.model.Pics;
import com.school.model.Sign;
import com.school.model.User;
import com.school.service.impl.*;
import com.school.utils.AssertUtil;
import com.school.utils.ResponseUtil;
import com.school.utils.RoleEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Api(
        value = "系统参数配置",
        tags = {"系统参数配置"}
)
@RestController
@RequestMapping({"/api/admin/sys"})
public class AdminSystemController {
    private static final String likeJobGroup = "LIKE_GROUP";
    private static final String onLikeJobName = "LIKE_ON";
    private static final String offLikeJobName = "LIKE_OFF";

    private static final String signJobGroup = "SIGN_GROUP";
    private static final String onSignJobName = "SIGN_ON";
    private static final String offSignJobName = "SIGN_OFF";

    private static final String mouDownloadJobGroup = "MOU_DOWNLOAD_GROUP";
    private static final String onMouDownloadJobName = "MOU_DOWNLOAD_ON";
    private static final String offMouDownloadJobName = "MOU_DOWNLOAD_OFF";

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    RoleToAuthoritiesServiceImpl roleToAuthoritiesService;
    @Autowired
    SchedulerManager schedulerManager;
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

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }


    @PostMapping("/sysConfig")
    public Object sysConfig(@ApiParam(value = "意向开始时间", example = "yyyy-MM-dd HH:mm:ss") @RequestParam("likeStartTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
                            @ApiParam(value = "意向结束时间", example = "yyyy-MM-dd HH:mm:ss") @RequestParam("likeEndTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime,
                            ){

    }

    @PostMapping("/configLikesPeriod")
    @ApiOperation(value = "配置用户意向时段", notes = "配置用户可以进行意向的时段")
//    @PreAuthorize("hasAnyRole('ADMINISTRATOR')")
    public Object controlLikes(@ApiParam(value = "开始时间", example = "yyyy-MM-dd HH:mm:ss") @RequestParam("likeStartTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime likeStartTime,
                               @ApiParam(value = "结束时间", example = "yyyy-MM-dd HH:mm:ss") @RequestParam("likeEndTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime likeEndTime) throws SchedulerException {
        schedulerManager.startJob(startTime, onLikeJobName, likeJobGroup, LikeOperationOnJob.class);
        schedulerManager.startJob(endTime, offLikeJobName, likeJobGroup, LikeOperationOffJob.class);
        return ResponseUtil.build(HttpStatus.OK.value(), "限制用户意向期间的成功", null);
    }

    @ApiOperation(value = "配置用户签约时段", notes = "配置用户可以进行签约的时间")
    @PostMapping("/configSignPeriod")
    //    @PreAuthorize("hasAnyRole('ADMINISTRATOR')")
    public Object controlSings(@ApiParam(value = "开始时间", example = "yyyy-MM-dd HH:mm:ss") @RequestParam("startTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
                               @ApiParam(value = "结束时间", example = "yyyy-MM-dd HH:mm:ss") @RequestParam("endTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) throws SchedulerException {
        schedulerManager.startJob(startTime, onSignJobName, signJobGroup, SignOperationOnJob.class);
        schedulerManager.startJob(endTime, offSignJobName, signJobGroup, SignOperationOffJob.class);
        return ResponseUtil.build(HttpStatus.OK.value(), "限制用户意向期间的成功", null);
    }

    @ApiOperation(value = "配置用户MOU下载时段", notes = "配置用户MOU下载时段")
    @PostMapping("/configMouDownloadPeriod")
    //    @PreAuthorize("hasAnyRole('ADMINISTRATOR')")
    public Object controlMouDownload(@ApiParam(value = "开始时间", example = "yyyy-MM-dd HH:mm:ss") @RequestParam("startTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
                                     @ApiParam(value = "结束时间", example = "yyyy-MM-dd HH:mm:ss") @RequestParam("endTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) throws SchedulerException {
        schedulerManager.startJob(startTime, onMouDownloadJobName, mouDownloadJobGroup, MouDownloadOperationOnJob.class);
        schedulerManager.startJob(endTime, offMouDownloadJobName, mouDownloadJobGroup, MouDownloadOperationOffJob.class);
        return ResponseUtil.build(HttpStatus.OK.value(), "限制用户意向期间的成功", null);
    }

    @PostMapping("/modifySystemEmail")
    @ApiOperation(value = "修改系统邮箱", notes = "修改系统邮箱，须有邮箱号以及该邮箱号的授权码")
    public String modifySystemEmail(@ApiParam(example = "123@qq.com", value = "系统邮箱账号") @RequestParam("username") String username,
                                    @ApiParam(example = "123456", value = "开启POP3后获得的授权码") @RequestParam("code") String code,
                                    HttpServletRequest request) throws EmailWrongFormatException, UsernameNullPointerException {
        logger.info("[" + request.getRemoteAddr() + "] 管理员修改默认系统邮箱！");
        Assert.notNull(code, "管理端设置系统的邮箱的授权码不应为空！");
        AssertUtil.isValidMail(username, "设置的系统邮箱不能为空！");
        emailService.modifySystemEmail(username, code);
        return ResponseUtil.build(HttpStatus.OK.value(), "修改系统邮箱成功！");
    }

    @PostMapping("/clean")
    @ApiOperation("修改系统邮箱")
    public String cleanData() throws UserNotFoundException, UserSignCorrespondException, SignNotFoundException, UserLikesNotCorrespondException, LikesNotFoundException {
        List<User> users = userService.querySelectiveLike(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        for (User user : users) {
            userService.delete(user);
        }
        List<Sign> signs = signService.querySelective(null, null, null, null, null, null, null, null, null, null);
        for (Sign sign : signs) {
            signService.delete(sign);
        }
        List<Pics> pics = picsService.querySelective(null, null, null);
        for (Pics pic : pics) {
            picsService.delete(pic);
        }
        List<Likes> likes = likeService.querySelective(null, null, null, null, null, null, null, null, null, null);
        for (Likes like : likes) {
            likeService.delete(like);
        }
        return ResponseUtil.build(HttpStatus.OK.value(), "删除所有数据成功！");

    }


    class MouDownloadOperationOnJob implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            roleToAuthoritiesService.addAuthority(RoleEnum.USER.value(), "user::mouDownload");
        }
    }

    class MouDownloadOperationOffJob implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            roleToAuthoritiesService.addAuthority(RoleEnum.USER.value(), "user::mouDownload");
        }
    }

    class LikeOperationOnJob implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            roleToAuthoritiesService.addAuthority(RoleEnum.USER.value(), "user::like");
        }
    }

    class LikeOperationOffJob implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            roleToAuthoritiesService.removeAuthority(RoleEnum.USER.value(), "user::like");
        }
    }

    class SignOperationOnJob implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            roleToAuthoritiesService.addAuthority(RoleEnum.USER.value(), "user::sign");
        }
    }

    class SignOperationOffJob implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            roleToAuthoritiesService.removeAuthority(RoleEnum.USER.value(), "user::sign");
        }
    }
}
