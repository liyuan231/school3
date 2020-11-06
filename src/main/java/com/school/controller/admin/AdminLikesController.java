//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.school.controller.admin;

import com.github.pagehelper.PageInfo;
import com.school.dto.*;
import com.school.exception.*;
import com.school.model.Likes;
import com.school.model.Sign;
import com.school.model.User;
import com.school.service.impl.*;
import com.school.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections4.ListUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController("adminLikeController")
@RequestMapping({"/api/admin/like"})
@Api(
        value = "签约意向管理",
        tags = {"签约意向管理"}
)
public class AdminLikesController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private LikeServiceImpl likeService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private EmailServiceImpl emailService;
    @Autowired
    private SignServiceImpl signService;
    @Autowired
    private PicsServiceImpl picsService;

    @GetMapping({"/listSearch"})
    @ApiOperation(
            value = "搜索/分页显示",
            notes = "输入高校名进行搜索"
    )
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public Object search(@RequestParam(value = "schoolname", required = false) String likeSchoolName,
                         @ApiParam(example = "1", value = "分页使用，要第几页的数据") @RequestParam(required = false) Integer page,
                         @ApiParam(example = "10", value = "分页使用，要该页的几条数据") @RequestParam(required = false) Integer pageSize,
                         @ApiParam(example = "1", value = "排序方式，从数据库中要的数据使用什么进行排序，如 add_time,update_time") @RequestParam(defaultValue = "add_time", required = false) String sort,
                         @ApiParam(example = "desc", value = "排序方式，升序asc还是降序desc") @RequestParam(defaultValue = "desc", required = false) String order,
                         @ApiParam(example = "2020", value = "year") @RequestParam(defaultValue = "2020", required = false) Integer year) {
        PageInfo<User> userPageInfo = userService.querySelective(likeSchoolName, page, pageSize, sort, order);
        int size = (int) userPageInfo.getTotal();
        List<AdvancedLikes> advancedLikesLinkedList = likeService.retrieveAdvancedLikes(userPageInfo.getList());
        SimplePage<List<AdvancedLikes>> simplePage = new SimplePage<>(size, advancedLikesLinkedList);
        return ResponseUtil.build(HttpStatus.OK.value(), "获取很多数据的那个接口的意向成功！", simplePage);

    }

    //TODO 此处有一个bug，前端传来的
    @GetMapping({"/listSearchSingle"})
    @ApiOperation(
            value = "查看单项意向，搜索/分页显示",
            notes = "输入高校名进行搜索"
    )
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public Object searchSingle(@RequestParam(value = "schoolname", required = false) String likeSchoolName,
                               @ApiParam(example = "1", value = "分页使用，要第几页的数据") @RequestParam(required = false) Integer page,
                               @ApiParam(example = "10", value = "分页使用，要该页的几条数据") @RequestParam(required = false) Integer pageSize,
                               @ApiParam(example = "1", value = "排序方式，从数据库中要的数据使用什么进行排序，如 add_time,update_time") @RequestParam(defaultValue = "add_time", required = false) String sort,
                               @ApiParam(example = "desc", value = "排序方式，升序asc还是降序desc") @RequestParam(defaultValue = "desc", required = false) String order,
                               @ApiParam(example = "2020", value = "year") @RequestParam(required = false, value = "year") Integer year) {
        PageInfo likeWithUserPageInfo = this.likeService.querySelective(year, likeSchoolName, page, pageSize, sort, order);
        int count = likeService.countByYearAndLikeSchoolName(year, likeSchoolName);
        SimplePage<List<LikeWithUser>> listSimplePage = new SimplePage<List<LikeWithUser>>(count, likeWithUserPageInfo.getList());
        return ResponseUtil.build(HttpStatus.OK.value(), "获取单项意向表 成功！", listSimplePage);
    }

    @ApiOperation(
            value = "导出单项签约意向表",
            notes = "导出单项签约意向表"
    )
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping({"/exportLikesFormSingle"})
    public void exportLikesFormSingle(@ApiParam(example = "意向的的ids", value = "[1,2,3]") @RequestParam(required = false, value = "likeIds") Integer[] likeIds, HttpServletResponse response) throws IOException {
        List<Likes> likesList;
        if (likeIds == null || likeIds.length == 0) {
            likesList = likeService.querySelective().getList();
        } else {
            likesList = new LinkedList<>();
            for (Integer likeId : likeIds) {
                Likes likes = likeService.queryByLikeId(likeId);
                likesList.add(likes);
//                likesList.addAll(likeService.querySelective(likeId, null, null, null, null, null, null, null, null, null, null).getList());
            }
        }
        Workbook workbook = this.likeService.exportLikesFormSingle(likesList);
        String fileName = Likes.class.getSimpleName() + ".xls";
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        workbook.write(outputStream);
        outputStream.close();
        workbook.close();
    }

    @ApiOperation(
            value = "导出签约意向表",
            notes = "导出签约意向表"
    )
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping({"/exportLikesForm"})
    public void exportLikesForm(@ApiParam(example = "用户的的的ids", value = "[1,2,3]") @RequestParam(required = false, value = "userIds") Integer[] userIds, HttpServletResponse response) throws IOException {
        List<User> users = new LinkedList<>();
        if (userIds == null || userIds.length == 0) {
            PageInfo<User> userPageInfo = userService.querySelective();
            users = userPageInfo.getList();
        } else {
            for (Integer userId : userIds) {
                User user = userService.queryById(userId);
                if (user != null) {
                    users.add(user);
                }
            }
        }
        Workbook workbook = this.likeService.exportLikesForm(users);
        String fileName = Likes.class.getSimpleName() + ".xls";
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        workbook.write(outputStream);
        outputStream.close();
        workbook.close();
    }

    @GetMapping({"/list/{userId}"})
    @ApiOperation(
            value = "凭每个高校id查看",
            notes = "每个高校的意向的查看"
    )
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public Object list(@PathVariable("userId") Integer likeUserId) {
//        List<Likes> likes = this.likeService.querySelective((Integer) null, likeUserId, (String) null, (Integer) null, (String) null, (Integer) null, (Integer) null, (String) null, (String) null, (Boolean) null, null).getList();
        //查看该用户的意向结果
        List<Likes> likes = likeService.queryByLikeUserId(likeUserId);
        SimpleLikeUser simplerLikeUser = new SimpleLikeUser();
        List<SimpleLike> likesList = new LinkedList<>();
        User u = userService.queryById(likeUserId, User.Column.id, User.Column.schoolName);
        simplerLikeUser.setSchoolName(u.getSchoolName());
        simplerLikeUser.setUserId(likeUserId);
        for (Likes like : likes) {
            SimpleLike simpleLike = new SimpleLike();
            simpleLike.setLikeId(like.getId());
            User likeUser = userService.queryById(like.getLikeUserId(), User.Column.id, User.Column.schoolName);
            simpleLike.setLikeSchoolName(likeUser.getSchoolName());
            simpleLike.setLikeUserId(likeUser.getId());
            User u2 = userService.queryById(like.getLikedUserId(), User.Column.id, User.Column.schoolName);
            simpleLike.setLikedSchoolName(u2.getSchoolName());
            simpleLike.setLikedUserId(u2.getId());
            likesList.add(simpleLike);
        }
        simplerLikeUser.setLikes(likesList);
        return ResponseUtil.build(HttpStatus.OK.value(), "获取该学校的所有意向成功！", simplerLikeUser);
    }


    @PostMapping({"/batchLikes"})
    @ApiOperation(
            value = "批量添加意向",
            notes = "批量添加多则意向"
    )
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public String add(
            @ApiParam(example = "1", value = "主动去喜欢其他用户的用户", required = true) @RequestParam("likeUserId") Integer likeUserId,
            @ApiParam(example = "2", value = "被喜欢的用户的id", required = true) @RequestParam("likedUserIds") Integer[] likedUserIds) throws UserNotFoundException {
        User likeUser = this.userService.queryById(likeUserId, User.Column.id, User.Column.schoolName);
        if (likeUser == null) {
            throw new UserNotFoundException("用户id不存在！");
        } else {
            for (Integer likedUserId : likedUserIds) {
                try {
                    User likedUser = userService.queryById(likedUserId, User.Column.id, User.Column.schoolName);
                    if (likedUser == null) {
                        throw new UserNotFoundException("用户id不存在！");
                    } else {
                        //TODO 此处逻辑可优化
//                        User likedUser = users.get(0);
                        Likes like = new Likes();
                        like.setLikeUserId(likeUserId);
                        like.setLikeSchoolName(likeUser.getSchoolName());
                        like.setLikedUserId(likedUser.getId());
                        like.setLikedSchoolName(likedUser.getSchoolName());
                        this.likeService.add(like);
                        checkIfNeedSign(like);
                    }
                } catch (LikesNotFoundException | UserNotFoundException | UserNotCorrectException | LikesAlreadyExistException | UserLikesNotCorrespondException e) {
                    logger.warn("[" + likeUser.getId() + "->" + likedUserId + "]" + e.getMessage());
                }
            }
            return ResponseUtil.build(HttpStatus.OK.value(), "管理端添加该学校意向成功！");
        }
    }

    @PostMapping({"/addLike/{likeId}"})
    @ApiOperation(
            value = "添加意向",
            notes = "添加多则意向"
    )
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public Object like(@PathVariable("likeId") Integer likeId) throws LikesNotFoundException {
        Likes likes = likeService.queryByLikeId(likeId);
        likeService.deleteById(likeId);
        User likeUser = userService.queryById(likes.getLikeUserId(), User.Column.id, User.Column.schoolName);
        User likedUser = userService.queryById(likes.getLikedUserId(), User.Column.id, User.Column.schoolName);
        Sign sign = new Sign();
        sign.setSignUserId(likes.getLikeUserId());
        sign.setSignSchoolName(likeUser.getSchoolName());
        sign.setSignedUserId(likes.getLikedUserId());
        sign.setSignedSchoolName(likedUser.getSchoolName());
        signService.add(sign);
        return ResponseUtil.build(HttpStatus.OK.value(), "管理端同意一则意向成功！");
    }

    private boolean checkIfNeedSign(Likes like) {
        List<Likes> likesList = likeService.queryByLikeUserIdAndLikedUserId(like.getLikedUserId(), like.getLikeUserId());
        //管理端添加一则意向，若这则意向的likeUserId->likedUserId 对应的意向 likedUserId->likeUserId在意向表中已经存在了，说明可实行自动签约
        if (likesList.size() > 0) {
            Likes anotherLike = likesList.get(0);
            likeService.deleteByLikeUserIdAndLikedUserId(like.getLikeUserId(), like.getLikedUserId());
            likeService.deleteByLikeUserIdAndLikedUserId(anotherLike.getLikeUserId(), anotherLike.getLikedUserId());
//            List<Sign> signs1 = signService.querySelective(null, like.getLikeUserId(), null, like.getLikedUserId(), null, null, null, null, null, null);
            List<Sign> signs1 = signService.queryBySignUserAndSignedUserId(like.getLikeUserId(), like.getLikedUserId());
//            List<Sign> signs2 = signService.querySelective(null, like.getLikedUserId(), null, like.getLikeUserId(), null, null, null, null, null, null);
            List<Sign> signs2 = signService.queryBySignUserAndSignedUserId(like.getLikedUserId(), like.getLikeUserId());
            //说明该则意向已经有对应
            if (signs1.size() + signs2.size() > 0) {
                return false;
            }
            Sign sign = new Sign();
            sign.setSignUserId(like.getLikeUserId());
            sign.setSignSchoolName(like.getLikeSchoolName());
            sign.setSignedUserId(like.getLikedUserId());
            sign.setSignedSchoolName(like.getLikedSchoolName());
            signService.add(sign);

            return true;
        }
        return false;
    }

    @DeleteMapping({"/delete"})
    @ApiOperation(
            value = "签约意向管理->修改签约意向-批量删除()",
            notes = "签约意向管理->修改签约意向-批量删除()"
    )
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public Object delete(@ApiParam(example = "1", value = "意向的id->数组") @RequestParam("ids") Integer[] ids) {
        for (Integer i : ids) {
            try {
                this.likeService.deleteById(i);
            } catch (LikesNotFoundException e) {
                logger.warn("该则意向id不存在->" + i);
            }
        }
        return ResponseUtil.build(HttpStatus.OK.value(), "删除一则意向成功！", (Object) null);
    }

    @ApiOperation(
            value = "签约意向管理->发送消息提醒高校参与意向选择",
            notes = "签约意向管理->发送消息提醒高校参与意向选择"
    )
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping({"/remind"})
    public String remindSchools(@ApiParam(example = "用户id数组", value = "[1,2,3]") @RequestParam(value = "userIds", required = false) Integer[] userIds) {
        List<User> users = null;
        if (userIds == null || userIds.length == 0) {
            PageInfo<User> userPageInfo = userService.querySelective(User.Column.id, User.Column.username);
            users = userPageInfo.getList();
        } else {
            users = new LinkedList<>();
            for (Integer userId : userIds) {
                try {
                    User user = userService.findById(userId);
                    users.add(user);
                } catch (UserNotFoundException e) {
                    logger.error("用户id不存在->" + userId);
                }
            }
        }
        List<User> finalUsers = users;
        new Thread(() -> {
            Iterator var2 = finalUsers.iterator();
            while (var2.hasNext()) {
                User user = (User) var2.next();
                try {
                    this.emailService.send(user.getUsername(), "!签约意向提醒!", "时间马上就要截至了,记得来参与意向选择~");
                } catch (MailException var5) {
                    this.logger.warn(user.getUsername() + var5.getMessage());
                }
            }
        }).start();
        return ResponseUtil.build(HttpStatus.OK.value(), "签约意向提醒成功!", (Object) null);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping({"/listSchools"})
    @ApiOperation(
            value = "加上了page和pageSize签约意向管理->修改签约意向->添加意向高校->输入高校名搜索",
            notes = "加上了page和pageSize 签约意向管理->修改签约意向->添加意向高校->输入高校名搜索"
    )
    public Object listSchools(@ApiParam(example = "1", value = "用户id") @RequestParam("userId") Integer userId,
                              @RequestParam(value = "schoolName", required = false) String schoolName,
                              @ApiParam(example = "1", value = "分页使用，要第几页的数据") @RequestParam(required = false) Integer page,
                              @ApiParam(example = "10", value = "分页使用，要该页的几条数据") @RequestParam(required = false) Integer pageSize,
                              @ApiParam(example = "1", value = "排序方式，从数据库中要的数据使用什么进行排序，如 add_time,update_time") @RequestParam(defaultValue = "add_time", required = false) String sort,
                              @ApiParam(example = "desc", value = "排序方式，升序asc还是降序desc") @RequestParam(defaultValue = "desc", required = false) String order) {
//        List<User> users = this.userService.querySelectiveLike((Integer) null, (String) null, schoolName, (String) null, (String) null, (String) null, (Integer) null, (String) null, (String) null, (Integer) null, (String) null, (String) null, (Integer) null, (String) null, page, pageSize, sort, order);
        PageInfo<User> userPageInfo = userService.querySelective(schoolName, null, null, sort, order, User.Column.id, User.Column.schoolName);
        final int[] size = {(int) userPageInfo.getTotal()};

        List<User> users = userPageInfo.getList();
        List<Sign> signs = signService.queryBySignUserId(userId);
        List<Sign> signs1 = signService.queryBySignedUserId(userId);
        //获取了我的签约结果
        signs.addAll(signs1);
        //获取了我的意向
        List<Likes> likes = likeService.queryByLikeUserId(userId);
        int[] theUserThatIHaveAlreadySignOrLike = new int[signs.size() + likes.size()];
        int i = 0;
        for (Sign sign : signs) {
            if (sign.getSignUserId().equals(userId)) {
                theUserThatIHaveAlreadySignOrLike[i++] = sign.getSignedUserId();
            } else {
                theUserThatIHaveAlreadySignOrLike[i++] = sign.getSignUserId();
            }
        }
        for (Likes like : likes) {
            theUserThatIHaveAlreadySignOrLike[i++] = like.getLikedUserId();
        }
        Arrays.sort(theUserThatIHaveAlreadySignOrLike);
//        List<UserWithMark> userWithMarks = new LinkedList<>();
//        for (User user : users) {
//            UserWithMark userWithMark = new UserWithMark();
//            userWithMark.setUser(user);
//            if (Arrays.binarySearch(theUserThatIHaveAlreadySignOrLike, user.getId()) >= 0||user.getId().equals()) {
//                size--;
//                userWithMark.setChoose(true);
//            } else {
//                userWithMark.setChoose(false);
//            }
//            userWithMarks.add(userWithMark);
//        }
        List<User> collect = users.stream().filter(new Predicate<User>() {
            @Override
            public boolean test(User user) {
                if (Arrays.binarySearch(theUserThatIHaveAlreadySignOrLike, user.getId()) >= 0 || user.getId().equals(userId)) {
                    size[0]--;
                    return false;
                }
                return true;
            }
        }).collect(Collectors.toList());
        List<List<User>> partition = ListUtils.partition(collect, pageSize);
        List<User> result = null;
        if (partition.size() >= page) {
            result = partition.get(page-1);
        }
        SimplePage simplePage = new SimplePage(size[0], result);
        return ResponseUtil.build(HttpStatus.OK.value(), "搜索用户列表成功！", simplePage);
    }

}
