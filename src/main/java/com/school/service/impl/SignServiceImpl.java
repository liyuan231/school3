//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.school.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.school.dao.SignMapper;
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

@Service
@Transactional
public class SignServiceImpl {
    @Resource
    private SignMapper signMapper;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserToRoleServiceImpl userToRoleService;

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
                this.add(signUser.getId(), signUser.getSchoolname(), signedUserId, signedUser.getSchoolname());
            } else {
                throw new SignAlreadyExistException("已经和该用户签约过了！！");
            }
        }
    }

    public void add(Integer signUserId, String signUserSchoolName, Integer signedUserId, String signedUserSchoolName) {
        Sign sign = new Sign();
        sign.setSignuserid(signUserId);
        sign.setSignschoolname(signUserSchoolName);
        sign.setSigneduserid(signedUserId);
        sign.setSignedschoolname(signedUserSchoolName);
        this.add(sign);
    }

    private List<Sign> querySelective(Sign sign) {
        return this.querySelective(sign.getId(), sign.getSignuserid(), sign.getSignschoolname(), sign.getSigneduserid(), sign.getSignedschoolname(), (Integer) null, (Integer) null, (Integer) null, (String) null, (String) null);
    }

    public List<Sign> querySelective(Integer id, Integer signUserId, String signSchoolName, Integer signedUserId, String signedSchoolName, Integer year, Integer page, Integer limit, String sort, String order) {
        SignExample signExample = new SignExample();
        Criteria criteria = signExample.createCriteria();
        if (!StringUtils.isEmpty(id)) {
            criteria.andIdEqualTo(id);
        }

        if (!StringUtils.isEmpty(signUserId)) {
            criteria.andSignuseridEqualTo(signUserId);
        }

        if (StringUtils.hasText(signSchoolName)) {
            criteria.andSignschoolnameLike("%" + signSchoolName + "%");
        }

        if (!StringUtils.isEmpty(signedUserId)) {
            criteria.andSigneduseridEqualTo(signedUserId);
        }

        if (StringUtils.hasText(signedSchoolName)) {
            criteria.andSignedschoolnameLike("%" + signedSchoolName + "%");
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
        sign.setSignuserid(signUserId);
        sign.setSignschoolname(signUserSchoolName);
        sign.setSigneduserid(signedUserId);
        sign.setSignedschoolname(signedUserSchoolName);
        sign.setStatus(status);
        this.update(sign);
    }

    public void deleteById(Integer id) throws SignNotFoundException, UserSignCorrespondException, UserNotFoundException {
        Sign sign = new Sign();
        sign.setId(id);
        delete(sign);
    }

    public void delete(Sign sign) throws SignNotFoundException, UserSignCorrespondException, UserNotFoundException {
        sign.setDeleted(true);
        this.update(sign);
    }

    public Workbook exportSignForm() {
        List<Sign> signs = this.querySelective((Integer) null, (Integer) null, (String) null, (Integer) null, (String) null, (Integer) null, (Integer) null, (Integer) null, (String) null, (String) null);
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        this.prepare(sheet);

        for (int i = 0; i < signs.size(); ++i) {
            Sign sign = (Sign) signs.get(i);
            Row row = sheet.createRow(i + 2);
            Cell cell0 = row.createCell(0);
            cell0.setCellValue(sign.getSignschoolname());
            Cell cell1 = row.createCell(1);
            cell1.setCellValue(sign.getSignedschoolname());
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

    public Integer count(String signSchoolName, String signedSchoolName, Integer year) {
        SignExample signExample = new SignExample();
        Criteria criteria = signExample.createCriteria();
        if (StringUtils.hasText(signedSchoolName)) {
            criteria.andSignedschoolnameLike("%" + signedSchoolName + "%");
        }

        if (StringUtils.hasText(signSchoolName)) {
            criteria.andSignschoolnameLike("%" + signSchoolName + "%");
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
}
