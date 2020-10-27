//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.school.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.school.dao.LikesMapper;
import com.school.dto.AdvancedLikes;
import com.school.dto.FullLikes;
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

    @Autowired
    private SignServiceImpl signService;

    @Autowired
    PicsServiceImpl picsService;

    public LikeServiceImpl() {
    }

    public List<Likes> querySelective(Integer id, Integer likeUserId, String likeSchoolName, Integer likedUserId, String likedSchoolName, Integer page, Integer pageSize, String sort, String order, Boolean distinctByLikeSchoolName, Integer year) {
        LikesExample likeExample = new LikesExample();
        Criteria criteria = likeExample.createCriteria();
        if (!StringUtils.isEmpty(id)) {
            criteria.andIdEqualTo(id);
        }

        if (!StringUtils.isEmpty(likeUserId)) {
            criteria.andLikeuseridEqualTo(likeUserId);
        }

        if (StringUtils.hasText(likeSchoolName)) {
            criteria.andLikeschoolnameLike("%" + likeSchoolName + "%");
        }

        if (!StringUtils.isEmpty(likedUserId)) {
            criteria.andLikeduseridEqualTo(likedUserId);
        }

        if (StringUtils.hasText(likedSchoolName)) {
            criteria.andLikedschoolnameLike("%" + likedSchoolName + "%");
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
            likes = this.likesMapper.selectByExampleSelective(likeExample, new Column[]{Column.likeuserid, Column.likeschoolname});
        } else {
            likes = this.likesMapper.selectByExampleSelective(likeExample, new Column[0]);
        }
        PageInfo<Likes> pageInfo = new PageInfo(likes);
        return pageInfo.getList();
    }


    public List<Likes> querySelective(Integer id, Integer likeUserId, String likeSchoolName, Integer likedUserId, String likedSchoolName, Integer page, Integer pageSize, String sort, String order, Boolean distinctByLikeSchoolName) {
        return querySelective(id, likeUserId, likeSchoolName, likedUserId, likedSchoolName, page, pageSize, sort, order, distinctByLikeSchoolName, null);
    }

    public void add(Likes like) throws UserNotFoundException, LikesAlreadyExistException, UserNotCorrectException, UserLikesNotCorrespondException, LikesNotFoundException {
        Integer likeUserId = like.getLikeuserid();
        Integer likedUserId = like.getLikeduserid();
        User byId = this.userService.findById(like.getLikeduserid());
        if (byId == null) {
            throw new UserNotFoundException("用户不存在！");
        } else {
            List<Likes> likes1 = this.querySelective((Integer) null, likeUserId, null, likedUserId, null, (Integer) null, (Integer) null, (String) null, (String) null, (Boolean) null, null);
            if (likes1.size() >= 1) {
                update(likes1.get(0).getId(), likeUserId, likedUserId, like.getLikeschoolname(), like.getLikedschoolname(), null);
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
        likes.setLikeuserid(likeUserId);
        likes.setLikeschoolname(likeSchoolName);
        likes.setLikeduserid(likedUserId);
        likes.setLikedschoolname(likedSchoolName);
        likes.setDeleted(deleted);
        return this.update(likes);
    }

    //这是根据意向id更新每一则意向
    public Likes update(Likes like) throws LikesNotFoundException {
        List<Likes> likes = this.querySelective(like.getId(), (Integer) null, (String) null, (Integer) null, (String) null, (Integer) null, (Integer) null, (String) null, (String) null, (Boolean) null, null);
        if (likes.size() == 0) {
            throw new LikesNotFoundException("该则意向不存在，请检查id");
        } else {
            Likes likeInDb = likes.get(0);
            like.setId(likeInDb.getId());
            like.setUpdateTime(LocalDateTime.now());
            this.likesMapper.updateByPrimaryKeySelective(like);
            List<Likes> likesInDb = this.querySelective(like.getId(), (Integer) null, (String) null, (Integer) null, (String) null, (Integer) null, (Integer) null, (String) null, (String) null, (Boolean) null, null);
            return likesInDb.size() == 0 ? null : (Likes) likesInDb.get(0);
        }
    }

    public void delete(Likes like) throws LikesNotFoundException {
        like.setDeleted(true);
        this.update(like);
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
            Integer integer = (Integer) map.get(like.getLikeuserid());
            if (integer != null) {
                result.add(like);
            } else {
                map.put(like.getLikeduserid(), like.getLikeuserid());
            }
        }

        return result;
    }

    public void add(Integer likeUserId, String likeSchoolName, Integer likedUserId, String likedSchoolName) throws UserNotFoundException, UserNotCorrectException, LikesAlreadyExistException, UserLikesNotCorrespondException, LikesNotFoundException {
        Likes likes = new Likes();
        likes.setLikeduserid(likedUserId);
        likes.setLikeuserid(likeUserId);
        likes.setLikeschoolname(likeSchoolName);
        likes.setLikedschoolname(likedSchoolName);
        this.add(likes);
    }

    public void deleteById(Integer id) throws LikesNotFoundException {
        Likes likes = new Likes();
        likes.setId(id);
        this.delete(likes);
    }

    public List<Likes> matchByLikedUserId() {
        User user = this.userService.retrieveUserByToken();
        LikesExample likesExample = new LikesExample();
        Criteria criteria = likesExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andLikeduseridEqualTo(user.getId());
        return this.likesMapper.selectByExample(likesExample);
    }

    public List<Likes> matchByLikeUserId() {
        User user = this.userService.retrieveUserByToken();
        LikesExample likesExample = new LikesExample();
        Criteria criteria = likesExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andLikeuseridEqualTo(user.getId());
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
            if (next.getLikeuserid().equals(userId)) {
                result.add(next);
            }
        }

        return result;
    }

    public List<AdvancedLikes> retrieveAdvancedLikes(List<Likes> theLikes) {
        List<AdvancedLikes> advancedLikes = new LinkedList<>();
        for (Likes like : theLikes) {
            List<Likes> eachLikes = querySelective(null, like.getLikeuserid(), null, null, null, null, null, null, null, null, null);
            List<Sign> signs = signService.querySelective(null, like.getLikeuserid(), null, null, null, null, null, null, null, null);
            List<Sign> signs1 = signService.querySelective(null, null, null, like.getLikeuserid(), null, null, null, null, null, null);
            signs.addAll(signs1);
            List<Sign> finalSigns = new LinkedList<>();
            for (Sign sign : signs) {
//                Sign sign1 = new Sign();
//                if (sign.getSignuserid().equals(like.getLikeuserid())) {
//                    sign1.setSignuserid(sign.getSigneduserid());
//                    sign1.setSignschoolname(sign.getSignedschoolname());
//                    sign1.setSigneduserid(sign.getSignuserid());
//                    sign1.setSignedschoolname(sign.getSignschoolname());
//                } else {
//                    sign1.setSignuserid(sign.getSignuserid());
//                    sign1.setSignschoolname(sign.getSignschoolname());
//                    sign1.setSigneduserid(sign.getSigneduserid());
//                    sign1.setSignedschoolname(sign.getSignedschoolname());
//                }
                finalSigns.add(sign);
            }

            Set<String> theUserThatIHaveSign = new HashSet<>();
            for (Sign sign : signs) {
                if (like.getLikeuserid().equals(sign.getSigneduserid())) {
                    theUserThatIHaveSign.add(String.valueOf(sign.getSignuserid()));
                } else {
                    theUserThatIHaveSign.add(String.valueOf(sign.getSigneduserid()));
                }
            }
            List<FullLikes> theUserThatLikesMeOrSigned = new LinkedList<>();
            List<Likes> theUserThatLikesMe = querySelective((Integer) null, (Integer) null, null, like.getLikeuserid(), null, null, null, null, null, null, null);
            for (Likes likes1 : theUserThatLikesMe) {
                FullLikes fullLikes = new FullLikes();
                fullLikes.setLikes(likes1);
                //TODO contains中比价的为对象，不为值，若想比较某一值，需另想,初步想法是模仿String重写hashCode以及equals
                //此时想到了另一种解法，因为String已经重写过这两个方法了，因此我们可以绕过这个问题
                if ((likes1.getLikeuserid().equals(like.getLikeuserid()))) {
                    if (theUserThatIHaveSign.contains(String.valueOf(likes1.getLikeduserid()))) {
                        fullLikes.setSigned(true);
                    }
                }
                if ((likes1.getLikeuserid().equals(like.getLikeduserid()))) {
                    if (theUserThatIHaveSign.contains(String.valueOf(likes1.getLikeuserid()))) {
                        fullLikes.setSigned(true);
                    }
                }
                theUserThatLikesMeOrSigned.add(fullLikes);
            }
            List<Pics> logos = picsService.querySelective(null, like.getLikeuserid(), FileEnum.LOGO.value());
            List<Pics> signatures = picsService.querySelective(null, like.getLikeuserid(), FileEnum.SIGNATURE.value());
            AdvancedLikes advancedLike = new AdvancedLikes(like.getLikeuserid(), like.getLikeschoolname(), finalSigns, eachLikes, theUserThatLikesMeOrSigned, logos.size() > 0, signatures.size() > 0);
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
            cell.setCellValue(advancedLikes.get(i).getSchoolname());

            cell = row.createCell(1);
            StringBuilder stringBuilder = new StringBuilder();
            List<Likes> likesList = advancedLikes.get(i).getLikesList();
            for (Likes likes1 : likesList) {
                if (advancedLikes.get(i).getUserId().equals(likes1.getLikeuserid())) {
                    stringBuilder.append(likes1.getLikedschoolname()).append(",");
                } else {
                    stringBuilder.append(likes1.getLikeschoolname()).append(",");
                }
            }
            deleteLastCharacter(stringBuilder);
            cell.setCellValue(stringBuilder.toString());

            cell = row.createCell(2);
            List<Sign> signList = advancedLikes.get(i).getSignList();
            stringBuilder = new StringBuilder();
            for (Sign sign : signList) {
                if (advancedLikes.get(i).getUserId().equals(sign.getSignuserid())) {
                    stringBuilder.append(sign.getSignedschoolname()).append(",");
                } else {
                    stringBuilder.append(sign.getSignschoolname()).append(",");
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
                    if (fullLike.getLikes().getLikeuserid().equals(advancedLikes.get(i).getUserId())) {
                        stringBuilder.append(likes1.getLikedschoolname()).append("(已接受 ").append(likes1.getUpdateTime()).append(")").append(",");
                    } else {
                        stringBuilder.append(likes1.getLikeschoolname()).append("(已接受 ").append(likes1.getUpdateTime()).append(")").append(",");
                    }
                } else {
                    if (fullLike.getLikes().getLikeuserid().equals(advancedLikes.get(i).getUserId())) {
                        stringBuilder.append(likes1.getLikedschoolname()).append("(未接受)").append(",");
                    } else {
                        stringBuilder.append(likes1.getLikeschoolname()).append("(未接受)").append(",");
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
        add(likeUser.getId(), likeUser.getSchoolname(), likedUserId, likedUser.getSchoolname());
    }

    public int count(Integer userId, String schoolName) {
        LikesExample likesExample = new LikesExample();
        Criteria criteria = likesExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(userId)) {
            criteria.andLikeuseridEqualTo(userId);
        }
        if (StringUtils.hasText(schoolName)) {
            criteria.andLikeschoolnameLike("%" + schoolName + "%");
        }
        return (int) likesMapper.countByExample(likesExample);
    }

    public int count(Integer userId, String schoolName, Integer year) {
        return count(userId,schoolName,year,null);

    }
    public int count(Integer userId, String schoolName, Integer year, Boolean distinctByLikeSchoolName) {
        LikesExample likesExample = new LikesExample();
        Criteria criteria = likesExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(userId)) {
            criteria.andLikeuseridEqualTo(userId);
        }
        if (StringUtils.hasText(schoolName)) {
            criteria.andLikeschoolnameLike("%" + schoolName + "%");
        }
        if (!StringUtils.isEmpty(year)) {
            LocalDateTime end = LocalDateTime.of(year, 12, 31, 23, 59);
            LocalDateTime start = LocalDateTime.of(year, 1, 1, 0, 0);
            criteria.andUpdateTimeBetween(start, end);
        }
        int size=-1;
        if (!StringUtils.isEmpty(distinctByLikeSchoolName) && distinctByLikeSchoolName) {
            likesExample.setDistinct(true);
//            likesMapper.countByExample(likesExample);
            size = this.likesMapper.selectByExampleSelective(likesExample, new Column[]{Column.likeuserid, Column.likeschoolname}).size();
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
            cell.setCellValue(likes.getLikeschoolname());

            cell = row.createCell(1);
            cell.setCellValue(likes.getLikedschoolname());

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
            criteria.andLikeuseridEqualTo(likeUserId);
        }

        if (StringUtils.hasText(likeSchoolName)) {
            criteria.andLikeschoolnameLike("%" + likeSchoolName + "%");
        }

        if (!StringUtils.isEmpty(likedUserId)) {
            criteria.andLikeduseridEqualTo(likedUserId);
        }

        if (StringUtils.hasText(likedSchoolName)) {
            criteria.andLikedschoolnameLike("%" + likedSchoolName + "%");
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
            likes = this.likesMapper.selectByExampleSelective(likeExample, new Column[]{Column.likeuserid, Column.likeschoolname});
        } else {
            likes = this.likesMapper.selectByExampleSelective(likeExample, new Column[0]);
        }
        PageInfo<Likes> pageInfo = new PageInfo(likes);
        size = likes.size();
        return new SimplePage(size,pageInfo.getList());
    }
}
