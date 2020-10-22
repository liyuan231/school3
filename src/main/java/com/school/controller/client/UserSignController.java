package com.school.controller.client;

import com.school.exception.SignAlreadyExistException;
import com.school.exception.SignNotCorrectException;
import com.school.exception.UserNotFoundException;
import com.school.service.impl.SignServiceImpl;
import com.school.service.impl.UserServiceImpl;
import com.school.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"选择签约"}, value = "选择签约->确认签约")
@RestController
@RequestMapping("/api/client/sign")
public class UserSignController {
    @Autowired
    private SignServiceImpl signService;
    @Autowired
    private UserServiceImpl userService;


    private final Logger logger = LoggerFactory.getLogger(getClass());

//    /**
//     * 传入被签约一方的userId
//     *
//     * @param signedUserId
//     * @return
//     */
//    @PreAuthorize("hasAnyRole('ADMINISTRATOR','USER')")
//    @ApiOperation(value = "签约", notes = "当前用户和某一用户进行签约")
//    @GetMapping("/sign/{signedUserId}")
//    public String sign(@ApiParam(value = "被签约方的id", example = "1") @PathVariable("signedUserId") Integer signedUserId) throws UserNotFoundException, SignAlreadyExistException, SignNotCorrectException {
//        signService.sign(signedUserId);
//        return ResponseUtil.build(HttpStatus.OK.value(), "签约成功！", null);
//    }

    @PreAuthorize("hasAnyRole('USER')")
    @ApiOperation(value = "批量签约", notes = "当前用户批量进行签约")
    @GetMapping("/batchSign")
    public String sibatchSign(@RequestParam("signedUserIds") Integer[] signedUserIds) {
        for (Integer signedUserId : signedUserIds) {
            try {
                signService.sign(signedUserId);
            } catch (UserNotFoundException | SignAlreadyExistException | SignNotCorrectException e) {
//                e.printStackTrace();
                logger.warn("当前被签约的高校id不存在->" + signedUserId);
            }
        }
        return ResponseUtil.build(HttpStatus.OK.value(), "批量签约用户成功！");
    }


//    @GetMapping("/list")
//    @ApiOperation(value = "查询", notes = "查询签约")
//    public Object list(@ApiParam(value = "待查询签约的id", example = "1") @RequestParam(value = "id", required = false) Integer id,
//                       @ApiParam(value = "查询某一用户主动和谁签过约", example = "1") @RequestParam(value = "signUserId", required = false) Integer signUserId,
//                       @ApiParam(value = "查询某一用户被动和谁签过约", example = "2") @RequestParam(required = false, value = "signedUserId") Integer signedUserId,
//                       @ApiParam(value = "分页，要第几页的数据", example = "1") @RequestParam(defaultValue = "1") Integer page,
//                       @ApiParam(value = "分页，该页数据要多少条", example = "10") @RequestParam(defaultValue = "10") Integer limit,
//                       @ApiParam(value = "排序，依照什么进行排序，add_time或update_time", example = "add_time") @RequestParam(defaultValue = "add_time") String sort,
//                       @ApiParam(value = "排序，升序(asc)或降序(desc)", example = "desc") @RequestParam(defaultValue = "desc") String order) {
//        List<Sign> signList = signService.querySelective(id, signUserId, null, signedUserId, null, null, page, limit, sort, order);
//        return ResponseUtil.build(HttpStatus.OK.value(), "获取签约列表成功！", signList);
//    }

//    @PostMapping("/update/{id}/{signUserId}/{signedUserId}")
//    @ApiOperation(value = "更新", notes = "依据id更新一则签约")
//    @PreAuthorize("hasAnyRole('USER')")
//    public Object update(@ApiParam(example = "1", value = "该则签约的id") @PathVariable("id") Integer id,
//                         @ApiParam(example = "1", value = "该则签约中主动签约的用户的id") @PathVariable("signUserId") Integer signUserId,
//                         @ApiParam(example = "2", value = "该则签约中被签约的用户id") @PathVariable("signedUserId") Integer signedUserId) throws UserSignCorrespondException, SignNotFoundException, UserNotFoundException {
//        List<User> users = userService.querySelectiveLike(signUserId, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
//        if (users.size() == 0) {
//            throw new UserNotFoundException("用户id不存在！");
//        }
//        User signUser = users.get(0);
//        users = userService.querySelectiveLike(signedUserId, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
//        if (users.size() == 0) {
//            throw new UserNotFoundException("用户id不存在！");
//        }
//        User signedUser = users.get(0);
//        signService.update(id, signUserId, signUser.getSchoolname(), signedUserId, signedUser.getSchoolname(), null);
//        return ResponseUtil.build(HttpStatus.OK.value(), "更新一则签约成功!", null);
//    }


//    @DeleteMapping("/delete/{id}")
//    @ApiOperation(value = "删除", notes = "依据id删除一则签约")
//    @PreAuthorize("hasAnyRole('USER')")
//    public Object delete(@PathVariable("id") Integer id) throws UserSignCorrespondException, SignNotFoundException, UserNotFoundException {
//        signService.deleteById(id);
//        return ResponseUtil.build(HttpStatus.OK.value(), "删除一则签约成功!", null);
//    }

//    @GetMapping("/listBySignUserId")
//    @ApiOperation(value = "查询我主动签约的用户", notes = "查询我主动签约的用户")
//    @PreAuthorize("hasAnyRole('USER')")
//    public Object queryBySignUserId() throws UserNotFoundException {
//        List<Sign> signs = signService.findBySignUserId();
//        List<User> users = new LinkedList<>();
//        for (Sign sign : signs) {
//            User user = userService.findById(sign.getSigneduserid());
//            users.add(user);
//        }
//        return ResponseUtil.build(HttpStatus.OK.value(), "获取我主动签约记录成功！", users);
//    }

//    @GetMapping("/listBySignedUserId")
//    @ApiOperation(value = "查询主动和我签约的用户", notes = "查询主动和我签约的用户（我是被动）")
//    @PreAuthorize("hasAnyRole('ADMINISTRATOR','USER')")
//    public Object queryBySignedUserId() {
//        List<Sign> signs = signService.findBySignedUserId();
//        List<User> users = new LinkedList<>();
//        for (Sign sign : signs) {
//            User user = userService.findById(sign.getSignuserid());
//            users.add(user);
//        }
//        return ResponseUtil.build(HttpStatus.OK.value(), "查询主动和我签约的用户成功！", users);
//    }
}
