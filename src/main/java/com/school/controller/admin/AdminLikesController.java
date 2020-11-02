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
import java.util.*;

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
        List<LikeWithUser> likesList = this.likeService.querySelectiveDistinct(year, likeSchoolName, page, pageSize, sort, order);
        int count = this.likeService.countDistinct(year, likeSchoolName);
        List<Likes> likesList1 = new LinkedList<>();
        for (LikeWithUser likeWithUser : likesList) {
            Likes likes = new Likes();
            likes.setId(likeWithUser.getLikeId());
            likes.setLikeUserId(likeWithUser.getLikeUserId());
            likes.setLikedUserId(likeWithUser.getLikedUserId());
            likesList1.add(likes);
        }
        List<AdvancedLikes> advancedLikes = likeService.retrieveAdvancedLikes(likesList1);
        SimplePage<List<AdvancedLikes>> listSimplePage = new SimplePage<>(count, advancedLikes);
        return ResponseUtil.build(HttpStatus.OK.value(), "获取签约结果意向表表成功！", listSimplePage);
    }

//    @GetMapping({"/listSearch"})
//    @ApiOperation(
//            value = "搜索/分页显示",
//            notes = "输入高校名进行搜索"
//    )
//    @PreAuthorize("hasRole('ADMINISTRATOR')")
//    public Object search(@RequestParam(value = "schoolName", required = false) String likeSchoolName,
//                         @ApiParam(example = "1", value = "分页使用，要第几页的数据") @RequestParam(required = false) Integer page,
//                         @ApiParam(example = "10", value = "分页使用，要该页的几条数据") @RequestParam(required = false) Integer pageSize,
//                         @ApiParam(example = "1", value = "排序方式，从数据库中要的数据使用什么进行排序，如 add_time,update_time") @RequestParam(defaultValue = "1", required = false) String sort,
//                         @ApiParam(example = "desc", value = "排序方式，升序asc还是降序desc") @RequestParam(defaultValue = "desc", required = false) String order) {
//        PageInfo<Likes> likesPageInfo = this.likeService.querySelective((Integer) null, (Integer) null, likeSchoolName, (Integer) null, (String) null, page, pageSize, sort, order, true);
//        List<Likes> list = likesPageInfo.getList();
//        int size = (int) likesPageInfo.getTotal();
//        LinkedHashMap<Likes, List<String>> result = new LinkedHashMap();
//        Iterator var9 = list.iterator();
////        Map<String, Object> map = new HashMap<>();
//        while (var9.hasNext()) {
//            Likes like = (Likes) var9.next();
//            Integer likeuserid = like.getLikeUserId();
//            List<Likes> tmpList = this.likeService.querySelective((Integer) null, likeuserid, (String) null, (Integer) null, (String) null, (Integer) null, (Integer) null, (String) null, (String) null, false).getList();
//            List<String> strings = (List) result.get(like);
//            if (strings == null) {
//                strings = new LinkedList();
//            }
//            Iterator var14 = tmpList.iterator();
//            while (var14.hasNext()) {
//                Likes item = (Likes) var14.next();
//                ((List) strings).add(item.getLikedSchoolName());
//            }
//            result.put(like, strings);
//        }
//        List<SimpleLikes> simpleLikes = new LinkedList<>();
//        for (Map.Entry<Likes, List<String>> entry : result.entrySet()) {
//            //每一个均为一条记录
//            SimpleLikes simpleLike = new SimpleLikes();
//            if (simpleLike.getLikeSchoolName() == null && simpleLike.getLikeUserId() == null) {
//                Likes likes = entry.getKey();
//                simpleLike.setLikeUserId(likes.getLikeUserId());
//                simpleLike.setLikeSchoolName(likes.getLikeSchoolName());
//                Integer count1 = signService.count(likes.getLikeUserId(), null, null, null);
//                Integer count2 = signService.countByLikedUserId(likes.getLikeUserId(), null, null, null);
//                simpleLike.setNumOfSigns(count1 + count2);
//                simpleLike.setNumOfLikes(entry.getValue().size());
//            }
//            List<String> likeds = entry.getValue();
//            List<String> linkedList = new LinkedList<>(likeds);
//            simpleLike.setLikedSchoolNames(linkedList);
//            simpleLikes.add(simpleLike);
//        }
//        SimplePage simplePage = new SimplePage(size, simpleLikes);
//        return ResponseUtil.build(HttpStatus.OK.value(), "获取高校签约意向成功！", simplePage);
//    }

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

