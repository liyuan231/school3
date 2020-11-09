package com.school.controller.client;

import com.school.dto.LikeOrSign;
import com.school.exception.*;
import com.school.model.Likes;
import com.school.model.Pics;
import com.school.model.Sign;
import com.school.model.User;
import com.school.service.golden.userservice;
import com.school.service.impl.LikeServiceImpl;
import com.school.service.impl.PicsServiceImpl;
import com.school.service.impl.SignServiceImpl;
import com.school.service.impl.UserServiceImpl;
import com.school.utils.FileEnum;
import com.school.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/api/client/like")
@Api(tags = {"客户端意向"})
public class UserLikeController {
    @Autowired
    private LikeServiceImpl likeService;
    @Autowired
    private userservice user_service;
    @Autowired
    private SignServiceImpl signService;

    @Autowired
    UserSignController usersign;

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private PicsServiceImpl picsService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @PreAuthorize("hasAnyRole('USER')")
    @ApiOperation(value = "选择签约->勾选参会学校->提交，若对方已选择自己为意向签约目标，系统自动匹配签约", notes = "批量表明意向并且自动匹配签约")
    @PostMapping("/batchLike")
    public String batchLike(@RequestParam(value = "likedUserIds") Integer[] likedUserIds) {
        String message = null;
        User user = userService.retrieveUserByToken();
        for (Integer likedUserId : likedUserIds) {
            try {
                List<Likes> likes = likeService.queryByLikeUserIdAndLikedUserId(likedUserId, user.getId());
                User u = userService.queryById(likedUserId, User.Column.id, User.Column.schoolName);
                if (likes.size() > 0) {
                    //说明数据库中有相应的意向，可匹配
                    Likes l = likes.get(0);
                    likeService.deleteById(l.getId());
                    LocalDateTime likeAddTime = l.getAddTime();
                    Sign sign = new Sign();
                    sign.setSignUserId(l.getLikeUserId());
                    sign.setSignSchoolName(user.getSchoolName());
                    sign.setSignedUserId(l.getLikedUserId());
                    sign.setSignedSchoolName(u.getSchoolName());
                    sign.setAddTime(likeAddTime);
                    signService.add(sign);
                    //匹配后删除对应的意向
                    message = "和" + u.getSchoolName() + "签约成功！";
                } else {
                    //无意向匹配，那就是表明意向
                    likeService.add(user.getId(), user.getSchoolName(), u.getId(), u.getSchoolName());
                    message = "和" + u.getSchoolName() + "表明意向成功!";
                }
            } catch (UserNotFoundException | UserNotCorrectException | LikesAlreadyExistException | UserLikesNotCorrespondException | LikesNotFoundException e) {
//                e.printStackTrace();
                logger.error("批量表明意向并且自动匹配签约-》" + e.getMessage());
            }
        }
        return ResponseUtil.build(HttpStatus.OK.value(), "批量表明意向且自动签约成功！", message);
    }


