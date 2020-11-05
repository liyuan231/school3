//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.school.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.school.dao.SignMapper;
import com.school.dao.SignWithUserMapper;
import com.school.dto.SignWithUser;
import com.school.exception.*;
import com.school.model.Sign;
import com.school.model.Sign.Column;
import com.school.model.SignExample;
import com.school.model.SignExample.Criteria;
import com.school.model.User;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class SignServiceImpl {
    @Resource
    private SignMapper signMapper;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserToRoleServiceImpl userToRoleService;
    @Resource
    private SignWithUserMapper signWithUserMapper;

    public SignServiceImpl() {
    }

    public void sign(Integer signedUserId) throws UserNotFoundException, SignAlreadyExistException, SignNotCorrectException {
        User signUser = this.userService.retrieveUserByToken();
        User signedUser = this.userService.findById(signedUserId);
        if (signedUser == null) {
            throw new UserNotFoundException("被签约的用户不存在！");
        } else if (signUser.getId().equals(signedUserId)) {
            throw new SignNotCorrectException("不能和自己签约！");
        } else {
            List<Sign> signs1 = this.querySelective((Integer) null, signUser.getId(), (String) null, signedUser.getId(), (String) null, (Integer) null, (Integer) null, (Integer) null, (String) null, (String) null);
            List<Sign> signs2 = this.querySelective((Integer) null, signedUser.getId(), (String) null, signUser.getId(), (String) null, (Integer) null, (Integer) null, (Integer) null, (String) null, (String) null);
            if (signs1.size() < 1 && signs2.size() < 1) {
                this.add(signUser.getId(), signUser.getSchoolName(), signedUserId, signedUser.getSchoolName());
            } else {
                throw new SignAlreadyExistException("已经和该用户签约过了！！");
            }
        }
    }

    public void add(Integer signUserId, String signUserSchoolName, Integer signedUserId, String signedUserSchoolName) {
        List<Sign> signs = queryBySignUserAndSignedUserId(signUserId, signedUserId);
        List<Sign> signs1 = queryBySignUserAndSignedUserId(signedUserId, signUserId);
        if(signs.size()+signs1.size()>0){
            return;
        }
        Sign sign = new Sign();
        sign.setSignUserId(signUserId);
        sign.setSignSchoolName(signUserSchoolName);
        sign.setSignedUserId(signedUserId);
        sign.setSignedSchoolName(signedUserSchoolName);
        this.add(sign);
    }

    private List<Sign> querySelective(Sign sign) {
        return this.querySelective(sign.getId(), sign.getSignUserId(), sign.getSignSchoolName(), sign.getSignedUserId(), sign.getSignedSchoolName(), (Integer) null, (Integer) null, (Integer) null, (String) null, (String) null);
    }

    public List<Sign> querySelective(Integer id, Integer signUserId, String signSchoolName, Integer signedUserId, String signedSchoolName, Integer year, Integer page, Integer limit, String sort, String order) {
        SignExample signExample = new SignExample();
        Criteria criteria = signExample.createCriteria();
        if (!StringUtils.isEmpty(id)) {
            criteria.andIdEqualTo(id);
        }

        if (!StringUtils.isEmpty(signUserId)) {
            criteria.andSignUserIdEqualTo(signUserId);
        }

        if (StringUtils.hasText(signSchoolName)) {
            criteria.andSignSchoolNameLike("%" + signSchoolName + "%");
        }

        if (!StringUtils.isEmpty(signedUserId)) {
            criteria.andSignedUserIdEqualTo(signedUserId);
        }

        if (StringUtils.hasText(signedSchoolName)) {
            criteria.andSignedSchoolNameLike("%" + signedSchoolName + "%");
        }

        if (StringUtils.hasText(sort) && StringUtils.hasText(order)) {
            signExample.setOrderByClause(sort + " " + order);
        }

        if (!StringUtils.isEmpty(year)) {
            LocalDateTime end = LocalDateTime.of(year, 12, 31, 23, 59);
            LocalDateTime start = LocalDateTime.of(year, 1, 1, 0, 0);
            criteria.andUpdateTimeBetween(start, end);
        }

        if (page != null || limit != null) {
            if (page == null) {
                PageHelper.startPage(1, limit);
            } else if (limit == null) {
                PageHelper.startPage(page, 10);
            } else {
                PageHelper.startPage(page, limit);
            }
        }

        criteria.andDeletedEqualTo(false);
        List<Sign> signs = this.signMapper.selectByExampleSelective(signExample, new Column[0]);
        PageInfo<Sign> signPageInfo = new PageInfo(signs);
        return signPageInfo.getList();
    }

    public void add(Sign sign) {
        sign.setAddTime(LocalDateTime.now());
        sign.setUpdateTime(LocalDateTime.now());
        this.signMapper.insertSelective(sign);
    }

    public Sign findById(Integer id) {
        return this.signMapper.selectByPrimaryKey(id);
    }

    public int update(Sign sign) throws SignNotFoundException, UserSignCorrespondException, UserNotFoundException {
        List<Sign> signs = this.querySelective(sign);
        if (signs.size() == 0) {
            throw new SignNotFoundException("该签约不存在，请检查id");
        } else {
            sign.setUpdateTime(LocalDateTime.now());
            return this.signMapper.updateByPrimaryKeySelective(sign);
        }
    }

    public void update(Integer id, Integer signUserId, String signUserSchoolName, Integer signedUserId, String signedUserSchoolName, Integer status) throws SignNotFoundException, UserSignCorrespondException, UserNotFoundException {
        Sign sign = new Sign();
        sign.setId(id);
        sign.setSignUserId(signUserId);
        sign.setSignSchoolName(signUserSchoolName);
        sign.setSignedUserId(signedUserId);
        sign.setSignedSchoolName(signedUserSchoolName);
        sign.setStatus(status);
        this.update(sign);
    }

    public void deleteById(Integer id) {
        this.signMapper.deleteByPrimaryKey(id);
    }

    public void delete(Sign sign) throws SignNotFoundException, UserSignCorrespondException, UserNotFoundException {
        deleteById(sign.getId());
//        sign.setDeleted(true);
//        this.update(sign);
    }

    public Workbook exportSignForm(List<Sign> signs) {
//        List<Sign> signs = this.querySelective((Integer) null, (Integer) null, (String) null, (Integer) null, (String) null, (Integer) null, (Integer) null, (Integer) null, (String) null, (String) null);
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        this.prepare(sheet);
        for (int i = 0; i < signs.size(); ++i) {
            Sign sign = (Sign) signs.get(i);
            Row row = sheet.createRow(i + 2);
            Cell cell0 = row.createCell(0);
            cell0.setCellValue(sign.getSignSchoolName());
            Cell cell1 = row.createCell(1);
            cell1.setCellValue(sign.getSignedSchoolName());
            Cell cell2 = row.createCell(2);
            LocalDateTime signTime = sign.getUpdateTime();
            int year = signTime.getYear();
            int month = signTime.getMonth().getValue();
            int dayOfMonth = signTime.getDayOfMonth();
            String time = year + "/" + month + "/" + dayOfMonth;
            cell2.setCellValue(time);
        }

        return workbook;
    }

    private void prepare(Sheet sheet) {
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("签约名单");
        Row row1 = sheet.createRow(1);
        Cell cell1 = row1.createCell(0);
        cell1.setCellValue("学校名称");
        Cell cell2 = row1.createCell(1);
        cell2.setCellValue("签约高校");
        Cell cell3 = row1.createCell(2);
        cell3.setCellValue("签约时间");
    }

    public Integer count(Integer likeUserId, String signSchoolName, String signedSchoolName, Integer year) {
        SignExample signExample = new SignExample();
        Criteria criteria = signExample.createCriteria();
        if (Objects.nonNull(likeUserId)) {
            criteria.andSignUserIdEqualTo(likeUserId);
        }
        if (StringUtils.hasText(signedSchoolName)) {
            criteria.andSignedSchoolNameLike("%" + signedSchoolName + "%");
        }

        if (StringUtils.hasText(signSchoolName)) {
            criteria.andSignSchoolNameLike("%" + signSchoolName + "%");
        }

        if (!StringUtils.isEmpty(year)) {
            LocalDateTime end = LocalDateTime.of(year, 12, 31, 23, 59);
            LocalDateTime start = LocalDateTime.of(year, 1, 1, 0, 0);
            criteria.andUpdateTimeBetween(start, end);
        }
        criteria.andDeletedEqualTo(false);
        long l = this.signMapper.countByExample(signExample);
        return Math.toIntExact(l);
    }

    public Integer countByLikedUserId(Integer likedUserId, String signSchoolName, String signedSchoolName, Integer year) {
        SignExample signExample = new SignExample();
        Criteria criteria = signExample.createCriteria();
        if (Objects.nonNull(likedUserId)) {
            criteria.andSignedUserIdEqualTo(likedUserId);
        }
        if (StringUtils.hasText(signedSchoolName)) {
            criteria.andSignedSchoolNameLike("%" + signedSchoolName + "%");
        }

        if (StringUtils.hasText(signSchoolName)) {
            criteria.andSignSchoolNameLike("%" + signSchoolName + "%");
        }

        if (!StringUtils.isEmpty(year)) {
            LocalDateTime end = LocalDateTime.of(year, 12, 31, 23, 59);
            LocalDateTime start = LocalDateTime.of(year, 1, 1, 0, 0);
            criteria.andUpdateTimeBetween(start, end);
        }
        criteria.andDeletedEqualTo(false);
        long l = this.signMapper.countByExample(signExample);
        return Math.toIntExact(l);
    }

    public void updateSchoolName(Integer userId, String schoolName) {
        Sign sign = new Sign();
        sign.setSignUserId(userId);
        sign.setSignSchoolName(schoolName);
        SignExample signExample = new SignExample();
        SignExample.Criteria criteria = signExample.createCriteria();
        criteria.andSignUserIdEqualTo(userId);
        signMapper.updateByExampleSelective(sign, signExample);

        sign = new Sign();
        sign.setSignedUserId(userId);
        sign.setSignedSchoolName(schoolName);
        signExample = new SignExample();
        criteria = signExample.createCriteria();
        criteria.andSignedUserIdEqualTo(userId);
        signMapper.updateByExampleSelective(sign, signExample);
    }

    public void deleteByUser(Integer userId) {
//        Sign sign = new Sign();
//        sign.setSignUserId(userId);
//        sign.setDeleted(true);
        SignExample signExample = new SignExample();
        SignExample.Criteria criteria = signExample.createCriteria();
        criteria.andSignUserIdEqualTo(userId);
//        signMapper.updateByExampleSelective(sign, signExample);
        signMapper.deleteByExample(signExample);

//        sign = new Sign();
//        sign.setSignedUserId(userId);
//        sign.setDeleted(true);
        signExample = new SignExample();
        criteria = signExample.createCriteria();
        criteria.andSignedUserIdEqualTo(userId);
//        signMapper.updateByExampleSelective(sign, signExample);
        signMapper.deleteByExample(signExample);
    }

    //APPENDIX-------------------------------
    public List<SignWithUser> querySelective(Integer year, String schoolName, Integer page, Integer pageSize, String sort, String order) {
        List<SignWithUser> signWithUsers = signWithUserMapper.querySelective(year, schoolName, page, pageSize, sort, order);
        return signWithUsers;
    }

    public Integer count(Integer year, String schoolName) {
        return signWithUserMapper.count(year, schoolName);
    }

    public Sign queryById(Integer signId) {
        SignExample signExample = new SignExample();
        SignExample.Criteria criteria = signExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andIdEqualTo(signId);
        List<Sign> signs = signMapper.selectByExampleSelective(signExample);
        return signs.size() == 0 ? null : signs.get(0);
    }

    public int updateByIdSelective(Sign sign) {
        sign.setUpdateTime(LocalDateTime.now());
        SignExample signExample = new SignExample();
        return signMapper.updateByPrimaryKeySelective(sign);
    }

    public List<Sign> querySelective(Sign.Column... columns) {
        SignExample signExample = new SignExample();
        SignExample.Criteria criteria = signExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        List<Sign> signs = this.signMapper.selectByExampleSelective(signExample, columns);
        PageInfo<Sign> signPageInfo = new PageInfo(signs);
        return signPageInfo.getList();
    }

    public List<Sign> queryBySignUserId(Integer likeUserId,Column...columns) {
        SignExample signExample = new SignExample();
        SignExample.Criteria criteria = signExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andSignUserIdEqualTo(likeUserId);
        return signMapper.selectByExampleSelective(signExample,columns);
    }

    public List<Sign> queryBySignedUserId(Integer likedUserId,Column...columns) {
        SignExample signExample = new SignExample();
        SignExample.Criteria criteria = signExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andSignedUserIdEqualTo(likedUserId);
        return signMapper.selectByExampleSelective(signExample,columns);
    }



    public List<Sign> queryBySignUserAndSignedUserId(Integer likeUserId, Integer likedUserId,Column...columns) {
        SignExample signExample = new SignExample();
        SignExample.Criteria criteria = signExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andSignedUserIdEqualTo(likedUserId);
        criteria.andSignUserIdEqualTo(likeUserId);
        return signMapper.selectByExampleSelective(signExample,columns);
    }

}
