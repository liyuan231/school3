package com.school.controller.client;

import com.school.dto.FullUser;
import com.school.dto.LikeOrSign;
import com.school.exception.SignAlreadyExistException;
import com.school.exception.SignNotCorrectException;
import com.school.exception.UserNotFoundException;
import com.school.model.Pics;
import com.school.model.Sign;
import com.school.model.User;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@Api(tags = {"选择签约"}, value = "选择签约->确认签约")
@RestController
@RequestMapping("/api/client/sign")
public class UserSignController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SignServiceImpl signService;
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private PicsServiceImpl picsService;

    @Value("${spring.file.path}")
    private String springFilePath;

    @Value("${file.default.logo}")
    private String defaultLogo;


    //贵校已于20个高校达成合作意向

    @PreAuthorize("hasAnyRole('USER')")
    @ApiOperation(value = "批量签约,不用此接口进行签约，用户批量选择意向时会自动与已选择自己的学校进行匹配签约", notes = "当前用户批量进行签约")
    @GetMapping("/batchSign")
    public String batchSign(@RequestParam("signedUserIds") Integer[] signedUserIds) {
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

    @PreAuthorize("hasAnyRole('USER')")
    @ApiOperation(value = "与我达成合作的高校", notes = "与我达成合作的高校")
    @GetMapping("/list")
    public String signList() {
        User user = userService.retrieveUserByToken();
        List<Sign> signs = signService.queryBySignUserId(user.getId());
        List<Sign> signs1 = signService.queryBySignedUserId(user.getId());
        signs.addAll(signs1);
        List<User> users = new LinkedList<>();
        for (Sign sign : signs) {
            //当前用户为主动签约
            if (sign.getSignUserId().equals(user.getId())) {
                User user1 = userService.queryById(sign.getSignedUserId(), User.Column.id, User.Column.schoolName, User.Column.contact, User.Column.address, User.Column.telephone, User.Column.profession, User.Column.country, User.Column.website, User.Column.location, User.Column.schoolCode, User.Column.username);
                users.add(user1);
            } else {
                User user1 = userService.queryById(sign.getSignUserId(), User.Column.id, User.Column.schoolName, User.Column.contact, User.Column.address, User.Column.telephone, User.Column.profession, User.Column.country, User.Column.website, User.Column.location, User.Column.schoolCode, User.Column.username);
                users.add(user1);
            }
        }
        List<FullUser> fullUsers = new LinkedList<>();
        for (User u : users) {
            FullUser fullUser = new FullUser();
            fullUser.setUser(u);
            List<Pics> logos = picsService.querySelective(null, u.getId(), FileEnum.LOGO.value());
            if (logos.size() > 0) {
                fullUser.setLogo(springFilePath + logos.get(0).getLocation());
            }
            List<Pics> signatures = picsService.querySelective(null, u.getId(), FileEnum.SIGNATURE.value());
            if (signatures.size() > 0) {
                fullUser.setSignature(springFilePath + signatures.get(0).getLocation());
            }
            fullUsers.add(fullUser);
        }
        return ResponseUtil.build(HttpStatus.OK.value(), "与我达成合作的高校", fullUsers);
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

    @GetMapping("/listBySignedUserId")
    @ApiOperation(value = "查询主动和我签约的用户", notes = "查询主动和我签约的用户（我是被动）")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR','USER')")
    public Object queryBySignedUserId() {
        User u = userService.retrieveUserByToken();
        List<Sign> signs = signService.queryBySignedUserId(u.getId());
        List<LikeOrSign> likeOrSigns = new LinkedList<>();
        for (Sign sign : signs) {
            LikeOrSign likeOrSign = new LikeOrSign();
            likeOrSign.setSignIdOrLikeId(sign.getId());
            likeOrSign.setSigned(true);
            likeOrSign.setUpdateTime(sign.getAddTime());
            likeOrSign.setSignTime(sign.getUpdateTime());
            User user = userService.queryById(sign.getSignUserId(), User.Column.id, User.Column.schoolName);
            likeOrSign.setSchoolId(user.getId());
            likeOrSign.setSchoolName(user.getSchoolName());
            List<Pics> logos = picsService.querySelective(user.getId(), FileEnum.LOGO.value());
            if (logos.size() > 0) {
                likeOrSign.setLogo(logos.get(0).getLocation());
            } else {
                likeOrSign.setLogo(null);
            }
//            simpleIntentions.add(likeOrSign);
            likeOrSigns.add(likeOrSign);
        }

        return ResponseUtil.build(HttpStatus.OK.value(), "查询主动和我签约成功的用户成功！", likeOrSigns);
    }

    @GetMapping("/countSigns")
    public String countSigns() {
        User user = userService.retrieveUserByToken();
        List<Sign> signs1 = signService.queryBySignUserId(user.getId(), Sign.Column.id);
        List<Sign> signs = signService.queryBySignedUserId(user.getId(), Sign.Column.id);
        return ResponseUtil.build(HttpStatus.OK.value(), "贵校已于n个高校达成合作意向！", signs1.size() + signs.size());
    }

}