    //
    @PostMapping({"/addLike/{likeId}"})
    @ApiOperation(
            value = "用户同意一则意向",
            notes = "用户同意一则意向"
    )
    @PreAuthorize("hasRole('USER')")
    public Object like(@PathVariable("likeId") Integer likeId) throws LikesNotFoundException {
        Likes likes = likeService.queryByLikeId(likeId);
        Assert.notNull(likes, "该则意向不存在！");
        LocalDateTime likeAddTime = likes.getAddTime();
        likeService.deleteById(likeId);
        User likeUser = userService.queryById(likes.getLikeUserId(), User.Column.id, User.Column.schoolName);
        User likedUser = userService.queryById(likes.getLikedUserId(), User.Column.id, User.Column.schoolName);
        Sign sign = new Sign();
        sign.setSignUserId(likes.getLikeUserId());
        sign.setSignSchoolName(likeUser.getSchoolName());
        sign.setSignedUserId(likes.getLikedUserId());
        sign.setSignedSchoolName(likedUser.getSchoolName());
        sign.setAddTime(likeAddTime);
        signService.add(sign);
        //每添加一则签约，删除其对应的所有意向
        likeService.deleteByLikeUserIdAndLikedUserId(likeUser.getId(), likedUser.getId());
        likeService.deleteByLikeUserIdAndLikedUserId(likedUser.getId(), likeUser.getId());
        return ResponseUtil.build(HttpStatus.OK.value(), "用户同意一则意向！");
    }
    //golden
//    @PreAuthorize("hasAnyRole('USER')")
//    @ApiOperation(value = "选择签约->勾选参会学校->提交，若对方已选择自己为意向签约目标，系统自动匹配签约", notes = "批量表明意向并且自动匹配签约")
//    @PostMapping("/batchLike")
//    public Object batchSign(@RequestParam(value = "likedUserIds") Integer[] likedUserIds) {
//        Integer host_id = userService.retrieveUserByToken().getId();
//        Integer batch_sign[] = new Integer[likedUserIds.length + 1];
//        int i = 0;
//        for (Integer likedUserId : likedUserIds) {
//            try {
//                likeService.like(likedUserId);
//            } catch (UserLikesNotCorrespondException | LikesNotFoundException | UserNotFoundException | UserNotCorrectException | LikesAlreadyExistException e) {
//                logger.warn(e.getMessage() + "->" + likedUserId);
////                e.printStackTrace();
//            }
//            try {
//                if (user_service.select_both(host_id, likedUserId)) {
//                    batch_sign[i] = likedUserId;
//                    i++;
//                }
//            } catch (Exception e) {
//                logger.warn(e.getMessage() + "->" + likedUserId);
//            }
//        }
//        Integer sign[] = new Integer[likedUserIds.length + 1];
//
//        System.out.println(i);
//        if (i != 0) {
//            sign = Arrays.copyOfRange(batch_sign, 0, i);
//            System.out.println(sign);
//            try {
//                usersign.batchSign(sign);
//            } catch (Exception e) {
//                logger.warn(e.getMessage());
//            }
//        }
//        return ResponseUtil.build(HttpStatus.OK.value(), "用户批量表明意向成功！签约已实时更新");
//    }

    @ApiOperation(value = "查询对我有意向的用户", notes = "看看谁对我有意向")
    @GetMapping("/matchWhoLikesMe")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR','USER')")
    public Object matchWhoLikesMe() throws UserNotFoundException {
        User u = this.userService.retrieveUserByToken();
        List<LikeOrSign> likeOrSigns = new LinkedList<>();
        //我的意向（我是被动）
        List<Likes> likes = likeService.queryByLikedUserId(u.getId());
        for (Likes like : likes) {
            LikeOrSign likeOrSign = new LikeOrSign();
            likeOrSign.setUpdateTime(like.getUpdateTime());
            likeOrSign.setSignIdOrLikeId(like.getId());
            likeOrSign.setSigned(false);
//            SimpleIntention simpleIntention = new SimpleIntention();
//            simpleIntention.setUpdateTime(like.getUpdateTime());
//            simpleIntention.setLikeId(like.getId());
            User user = userService.queryById(like.getLikeUserId(), User.Column.id, User.Column.schoolName);
            likeOrSign.setSchoolId(user.getId());
            likeOrSign.setSchoolName(user.getSchoolName());
//            simpleIntention.setSchoolId(user.getId());
//            simpleIntention.setSchoolName(user.getSchoolName());
            List<Pics> logos = picsService.querySelective(like.getLikeUserId(), FileEnum.LOGO.value());
            if (logos.size() > 0) {
//                simpleIntention.setLogo(false);
                likeOrSign.setLogo(logos.get(0).getLocation());
            } else {
//                simpleIntention.setLogo(true);
                likeOrSign.setLogo(null);
            }
//            simpleIntentions.add(simpleIntention);
            likeOrSigns.add(likeOrSign);
        }
        return ResponseUtil.build(HttpStatus.OK.value(), "获取我的意向用户", likeOrSigns);
//        return ResponseUtil.build(HttpStatus.OK.value(), "获取对我有意向的用户成功！", fullUsers);
    }


}