//    @GetMapping({"/listSearch"})
//    @ApiOperation(
//            value = "搜索/分页显示",
//            notes = "输入高校名进行搜索"
//    )
//    @PreAuthorize("hasRole('ADMINISTRATOR')")
//    public Object search(@RequestParam(value = "schoolname", required = false) String likeSchoolName,
//                         @ApiParam(example = "1", value = "分页使用，要第几页的数据") @RequestParam(required = false) Integer page,
//                         @ApiParam(example = "10", value = "分页使用，要该页的几条数据") @RequestParam(required = false) Integer pageSize,
//                         @ApiParam(example = "1", value = "排序方式，从数据库中要的数据使用什么进行排序，如 add_time,update_time") @RequestParam(defaultValue = "add_time", required = false) String sort,
//                         @ApiParam(example = "desc", value = "排序方式，升序asc还是降序desc") @RequestParam(defaultValue = "desc", required = false) String order,
//                         @ApiParam(example = "2020", value = "year") @RequestParam(defaultValue = "2020", required = false) Integer year) {
//        List<LikeWithUser> likesList = this.likeService.querySelectiveDistinct(year, likeSchoolName, page, pageSize, sort, order);
//        int count = this.likeService.countDistinct(year, likeSchoolName);
//        List<Likes> likesList1 = new LinkedList<>();
//        for (LikeWithUser likeWithUser : likesList) {
//            Likes likes = new Likes();
//            likes.setId(likeWithUser.getLikeId());
//            likes.setLikeUserId(likeWithUser.getLikeUserId());
//            likes.setLikedUserId(likeWithUser.getLikedUserId());
//            likesList1.add(likes);
//        }
//        List<AdvancedLikes> advancedLikes = likeService.retrieveAdvancedLikes(likesList1);
//        SimplePage<List<AdvancedLikes>> listSimplePage = new SimplePage<>(count, advancedLikes);
//        return ResponseUtil.build(HttpStatus.OK.value(), "获取签约结果意向表表成功！", listSimplePage);
//    }

    @ApiOperation(
            value = "导出单项签约意向表",
            notes = "导出单项签约意向表"
    )
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping({"/exportLikesFormSingle"})
    public void exportLikesFormSingle(@ApiParam(example = "意向的的ids", value = "[1,2,3]") @RequestParam(required = false, value = "likeIds") Integer[] likeIds, HttpServletResponse response) throws IOException {
        List<Likes> likesList;
        if (likeIds == null || likeIds.length == 0) {
            likesList = likeService.querySelective(null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null, null).getList();
        } else {
            likesList = new LinkedList<>();
            for (Integer likeId : likeIds) {
                likesList.addAll(likeService.querySelective(likeId, null, null, null, null, null, null, null, null, null, null).getList());
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
        List<Likes> likesList;
        if (userIds == null || userIds.length == 0) {
            likesList = likeService.querySelective(null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null, null).getList();
        } else {
            likesList = new LinkedList<>();
            for (Integer userId : userIds) {
                likesList.addAll(likeService.querySelective(null, userId, null, null, null, null, null, null, null, null, null).getList());
            }
        }
        Workbook workbook = this.likeService.exportLikesForm(likesList);
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
        List<Likes> likes = this.likeService.querySelective((Integer) null, likeUserId, (String) null, (Integer) null, (String) null, (Integer) null, (Integer) null, (String) null, (String) null, (Boolean) null, null).getList();
        SimpleUserLikes simpleUserLikes = new SimpleUserLikes();
        List<Likes> likesList = new LinkedList<>();
        for (Likes like : likes) {
            if (simpleUserLikes.getUserId() == null) {
                simpleUserLikes.setUserId(like.getLikeUserId());
            }
            like.setLikeUserId(null);
            like.setAddTime(null);
            like.setUpdateTime(null);
            like.setDeleted(null);
            likesList.add(like);
        }
        simpleUserLikes.setLikes(likesList);
        return ResponseUtil.build(HttpStatus.OK.value(), "获取该学校的所有意向成功！", simpleUserLikes);
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
        List<User> users = this.userService.querySelectiveLike(likeUserId, (String) null, (String) null, (String) null, (String) null, (String) null, (Integer) null, (String) null, (String) null, (Integer) null, (String) null, (String) null, (Integer) null, (String) null, (Integer) null, (Integer) null, (String) null, (String) null);
        if (users.size() == 0) {
            throw new UserNotFoundException("用户id不存在！");
        } else {
            User likeUser = users.get(0);
            for (Integer likedUserId : likedUserIds) {
                try {
                    users = this.userService.querySelectiveLike(likedUserId, (String) null, (String) null, (String) null, (String) null, (String) null, (Integer) null, (String) null, (String) null, (Integer) null, (String) null, (String) null, (Integer) null, (String) null, (Integer) null, (Integer) null, (String) null, (String) null);
                    if (users.size() == 0) {
                        throw new UserNotFoundException("用户id不存在！");
                    } else {
                        User likedUser = users.get(0);
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

    private void checkIfNeedSign(Likes like) {
        List<Likes> likesList = likeService.querySelective(null, like.getLikedUserId(), null, like.getLikeUserId(), null, null, null, null, null, null, null).getList();
        //管理端添加一则意向，若这则意向的likeUserId->likedUserId 对应的意向 likedUserId->likeUserId在意向表中已经存在了，说明可实行自动签约
        if (likesList.size() > 0) {
            List<Sign> signs1 = signService.querySelective(null, like.getLikeUserId(), null, like.getLikedUserId(), null, null, null, null, null, null);
            List<Sign> signs2 = signService.querySelective(null, like.getLikedUserId(), null, like.getLikeUserId(), null, null, null, null, null, null);
            //说明该则意向已经有对应
            if (signs1.size() + signs2.size() > 0) {
                return;
            }
            Sign sign = new Sign();
            sign.setSignUserId(like.getLikeUserId());
            sign.setSignSchoolName(like.getLikeSchoolName());
            sign.setSignedUserId(like.getLikedUserId());
            sign.setSignedSchoolName(like.getLikedSchoolName());
            signService.add(sign);
        }

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
        if (userIds == null || userIds.length != 0) {
            users = this.userService.querySelectiveLike((Integer) null, (String) null, (String) null, (String) null, (String) null, (String) null, (Integer) null, (String) null, (String) null, (Integer) null, (String) null, (String) null, (Integer) null, (String) null, (Integer) null, (Integer) null, (String) null, (String) null);
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
                    this.logger.warn("该邮箱号不存在:" + user.getUsername());
                }
            }
        }).start();
        return ResponseUtil.build(HttpStatus.OK.value(), "签约意向提醒成功!", (Object) null);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping({"/listSchools"})
    @ApiOperation(
            value = "签约意向管理->修改签约意向->添加意向高校->输入高校名搜索",
            notes = "签约意向管理->修改签约意向->添加意向高校->输入高校名搜索"
    )
    public Object listSchools(@RequestParam(value = "schoolName", required = false) String schoolName, @ApiParam(example = "1", value = "分页使用，要第几页的数据") @RequestParam(required = false) Integer page, @ApiParam(example = "10", value = "分页使用，要该页的几条数据") @RequestParam(required = false) Integer pageSize, @ApiParam(example = "1", value = "排序方式，从数据库中要的数据使用什么进行排序，如 add_time,update_time") @RequestParam(defaultValue = "add_time", required = false) String sort, @ApiParam(example = "desc", value = "排序方式，升序asc还是降序desc") @RequestParam(defaultValue = "desc", required = false) String order) {
        List<User> users = this.userService.querySelectiveLike((Integer) null, (String) null, schoolName, (String) null, (String) null, (String) null, (Integer) null, (String) null, (String) null, (Integer) null, (String) null, (String) null, (Integer) null, (String) null, page, pageSize, sort, order);
        int size = this.userService.count(schoolName);
        List<SimpleUser> simpleUsers = new LinkedList();
        Iterator var9 = users.iterator();

        while (var9.hasNext()) {
            User user = (User) var9.next();
            SimpleUser simpleUser = new SimpleUser();
            this.fill(user, simpleUser);
            simpleUsers.add(simpleUser);
        }
        SimplePage simplePage = new SimplePage(size, simpleUsers);
        return ResponseUtil.build(HttpStatus.OK.value(), "搜索用户列表成功！", simplePage);
    }

    private void fill(User user, SimpleUser simpleUser) {
        simpleUser.setId(user.getId());
        simpleUser.setSchoolName(user.getSchoolName());
    }
}
