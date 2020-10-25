package com.school.utils;

import com.school.dto.Certification;
import com.school.exception.UserNotFoundException;
import com.school.model.Pics;
import com.school.model.User;
import com.school.service.impl.PicsServiceImpl;
import com.school.service.impl.SignServiceImpl;
import com.school.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommonUtil {
    @Autowired
    private SignServiceImpl signService;

    @Value("${file.name.witness}")
    private String witness;
    @Value("${spring.file.path}")
    private String springFilePath;

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private PicsServiceImpl picsService;
    @Autowired
    FileUtil fileUtil;

    public void fill(Certification certification, Integer signUserId, Integer signedUserId) throws UserNotFoundException {
        User signUser = userService.findById(signUserId);
        certification.setSignUserCountry(signUser.getCountry());
        certification.setSignUserProfession(signUser.getProfession());
        certification.setSignUserSchoolName(signUser.getSchoolname());
        List<Pics> logos = picsService.querySelective(null, signUserId, FileEnum.LOGO.value());
        if (logos.size() == 0) {
            throw new NullPointerException("当前用户尚未上传logo！");
        }
        String signUserLogo = springFilePath + logos.get(0).getLocation();
        List<Pics> signatures = picsService.querySelective(null, signUserId, FileEnum.SIGNATURE.value());
        if (signatures.size() == 0) {
            throw new NullPointerException("当前用户尚未上传校长签章！");
        }
        String signUserSignature = springFilePath + signatures.get(0).getLocation();
        certification.setSignUserLogo(signUserLogo);
        certification.setSignUserSignature(signUserSignature);

        //------------------------------
        User signedUser = userService.findById(signedUserId);
        certification.setSignedUserCountry(signedUser.getCountry());
        certification.setSignedUserProfession(signedUser.getProfession());
        certification.setSignedUserSchoolName(signedUser.getSchoolname());
        List<Pics> signedUserlogos = picsService.querySelective(null, signedUserId, FileEnum.LOGO.value());
        if (signedUserlogos.size() == 0) {
            throw new NullPointerException("当前用户尚未上传logo！");
        }
        String signedUserLogo = springFilePath + signedUserlogos.get(0).getLocation();
        List<Pics> signedUserSignatures = picsService.querySelective(null, signedUserId, FileEnum.SIGNATURE.value());
        if (signedUserSignatures.size() == 0) {
            throw new NullPointerException("当前用户尚未上传校长签章！");
        }
        String signedUserSignature = springFilePath + signedUserSignatures.get(0).getLocation();
        certification.setSignedUserLogo(signedUserLogo);
        certification.setSignedUserSignature(signedUserSignature);
    }

}
