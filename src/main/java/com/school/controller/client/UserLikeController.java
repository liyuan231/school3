package com.school.controller.client;

import com.school.exception.*;
import com.school.model.Likes;
import com.school.model.User;
import com.school.service.impl.LikeServiceImpl;
import com.school.service.impl.PicsServiceImpl;
import com.school.service.impl.UserServiceImpl;
import com.school.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/api/client/like")
@Api(tags = {"客户端意向"})
public class UserLikeController {
    @Autowired
    private LikeServiceImpl likeService;

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private PicsServiceImpl picsService;

    private final Logger logger = LoggerFactory.getLogger(getClass());


    @PreAuthorize("hasAnyRole('USER')")
    @ApiOperation(value = "选择签约->勾选参会学校->提交", notes = "批量表明意向")
    @GetMapping("/batchLike")
    public Object batchSign(@RequestParam("likes") Integer[] likedUserIds) {
        for (Integer likedUserId : likedUserIds) {
            try {
                likeService.like(likedUserId);
            } catch (UserLikesNotCorrespondException | LikesNotFoundException|UserNotFoundException | UserNotCorrectException | LikesAlreadyExistException e) {
                logger.warn(e.getMessage()+"->"+ likedUserId);
//                e.printStackTrace();
            }
        }
        return ResponseUtil.build(HttpStatus.OK.value(), "用户批量表明意向成功！");
    }


//    /**
//     * 传入当前用户喜欢的一方的userId
//     *
//     * @param likedUserId
//     * @return
//     */
//    @PreAuthorize("hasAnyRole('USER') and hasAnyAuthority('user::like')")
//    @PostMapping("/like/{likedUserId}")
//    @ApiOperation(value = "表明意向（需登录）", notes = "用户表明意向,添加一则意向记录")
//    public String like(@ApiParam(example = "1", value = "被表明意向的用户的id") @PathVariable("likedUserId") Integer likedUserId) throws UserNotFoundException, UserNotCorrectException, LikesAlreadyExistException {
//        User user = userService.retrieveUserByToken();
//        List<User> users = userService.querySelectiveLike(null, user.getUsername(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
//        if (users.size() == 0) {
//            throw new UserNotFoundException("用户名不存在！");
//        }
//        List<User> likedUsers = userService.querySelectiveLike(likedUserId, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
//        //TODO
////        likeService.add();
//        return ResponseUtil.build(HttpStatus.OK.value(), "表明意向成功！", null);
//    }

//    @GetMapping("/list")
//    @ApiOperation(value = "查询意向（需讨论！！！）", notes = "主要用于用户端分页显示，也是为了支持用户端的搜索功能，搜索某一用户喜欢谁或被谁意向")
//    public Object list(@ApiParam(example = "1", value = "想要查询的该则意向的id") @RequestParam(value = "id", required = false) Integer id,
//                       @ApiParam(example = "1", value = "用户的id，使用用户id查询她都对谁有过意向") @RequestParam(value = "likeUserId", required = false) Integer likeUserId,
//                       @ApiParam(example = "1", value = "用户的id，使用用户id查询都有谁对他有意向") @RequestParam(value = "likedUserId", required = false) Integer likedUserId,
//                       @ApiParam(example = "1", value = "分页使用，要第几页的数据") @RequestParam(defaultValue = "1") Integer page,
//                       @ApiParam(example = "10", value = "分页使用，要该页的几条数据") @RequestParam(defaultValue = "10") Integer limit,
//                       @ApiParam(example = "1", value = "排序方式，从数据库中要的数据使用什么进行排序，如 add_time,update_time") @RequestParam(defaultValue = "add_time") String sort,
//                       @ApiParam(example = "desc", value = "排序方式，升序asc还是降序desc") @RequestParam(defaultValue = "desc") String order) {
//        List<Likes> list = likeService.querySelective(id, likeUserId, null, likedUserId, null, page, limit, sort, order, null);
//        return ResponseUtil.build(HttpStatus.OK.value(), "获取意向列表成功！", list);
//    }

//    @ApiOperation(value = "删除", notes = "用户删除一则意向")
//    @PreAuthorize("hasAnyRole('USER')")
//    @DeleteMapping("/delete/{id}")
//    public Object delete(@ApiParam(example = "1", value = "将被删除的那则意向的id") @PathVariable("id") Integer id) throws UserLikesNotCorrespondException, LikesNotFoundException {
//        likeService.deleteById(id);
//        return ResponseUtil.build(HttpStatus.OK.value(), "删除一则意向成功！", null);
//    }

//    @ApiOperation(value = "修改", notes = "用户删除一则意向")
//    @PreAuthorize("hasAnyRole(,'USER')")
//    @DeleteMapping("/update/{id}/{likeUserId}/{likedUserId}")
//    public Object update(@ApiParam(example = "1", value = "将被修改的那则意向的id") @PathVariable("id") Integer id,
//                         @ApiParam(example = "1", value = "主动喜欢的用户") @PathVariable("likeUserId") Integer likeUserId,
//                         @ApiParam(example = "1", value = "被喜欢的用户") @PathVariable("likedUserId") Integer likedUserId) throws UserLikesNotCorrespondException, LikesNotFoundException {
//        likeService.update(id, likeUserId, likedUserId, null, null, null);
//        return ResponseUtil.build(HttpStatus.OK.value(), "删除一则意向成功！", null);
//    }

//    @ApiOperation(value = "查询对我有意向的用户", notes = "看看谁对我有意向，同查询")
//    @GetMapping("/matchWhoLikesMe")
//    @PreAuthorize("hasAnyRole('ADMINISTRATOR','USER')")
//    public Object matchWhoLikesMe() throws UserNotFoundException {
//        List<Likes> matchs = likeService.matchByLikedUserId();
//        List<User> users = new LinkedList<>();
//        for (Likes match : matchs) {
//            User user = userService.findById(match.getLikeuserid());
//            users.add(user);
//        }
//        return ResponseUtil.build(HttpStatus.OK.value(), "获取对我有意向的用户成功！", users);
//    }

