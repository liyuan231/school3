//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.school.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.school.dao.LikeWithUserMapper;
import com.school.dao.LikesMapper;
import com.school.dto.AdvancedLikes;
import com.school.dto.FullLikes;
import com.school.dto.LikeWithUser;
import com.school.dto.SimplePage;
import com.school.exception.*;
import com.school.model.*;
import com.school.model.Likes.Column;
import com.school.model.LikesExample.Criteria;
import com.school.utils.FileEnum;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class LikeServiceImpl {
    @Resource
    private LikesMapper likesMapper;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserToRoleServiceImpl userToRoleService;
    @Resource
    private LikeWithUserMapper likeWithUserMapper;

    @Autowired
    private SignServiceImpl signService;

    @Autowired
    PicsServiceImpl picsService;

    public LikeServiceImpl() {
    }

    public PageInfo<Likes> querySelective(Integer id, Integer likeUserId, String likeSchoolName, Integer likedUserId, String likedSchoolName, Integer page, Integer pageSize, String sort, String order, Boolean distinctByLikeSchoolName, Integer year) {
        LikesExample likeExample = new LikesExample();
        Criteria criteria = likeExample.createCriteria();
        if (!StringUtils.isEmpty(id)) {
            criteria.andIdEqualTo(id);
        }

        if (!StringUtils.isEmpty(likeUserId)) {
            criteria.andLikeUserIdEqualTo(likeUserId);
        }

        if (StringUtils.hasText(likeSchoolName)) {
            criteria.andLikeSchoolNameLike("%" + likeSchoolName + "%");
        }

        if (!StringUtils.isEmpty(likedUserId)) {
            criteria.andLikedUserIdEqualTo(likedUserId);
        }

        if (StringUtils.hasText(likedSchoolName)) {
            criteria.andLikedSchoolNameLike("%" + likedSchoolName + "%");
        }

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            likeExample.setOrderByClause(sort + " " + order);
        }

        if (!StringUtils.isEmpty(year)) {
            LocalDateTime end = LocalDateTime.of(year, 12, 31, 23, 59);
            LocalDateTime start = LocalDateTime.of(year, 1, 1, 0, 0);
            criteria.andUpdateTimeBetween(start, end);
        }

        if (page != null || pageSize != null) {
            if (page == null) {
                PageHelper.startPage(1, pageSize);
            } else if (pageSize == null) {
                PageHelper.startPage(page, 10);
            } else {
                PageHelper.startPage(page, pageSize);
            }
        }
        List<Likes> likes = null;
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(distinctByLikeSchoolName) && distinctByLikeSchoolName) {
            likeExample.setDistinct(true);
            likes = this.likesMapper.selectByExampleSelective(likeExample, new Column[]{Column.likeUserId, Column.likeSchoolName});
        } else {
            likes = this.likesMapper.selectByExampleSelective(likeExample, new Column[0]);
        }
        PageInfo<Likes> pageInfo = new PageInfo(likes);
        return pageInfo;
    }


    public PageInfo<Likes> querySelective(Integer id, Integer likeUserId, String likeSchoolName, Integer likedUserId, String likedSchoolName, Integer page, Integer pageSize, String sort, String order, Boolean distinctByLikeSchoolName) {
        return querySelective(id, likeUserId, likeSchoolName, likedUserId, likedSchoolName, page, pageSize, sort, order, distinctByLikeSchoolName, null);
    }

    public void add(Likes like) throws UserNotFoundException, LikesAlreadyExistException, UserNotCorrectException, UserLikesNotCorrespondException, LikesNotFoundException {
        Integer likeUserId = like.getLikeUserId();
        Integer likedUserId = like.getLikedUserId();
        User byId = this.userService.findById(like.getLikedUserId());
        if (byId == null) {
            throw new UserNotFoundException("用户不存在！");
        } else {
            List<Likes> likes1 = this.querySelective((Integer) null, likeUserId, null, likedUserId, null, (Integer) null, (Integer) null, (String) null, (String) null, (Boolean) null, null).getList();
            if (likes1.size() >= 1) {
                update(likes1.get(0).getId(), likeUserId, likedUserId, like.getLikeSchoolName(), like.getLikedSchoolName(), null);
                throw new LikesAlreadyExistException("已经和该用户表明过意向了!");
            } else if (likeUserId.equals(likedUserId)) {
                throw new UserNotCorrectException("不能自己对自己有意向！");
            } else {
                like.setAddTime(LocalDateTime.now());
                like.setUpdateTime(LocalDateTime.now());
                this.likesMapper.insertSelective(like);
            }
        }
    }

    public Likes update(Integer id, Integer likeUserId, Integer likedUserId, String likeSchoolName, String likedSchoolName, Boolean deleted) throws UserLikesNotCorrespondException, LikesNotFoundException {
        Likes likes = new Likes();
        likes.setId(id);
        likes.setLikeUserId(likeUserId);
        likes.setLikeSchoolName(likeSchoolName);
        likes.setLikedUserId(likedUserId);
        likes.setLikedSchoolName(likedSchoolName);
        likes.setDeleted(deleted);
        return this.update(likes);
    }

    //这是根据意向id更新每一则意向
    public Likes update(Likes like) throws LikesNotFoundException {
        List<Likes> likes = this.querySelective(like.getId(), (Integer) null, (String) null, (Integer) null, (String) null, (Integer) null, (Integer) null, (String) null, (String) null, (Boolean) null, null).getList();
        if (likes.size() == 0) {
            throw new LikesNotFoundException("该则意向不存在，请检查id");
        } else {
            Likes likeInDb = likes.get(0);
            like.setId(likeInDb.getId());
            like.setUpdateTime(LocalDateTime.now());
            this.likesMapper.updateByPrimaryKeySelective(like);
            List<Likes> likesInDb = this.querySelective(like.getId(), (Integer) null, (String) null, (Integer) null, (String) null, (Integer) null, (Integer) null, (String) null, (String) null, (Boolean) null, null).getList();
            return likesInDb.size() == 0 ? null : (Likes) likesInDb.get(0);
        }
    }

    public void delete(Likes like) throws LikesNotFoundException {
        likesMapper.deleteByPrimaryKey(like.getId());
//        like.setDeleted(true);
//        this.update(like);
    }

    public List<Likes> match() {
        LikesExample likesExample = new LikesExample();
        Criteria criteria = likesExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        List<Likes> likes = this.likesMapper.selectByExample(likesExample);
        Map<Integer, Integer> map = new HashMap();
        List<Likes> result = new LinkedList();
        Iterator var6 = likes.iterator();

        while (var6.hasNext()) {
            Likes like = (Likes) var6.next();
            Integer integer = (Integer) map.get(like.getLikeUserId());
            if (integer != null) {
                result.add(like);
            } else {
                map.put(like.getLikedUserId(), like.getLikeUserId());
            }
        }

        return result;
    }

    public void add(Integer likeUserId, String likeSchoolName, Integer likedUserId, String likedSchoolName) throws UserNotFoundException, UserNotCorrectException, LikesAlreadyExistException, UserLikesNotCorrespondException, LikesNotFoundException {
        Likes likes = new Likes();
        likes.setLikedUserId(likedUserId);
        likes.setLikeUserId(likeUserId);
        likes.setLikeSchoolName(likeSchoolName);
        likes.setLikedSchoolName(likedSchoolName);
        this.add(likes);
    }

    public void deleteById(Integer id) throws LikesNotFoundException {
        this.likesMapper.deleteByPrimaryKey(id);
    }

    public List<Likes> matchByLikedUserId() {
        User user = this.userService.retrieveUserByToken();
        LikesExample likesExample = new LikesExample();
        Criteria criteria = likesExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andLikedUserIdEqualTo(user.getId());
        return this.likesMapper.selectByExample(likesExample);
    }

    public List<Likes> matchByLikeUserId() {
        User user = this.userService.retrieveUserByToken();
        LikesExample likesExample = new LikesExample();
        Criteria criteria = likesExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andLikeUserIdEqualTo(user.getId());
        return this.likesMapper.selectByExample(likesExample);
    }

    public List<Likes> matchByUserId() {
        User user = this.userService.retrieveUserByToken();
        Integer userId = user.getId();
        List<Likes> result = new LinkedList();
        List<Likes> matches = this.match();
        Iterator var5 = matches.iterator();

        while (var5.hasNext()) {
            Likes next = (Likes) var5.next();
            if (next.getLikeUserId().equals(userId)) {
                result.add(next);
            }
        }

        return result;
    }

    public List<AdvancedLikes> retrieveAdvancedLikes(List<Likes> theLikes) {
        List<AdvancedLikes> advancedLikes = new LinkedList<>();
        for (Likes like : theLikes) {
            List<Likes> eachLikes = querySelective(null, like.getLikeUserId(), null, null, null, null, null, null, null, null, null).getList();
            List<Sign> signs = signService.querySelective(null, like.getLikeUserId(), null, null, null, null, null, null, null, null);
            List<Sign> signs1 = signService.querySelective(null, null, null, like.getLikeUserId(), null, null, null, null, null, null);
            signs.addAll(signs1);
            Set<String> theUserThatIHaveSign = new HashSet<>();
            for (Sign sign : signs) {
                if (like.getLikeUserId().equals(sign.getSignedUserId())) {
                    theUserThatIHaveSign.add(String.valueOf(sign.getSignUserId()));
                } else {
                    theUserThatIHaveSign.add(String.valueOf(sign.getSignedUserId()));
                }
            }
            List<FullLikes> theUserThatLikesMeOrSigned = new LinkedList<>();
            List<Likes> theUserThatLikesMe = querySelective((Integer) null, (Integer) null, null, like.getLikeUserId(), null, null, null, null, null, null, null).getList();
            for (Likes likes1 : theUserThatLikesMe) {
                FullLikes fullLikes = new FullLikes();
                fullLikes.setLikes(likes1);
                //TODO contains中比价的为对象，不为值，若想比较某一值，需另想,初步想法是模仿String重写hashCode以及equals
                //此时想到了另一种解法，因为String已经重写过这两个方法了，因此我们可以绕过这个问题
                if ((likes1.getLikeUserId().equals(like.getLikeUserId()))) {
                    if (theUserThatIHaveSign.contains(String.valueOf(likes1.getLikedUserId()))) {
                        fullLikes.setSigned(true);
                    }
                }
                if ((likes1.getLikedUserId().equals(like.getLikeUserId()))) {
                    if (theUserThatIHaveSign.contains(String.valueOf(likes1.getLikeUserId()))) {
                        fullLikes.setSigned(true);
                    }
                }
                theUserThatLikesMeOrSigned.add(fullLikes);
            }
            List<Pics> logos = picsService.querySelective(null, like.getLikeUserId(), FileEnum.LOGO.value());
            List<Pics> signatures = picsService.querySelective(null, like.getLikeUserId(), FileEnum.SIGNATURE.value());
            AdvancedLikes advancedLike = new AdvancedLikes(like.getLikeUserId(), like.getLikeSchoolName(), signs, eachLikes, theUserThatLikesMeOrSigned, logos.size() > 0, signatures.size() > 0);
            advancedLikes.add(advancedLike);
        }
        return advancedLikes;
    }


    public Workbook exportLikesForm(List<Likes> likes) {
//        List<Likes> likes = this.querySelective((Integer) null, (Integer) null, (String) null, (Integer) null, (String) null, (Integer) null, (Integer) null, (String) null, (String) null, (Boolean) null);
//        Map<String, List<String>> map = new TreeMap();
        List<AdvancedLikes> advancedLikes = retrieveAdvancedLikes(likes);
//        Iterator var3 = advancedLikes.iterator();

        Workbook workbook = new HSSFWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("IMPACT");
        font.setBold(true);
        cellStyle.setFont(font);
        Sheet sheet = workbook.createSheet();
        this.prepare(cellStyle, sheet);
//        int i = 0;

        for (int i = 0; i < advancedLikes.size(); i++) {
            Row row = sheet.createRow(i + 2);
            Cell cell = row.createCell(0);
            cell.setCellValue(advancedLikes.get(i).getSchoolName());

            cell = row.createCell(1);
            StringBuilder stringBuilder = new StringBuilder();
            List<Likes> likesList = advancedLikes.get(i).getLikesList();
            for (Likes likes1 : likesList) {
                if (advancedLikes.get(i).getUserId().equals(likes1.getLikeUserId())) {
                    stringBuilder.append(likes1.getLikedSchoolName()).append(",");
                } else {
                    stringBuilder.append(likes1.getLikeSchoolName()).append(",");
                }
            }
            deleteLastCharacter(stringBuilder);
            cell.setCellValue(stringBuilder.toString());

            cell = row.createCell(2);
            List<Sign> signList = advancedLikes.get(i).getSignList();
            stringBuilder = new StringBuilder();
            for (Sign sign : signList) {
                if (advancedLikes.get(i).getUserId().equals(sign.getSignUserId())) {
                    stringBuilder.append(sign.getSignedSchoolName()).append(",");
                } else {
                    stringBuilder.append(sign.getSignSchoolName()).append(",");
                }
            }
            deleteLastCharacter(stringBuilder);
            cell.setCellValue(stringBuilder.toString());

            cell = row.createCell(3);
            List<FullLikes> fullLikes = advancedLikes.get(i).getFullLikes();
            stringBuilder = new StringBuilder();
            for (FullLikes fullLike : fullLikes) {
                Likes likes1 = fullLike.getLikes();
                if (fullLike.isSigned()) {
                    if (fullLike.getLikes().getLikeUserId().equals(advancedLikes.get(i).getUserId())) {
                        stringBuilder.append(likes1.getLikedSchoolName()).append("(已接受 ").append(likes1.getUpdateTime()).append(")").append(",");
                    } else {
                        stringBuilder.append(likes1.getLikeSchoolName()).append("(已接受 ").append(likes1.getUpdateTime()).append(")").append(",");
                    }
                } else {
                    if (fullLike.getLikes().getLikeUserId().equals(advancedLikes.get(i).getUserId())) {
                        stringBuilder.append(likes1.getLikedSchoolName()).append("(未接受)").append(",");
                    } else {
                        stringBuilder.append(likes1.getLikeSchoolName()).append("(未接受)").append(",");
                    }

                }
            }
            deleteLastCharacter(stringBuilder);
            cell.setCellValue(stringBuilder.toString());

            cell = row.createCell(4);
            if (advancedLikes.get(i).isHasLogo()) {
                cell.setCellValue("已上传");
            } else {
                cell.setCellValue("未上传");
            }
        }
        return workbook;
    }

    private void deleteLastCharacter(StringBuilder stringBuilder) {
        if (stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
    }

    public void prepare(CellStyle cellStyle, Sheet sheet) {
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("签约意向表");
        row = sheet.createRow(1);

        Cell cell = row.createCell(0);
        cell.setCellValue("学校名称");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(1);
        cell.setCellValue("签约意向");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(2);
        cell.setCellValue("签约匹配高校");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(3);
        cell.setCellValue("被邀约情况");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(4);
        cell.setCellValue("学校logo");
        cell.setCellStyle(cellStyle);
    }

    public void like(Integer likedUserId) throws UserNotFoundException, UserNotCorrectException, LikesAlreadyExistException, UserLikesNotCorrespondException, LikesNotFoundException {
        //当前用户的信息
        User likeUser = userService.retrieveUserByToken();
        User likedUser = userService.findById(likedUserId);
        add(likeUser.getId(), likeUser.getSchoolName(), likedUserId, likedUser.getSchoolName());
    }

    public int count(Integer userId, String schoolName) {
        LikesExample likesExample = new LikesExample();
        Criteria criteria = likesExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(userId)) {
            criteria.andLikeUserIdEqualTo(userId);
        }
        if (StringUtils.hasText(schoolName)) {
            criteria.andLikeSchoolNameLike("%" + schoolName + "%");
        }
        return (int) likesMapper.countByExample(likesExample);
    }

    public int count(Integer userId, String schoolName, Integer year) {
        return count(userId, schoolName, year, null);

    }

    public int count(Integer userId, String schoolName, Integer year, Boolean distinctByLikeSchoolName) {
        LikesExample likesExample = new LikesExample();
        Criteria criteria = likesExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(userId)) {
            criteria.andLikeUserIdEqualTo(userId);
        }
        if (StringUtils.hasText(schoolName)) {
            criteria.andLikeSchoolNameLike("%" + schoolName + "%");
        }
        if (!StringUtils.isEmpty(year)) {
            LocalDateTime end = LocalDateTime.of(year, 12, 31, 23, 59);
            LocalDateTime start = LocalDateTime.of(year, 1, 1, 0, 0);
            criteria.andUpdateTimeBetween(start, end);
        }
        int size = -1;
        if (!StringUtils.isEmpty(distinctByLikeSchoolName) && distinctByLikeSchoolName) {
            likesExample.setDistinct(true);
//            likesMapper.countByExample(likesExample);
            size = this.likesMapper.selectByExampleSelective(likesExample, new Column[]{Column.likeUserId, Column.likeSchoolName}).size();
        } else {
            size = this.likesMapper.selectByExampleSelective(likesExample, new Column[0]).size();
        }
        return size;
    }


    public Workbook exportLikesFormSingle(List<Likes> likesList) {
        Workbook workbook = new HSSFWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("IMPACT");
        font.setBold(true);
        cellStyle.setFont(font);
        Sheet sheet = workbook.createSheet();
        this.prepareSingle(cellStyle, sheet);
        int k = 0;
        for (Likes likes : likesList) {
            Row row = sheet.createRow(k + 2);
            Cell cell = row.createCell(0);
            cell.setCellValue(likes.getLikeSchoolName());

            cell = row.createCell(1);
            cell.setCellValue(likes.getLikedSchoolName());

            cell = row.createCell(2);
            cell.setCellValue(likes.getAddTime().toString());
            k++;
        }
        return workbook;

    }

    private void prepareSingle(CellStyle cellStyle, Sheet sheet) {
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("单项意向表");
        row = sheet.createRow(1);

        Cell cell = row.createCell(0);
        cell.setCellValue("学校名称");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(1);
        cell.setCellValue("意向高校");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(2);
        cell.setCellValue("提出时间");
        cell.setCellStyle(cellStyle);
    }

    public SimplePage queryDistinct(Integer id, Integer likeUserId, String likeSchoolName, Integer likedUserId, String likedSchoolName, Integer page, Integer pageSize, String sort, String order, Boolean distinctByLikeSchoolName, Integer year) {
        Integer size = null;
        LikesExample likeExample = new LikesExample();
        Criteria criteria = likeExample.createCriteria();
        if (!StringUtils.isEmpty(id)) {
            criteria.andIdEqualTo(id);
        }

        if (!StringUtils.isEmpty(likeUserId)) {
            criteria.andLikeUserIdEqualTo(likeUserId);
        }

        if (StringUtils.hasText(likeSchoolName)) {
            criteria.andLikeSchoolNameLike("%" + likeSchoolName + "%");
        }

        if (!StringUtils.isEmpty(likedUserId)) {
            criteria.andLikedUserIdEqualTo(likedUserId);
        }

        if (StringUtils.hasText(likedSchoolName)) {
            criteria.andLikedSchoolNameLike("%" + likedSchoolName + "%");
        }

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            likeExample.setOrderByClause(sort + " " + order);
        }

        if (!StringUtils.isEmpty(year)) {
            LocalDateTime end = LocalDateTime.of(year, 12, 31, 23, 59);
            LocalDateTime start = LocalDateTime.of(year, 1, 1, 0, 0);
            criteria.andUpdateTimeBetween(start, end);
        }

        if (page != null || pageSize != null) {
            if (page == null) {
                PageHelper.startPage(1, pageSize);
            } else if (pageSize == null) {
                PageHelper.startPage(page, 10);
            } else {
                PageHelper.startPage(page, pageSize);
            }
        }
        List<Likes> likes = null;
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(distinctByLikeSchoolName) && distinctByLikeSchoolName) {
            likeExample.setDistinct(true);
            likes = this.likesMapper.selectByExampleSelective(likeExample, new Column[]{Column.likeUserId, Column.likeSchoolName});
        } else {
            likes = this.likesMapper.selectByExampleSelective(likeExample, new Column[0]);
        }
        PageInfo<Likes> pageInfo = new PageInfo(likes);
        size = likes.size();
        return new SimplePage(size, pageInfo.getList());
    }

    public void updateSchoolName(Integer userId, String schoolName) {
        Likes likes = new Likes();
        likes.setLikeUserId(userId);
        likes.setLikeSchoolName(schoolName);
        LikesExample likesExample = new LikesExample();
        Criteria criteria = likesExample.createCriteria();
        criteria.andLikeUserIdEqualTo(userId);
        likesMapper.updateByExampleSelective(likes, likesExample);


        likes = new Likes();
        likes.setLikedUserId(userId);
        likes.setLikedSchoolName(schoolName);
        likesExample = new LikesExample();
        criteria = likesExample.createCriteria();
        criteria.andLikedUserIdEqualTo(userId);
        likesMapper.updateByExampleSelective(likes, likesExample);
    }

    public void deleteByUser(Integer userId) {
//        Likes likes = new Likes();
//        likes.setDeleted(true);
//        likes.setLikeUserId(userId);
        LikesExample likesExample = new LikesExample();
        Criteria criteria = likesExample.createCriteria();
        criteria.andLikeUserIdEqualTo(userId);
//        likesMapper.updateByExampleSelective(likes, likesExample);
        likesMapper.deleteByExample(likesExample);

//        likes = new Likes();
//        likes.setDeleted(true);
//        likes.setLikedUserId(userId);
        likesExample = new LikesExample();
        criteria = likesExample.createCriteria();
        criteria.andLikedUserIdEqualTo(userId);
//        likesMapper.updateByExampleSelective(likes, likesExample);
        likesMapper.deleteByExample(likesExample);

    }

    //----------------------------APPEND--------------------------------
    public PageInfo<LikeWithUser> querySelective(Integer year, String likeSchoolName, Integer page, Integer pageSize, String sort, String order, Likes.Column... columns) {
//        if (page != null && pageSize != null) {
//            PageHelper.startPage(page, pageSize);
//        }
        List<LikeWithUser> likeWithUsers = likeWithUserMapper.querySelective(year, likeSchoolName, page, pageSize, sort, order);
        return new PageInfo<>(likeWithUsers);
    }

    public List<LikeWithUser> querySelectiveDistinct(Integer year, String likeSchoolName, Integer page, Integer pageSize, String sort, String order) {
        return likeWithUserMapper.querySelectiveDistinct(year, likeSchoolName, page, pageSize, sort, order);
    }

    public int countDistinct(Integer year, String likeSchoolName) {
        return likeWithUserMapper.countDistinct(year, likeSchoolName);
    }

    public int countByYearAndLikeSchoolName(Integer year, String likeSchoolName) {
        return likeWithUserMapper.count(year, likeSchoolName);

    }
}
