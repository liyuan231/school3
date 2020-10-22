package com.school.controller.client;

import com.school.dto.SimplePage;
import com.school.dto.SimpleUser;
import com.school.model.User;
import com.school.service.impl.UserServiceImpl;
import com.school.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * description：
 * author：崔童
 * date：2020/10/11
 */
//@Api(tags = {"选择签约|客户端查询"})
//@RestController
//@RequestMapping("/api/client/user")
public class UserAllController {
//    @Autowired
    private UserServiceImpl userService;

//    @GetMapping("/select")
//    @ApiOperation(value = "查询所有学校", notes = "查询所有学校")
//    @PreAuthorize("hasAnyRole('USER')")
//    public Object list(@ApiParam(value = "分页，要第几页的数据", example = "1") @RequestParam(defaultValue = "1") Integer page,
//                       @ApiParam(value = "分页，该页数据要多少条", example = "10") @RequestParam(defaultValue = "10") Integer limit) {
//        List<User> userList = userService.querySelectiveAllByPage(page, limit);
//        Integer size = this.userService.count();
//        SimplePage<List<SimpleUser>> simplePage = new SimplePage(size, userList);
//        return ResponseUtil.build(HttpStatus.OK.value(), "获取列表成功！", simplePage);
//    }
//
//    @GetMapping("select/like")
//    @ApiOperation(value = "勾选参会学校，学校信息模糊查询", notes = "模糊查询")
//    @PreAuthorize("hasAnyRole('USER')")
//    public Object list(@ApiParam(value = "关键字", example = "") @RequestParam(defaultValue = "") String key) {
//        List<User> userList = userService.querySelectiveAllDim(key);
//        return ResponseUtil.build(HttpStatus.OK.value(), "获取列表成功！", userList);
//    }
//
//    @GetMapping("select/like/{schoolname}")
//    @ApiOperation(value = "学校名称模糊查询", notes = "模糊查询")
////    @PreAuthorize("hasAnyRole('ADMINISTRATOR','USER')")
//    public Object list(@ApiParam(value = "关键字", example = "") @RequestParam(defaultValue = "") String key,
//                       @ApiParam(value = "分页，要第几页的数据", example = "1") @RequestParam(defaultValue = "1") Integer page,
//                       @ApiParam(value = "分页，该页数据要多少条", example = "10") @RequestParam(defaultValue = "10") Integer limit) {
//        List<User> userList = userService.querySelectiveBySchoolnameDim(key, page, limit);
//        Integer size = this.userService.count(key);
//        SimplePage<List<SimpleUser>> simplePage = new SimplePage(size, userList);
//        return ResponseUtil.build(HttpStatus.OK.value(), "获取列表成功！", simplePage);
//    }

}