    @GetMapping("/listILikesWho")
    @PreAuthorize("hasAnyRole('USER')")
    @ApiOperation(value = "查询我有意向的用户", notes = "确认签约处获取我有意向的学校")
    public Object matchILikesWho() {
        User curUser = userService.retrieveUserByToken();
        //我意向的所有的用户学校
        List<Likes> theUserThatILikes = likeService.querySelective(null, curUser.getId(), null, null, null, null, null, null, null, null);
        for (Likes theUserThatILike : theUserThatILikes) {
            clean(theUserThatILike);
        }
        return ResponseUtil.build(HttpStatus.OK.value(), "获取我有意向的用户成功！", theUserThatILikes);
    }

    private void clean(Likes theUserThatILikes) {
        theUserThatILikes.setLikeuserid(null);
        theUserThatILikes.setLikeschoolname(null);
        theUserThatILikes.setAddTime(null);
        theUserThatILikes.setUpdateTime(null);
        theUserThatILikes.setDeleted(null);
        theUserThatILikes.setId(null);
//        theUserThatILikes.set
    }

//    @GetMapping("/match")
//    @PreAuthorize("hasAnyRole('ADMINISTRATOR','USER')")
//    @ApiOperation(value = "查询互有意向的用户", notes = "获取我有意向的用户且对我也有意向，即互相喜欢的用户")
//    public Object match() throws UserNotFoundException {
//        List<Likes> matchs = likeService.matchByUserId();
//        List<User> users = new LinkedList<>();
//        for (Likes match : matchs) {
//            User user = userService.findById(match.getLikeduserid());
//            users.add(user);
//        }
//        return ResponseUtil.build(HttpStatus.OK.value(), "获取互相喜欢的用户们成功！", users);
//    }
}
