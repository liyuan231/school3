package com.school.controller.common;

import com.school.exception.UserNotFoundException;
import com.school.model.Sign;
import com.school.service.impl.SignServiceImpl;
import com.school.utils.FileUtil;
import com.school.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(value = "签约证书下载", tags = {"签约证书下载"})
@RestController
@RequestMapping("/api/admin/certification")
public class CertificationDownloadController {
    @Autowired
    private SignServiceImpl signService;

    @Autowired
    FileUtil fileUtil;

    //4@qq.com->
    //Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWRpZW5jZSI6IjRAcXEuY29tIiwic3Vic2NyaWJlciI6ImFsbCIsInJvbGVzIjoiW1wiVVNFUlwiXSIsImV4cGlyYXRpb24iOiIyMDIwLTEwLTI4IDEwOjAyOjA5IiwiaXNzdWVBdCI6IjIwMjAtMTAtMTggMTA6MDI6MDkiLCJpc3N1ZXIiOiJMaXl1YW4ifQ.Tvr6ZpJmq7pAA5gf3o-B3gTUhP976eEm2CfKW_6021prHehHoKAGx-zSaJWfQ66vXGXfFZmBHEy2BhJ-ySJITEuoKif8r3sYnDALr5NJUbKbmb33lL9zto6rAyCJQdA9sNlcVz_g8a0jtbNjnRrQAEpeRevn3bY8q8-5Wg9yytQ

    //9@qq.com
    //Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWRpZW5jZSI6IjlAcXEuY29tIiwic3Vic2NyaWJlciI6ImFsbCIsInJvbGVzIjoiW1wiVVNFUlwiXSIsImV4cGlyYXRpb24iOiIyMDIwLTEwLTI4IDEwOjAyOjMxIiwiaXNzdWVBdCI6IjIwMjAtMTAtMTggMTA6MDI6MzEiLCJpc3N1ZXIiOiJMaXl1YW4ifQ.eWOKEFhjqQhdgO6Zw2Vd3R8mT6uEzax5J6HO_2Lt6_eWxC1Ctgu6YYD4gBJfr9X9caLwrGiQuJSLrvWt57RUugl9VEsSh4K3zVkTnalIE9_zY5ZRmMeq4I8M73d3KJc07sC6NcEhFP1mpibzeeh5mlwkuOfrixS2uZ0FCEJwmEA

    //管理员
    //Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWRpZW5jZSI6IjI4MTIzMjk0MjVAcXEuY29tIiwic3Vic2NyaWJlciI6ImFsbCIsInJvbGVzIjoiW1wiQURNSU5JU1RSQVRPUlwiXSIsImV4cGlyYXRpb24iOiIyMDIwLTEwLTI4IDEwOjA4OjIxIiwiaXNzdWVBdCI6IjIwMjAtMTAtMTggMTA6MDg6MjEiLCJpc3N1ZXIiOiJMaXl1YW4ifQ.w47pGSpIhUlceOO7w4goTo3bcXUHLYts_9XxLvgMWLL8h_2UHI_W1hL9zCa65poN4zt3kpUKmBc9b1D1I1GclvuKZMNNer31OODW26CeM_TKW9QLGCicOo2rok4xL8cKH3zQCPOqqw-5zBqniJKcXu8TF6sFiXm4QjhZYSrjBus

    @GetMapping("/download/{signId}")
//    @PreAuthorize("hasAnyRole('USER','ADMINISTRATOR')")
    @ApiOperation(value = "下载签约证书", notes = "下载签约证书")
    public String downloadCertification(@PathVariable("signId") Integer signId,
                                        HttpServletResponse response) throws UserNotFoundException {

        List<Sign> signs = signService.querySelective(signId, null, null, null, null, null, null, null, null, null);
        if (signs.size() == 0) {
            return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), "该则签约id不存在！");
        }
        Sign sign = signs.get(0);
//        User user = userService.retrieveUserByToken();
//        if (!sign.getSignuserid().equals(user.getId())) {
//            return ResponseUtil.build(HttpStatus.BAD_REQUEST.value(), "当前用户与目标的签约id不符！");
//        }
        return fileUtil.downloadCertification(response, sign);
    }


}
