//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.school.controller.admin;

import com.school.dto.SignWithUser;
import com.school.dto.SimplePage;
import com.school.exception.SignNotFoundException;
import com.school.exception.UserNotFoundException;
import com.school.exception.UserSignCorrespondException;
import com.school.model.Sign;
import com.school.model.User;
import com.school.service.impl.EmailServiceImpl;
import com.school.service.impl.SignServiceImpl;
import com.school.service.impl.UserServiceImpl;
import com.school.utils.ResponseUtil;
import com.school.utils.Status;
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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Api(
        value = "高校信息管理，高校签约名单管理，管理签约",
        tags = {"高校签约"}
)
@RestController("adminSignController")
@RequestMapping({"/api/admin/sign"})
public class AdminSignController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    SignServiceImpl signService;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    EmailServiceImpl emailService;

    public AdminSignController() {
    }

//    @GetMapping({"/listSearch"})
//    @ApiOperation(
//            value = "高校信息管理/签约公示->搜索/分页显示",
//            notes = "签约公示->搜索/分页显示"
//    )
//    @PreAuthorize("hasRole('ADMINISTRATOR')")
//    public String search(@ApiParam(example = "schoolName", value = "schoolname") @RequestParam(value = "schoolname", required = false) String schoolname, @ApiParam(example = "2020", value = "year") @RequestParam(value = "year", required = false) Integer year, @ApiParam(example = "1", value = "分页使用，要第几页的数据") @RequestParam(value = "page", required = false) Integer page, @ApiParam(example = "10", value = "分页使用，要该页的几条数据") @RequestParam(value = "pageSize", required = false) Integer pageSize, @ApiParam(example = "1", value = "排序方式，从数据库中要的数据使用什么进行排序，如 add_time,update_time") @RequestParam(defaultValue = "add_time") String sort, @ApiParam(example = "desc", value = "排序方式，升序asc还是降序desc") @RequestParam(defaultValue = "desc") String order) {
//        List<Sign> signs = this.signService.querySelective((Integer) null, (Integer) null, schoolname, (Integer) null, (String) null, year, page, pageSize, sort, order);
//        for (Sign sign : signs) {
//            sign.setAddTime(null);
//            sign.setDeleted(null);
//        }
//        Integer count = this.signService.count(null, schoolname, (String) null, year);
//        SimplePage<List<Sign>> result = new SimplePage(count, signs);
//        return ResponseUtil.build(HttpStatus.OK.value(), "获取该关键字学校的签约结果成功！", result);
//    }

    @GetMapping({"/listSearch"})
    @ApiOperation(
            value = "高校信息管理/签约公示->搜索/分页显示",
            notes = "签约公示->搜索/分页显示"
    )
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public String search(@ApiParam(example = "schoolName", value = "schoolname") @RequestParam(value = "schoolname", required = false) String schoolname, @ApiParam(example = "2020", value = "year") @RequestParam(value = "year", required = false) Integer year, @ApiParam(example = "1", value = "分页使用，要第几页的数据") @RequestParam(value = "page", required = false) Integer page, @ApiParam(example = "10", value = "分页使用，要该页的几条数据") @RequestParam(value = "pageSize", required = false) Integer pageSize, @ApiParam(example = "add_time", value = "排序方式，从数据库中要的数据使用什么进行排序，如 add_time,update_time") @RequestParam(defaultValue = "add_time") String sort, @ApiParam(example = "desc", value = "排序方式，升序asc还是降序desc") @RequestParam(defaultValue = "desc") String order) {
        List<SignWithUser> signWithUsers = this.signService.querySelective(year, schoolname, page, pageSize, sort, order);
        int count = this.signService.count(year, schoolname);
        SimplePage<List<Sign>> result = new SimplePage(count, signWithUsers);
        return ResponseUtil.build(HttpStatus.OK.value(), "获取该关键字学校的签约结果成功！", result);
    }

    @PostMapping("/changeStatus/{signId}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @ApiOperation(value = "修改签约状态，即是否公示", notes = "修改签约状态")
    public String switchSignId(@ApiParam(value = "该则签约的id", example = "1") @PathVariable("signId") Integer signId) throws SignNotFoundException, UserSignCorrespondException, UserNotFoundException {
        Sign sign = signService.findById(signId);
        if (sign == null) {
            return ResponseUtil.build(HttpStatus.OK.value(), "该则签约不存在！");
        }
        sign.setStatus(sign.getStatus() == Status.SIGN_SHOW ? Status.SIGN_HIDDEN : Status.SIGN_SHOW);
        signService.update(sign);
        return ResponseUtil.build(HttpStatus.OK.value(), "修改该则签约状态成功！");
    }


    @ApiOperation(
            value = "签约公示->导出签约名单",
            notes = "签约公示->导出签约名单"
    )
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping({"/exportSignForm"})
    public void exportSignForm(@ApiParam(value = "多则签约的id", example = "[1,2,3]") @RequestParam(value = "signIds", required = false) Integer[] signIds, HttpServletResponse response) throws IOException {
        List<Sign> signs;
        if (signIds == null || signIds.length == 0) {
            signs = signService.querySelective((Integer) null, (Integer) null, (String) null, (Integer) null, (String) null, (Integer) null, (Integer) null, (Integer) null, (String) null, (String) null);
        } else {
            signs = new LinkedList<>();
            for (Integer signId : signIds) {
                Sign sign = signService.findById(signId);
                signs.add(sign);
            }
        }
        Workbook workbook = this.signService.exportSignForm(signs);
        String fileName = Sign.class.getSimpleName() + ".xls";
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        workbook.write(outputStream);
        outputStream.close();
        workbook.close();
    }

    @ApiOperation(
            value = "签约结果管理->发送消息提醒高校查看签约结果",
            notes = "签约结果管理->发送消息提醒高校查看签约结果"
    )
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping({"/remind"})
    public String remindSchools(@ApiParam(example = "用户id数组", value = "[1,2,3]") @RequestParam("userIds") Integer[] userIds) {
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
                    this.emailService.send(user.getUsername(), "!签约结果提醒!", "记得查看签约结果~");
                } catch (MailException var5) {
                    this.logger.warn("该邮箱号不存在->" + user.getUsername());
                }
            }
        }).start();
        return ResponseUtil.build(HttpStatus.OK.value(), "提醒成功!", (Object) null);
    }

    @PostMapping("/rollBack")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @ApiOperation(value = "高校信息管理->高校签约名单管理->回退签约名单", notes = "即管理员选中多个签约记录，然后点击回退，因此前端需传来一个数组")
    public Object rollBack(@RequestParam("signIds") Integer[] signIds) {
        for (Integer signId : signIds) {
            try {
                signService.deleteById(signId);
            } catch (UserNotFoundException | UserSignCorrespondException | SignNotFoundException e) {
                logger.warn("删除签约记录失败，该则签约不存在->" + signId);
            }
        }
        return ResponseUtil.build(HttpStatus.OK.value(), "回退签约信息成功！");
    }


}
