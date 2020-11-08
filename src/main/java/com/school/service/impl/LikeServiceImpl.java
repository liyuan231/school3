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
import com.school.dto.LikeWithUser;
import com.school.dto.SimplePage;
import com.school.dto.StringLikesWithMark;
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
import java.time.format.DateTimeFormatter;
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

    public PageInfo<Likes> querySelective(Integer id, Integer likeUserId, String likeSchoolName, Integer likedUserId, String likedSchoolName, Integer page, Integer pageSize, String sort, String order, Boolean distinctByLikeSchoolName, Integer year, Column... columns) {
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
            likes = this.likesMapper.selectByExampleSelective(likeExample, columns);
        }
        PageInfo<Likes> pageInfo = new PageInfo(likes);
        return pageInfo;
    }

    public PageInfo<Likes> querySelective(Integer id, Integer likeUserId, String likeSchoolName, Integer likedUserId, String likedSchoolName, Integer page, Integer pageSize, String sort, String order, Boolean distinctByLikeSchoolName) {
        return querySelective(id, likeUserId, likeSchoolName, likedUserId, likedSchoolName, page, pageSize, sort, order, distinctByLikeSchoolName, null);
    }

    public void add(Likes like) throws UserNotFoundException, LikesAlreadyExistException, UserNotCorrectException, UserLikesNotCorrespondException, LikesNotFoundException {
//        Integer likeUserId = like.getLikeUserId();
//        Integer likedUserId = like.getLikedUserId();
//        User byId = this.userService.findById(like.getLikedUserId());
//        if (byId == null) {
//            throw new UserNotFoundException("用户不存在！");
//        } else {
//            List<Likes> likes1 = this.querySelective((Integer) null, likeUserId, null, likedUserId, null, (Integer) null, (Integer) null, (String) null, (String) null, (Boolean) null, null).getList();
//            if (likes1.size() >= 1) {
//                update(likes1.get(0).getId(), likeUserId, likedUserId, like.getLikeSchoolName(), like.getLikedSchoolName(), null);
//                throw new LikesAlreadyExistException("已经和该用户表明过意向了!");
//            } else if (likeUserId.equals(likedUserId)) {
//                throw new UserNotCorrectException("不能自己对自己有意向！");
//            } else {
        like.setAddTime(LocalDateTime.now());
        like.setUpdateTime(LocalDateTime.now());
        this.likesMapper.insertSelective(like);
//            }
//        }
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

    public List<Likes> matchByLikedUserId(User user) {

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

    public List<AdvancedLikes> retrieveAdvancedLikes(List<User> users) {
        List<AdvancedLikes> advancedLikesLinkedList = new LinkedList<>();
        for (User user : users) {
            AdvancedLikes advancedLikes = new AdvancedLikes();
            advancedLikes.setUserId(user.getId());
            advancedLikes.setSchoolName(user.getSchoolName());
            //我的意向选择
            List<Likes> likes = queryByLikeUserId(user.getId());
            List<String> likesStringList = new LinkedList<>();
            for (Likes like : likes) {
                User u = userService.queryById(like.getLikedUserId(), User.Column.id, User.Column.schoolName);
                likesStringList.add(u.getSchoolName());
            }
            advancedLikes.setLikesList(likesStringList);
            List<Integer> theUserThatIHaveSign = new ArrayList<>();
            List<String> signStringList = new LinkedList<>();
            List<Sign> signs1 = signService.queryBySignUserId(user.getId());
            List<Sign> signs = signService.queryBySignedUserId(user.getId());
            signs.addAll(signs1);
            for (Sign sign : signs) {
                //我即为主动签约人
                if (user.getId().equals(sign.getSignUserId())) {
                    User u = userService.queryById(sign.getSignedUserId(), User.Column.id, User.Column.schoolName);
                    signStringList.add(u.getSchoolName());
                    theUserThatIHaveSign.add(sign.getSignedUserId());
                } else {
                    User u = userService.queryById(sign.getSignUserId(), User.Column.id, User.Column.schoolName);
                    signStringList.add(u.getSchoolName());
                    theUserThatIHaveSign.add(sign.getSignUserId());
                }
            }
            advancedLikes.setSignList(signStringList);
            //我的签约
//            advancedLikes.setSignList(signs);
            //主动邀请我的用户
            List<StringLikesWithMark> stringLikesWithMarks = new LinkedList<>();
            List<Likes> theUserThatLikesMe = queryByLikedUserId(user.getId());
            for (Likes l : theUserThatLikesMe) {
                StringLikesWithMark tmp = new StringLikesWithMark();
                tmp.setUpdateTime(l.getUpdateTime());
                tmp.setLikeId(l.getId());
                //我即为主动表明意向的人
                if (l.getLikeUserId().equals(user.getId())) {
                    User u = userService.queryById(l.getLikedUserId());
                    tmp.setSchoolName(u.getSchoolName());
                    if (search(theUserThatIHaveSign, l.getLikedUserId())) {
                        tmp.setSigned(true);
                    }

                } else {
                    User u = userService.queryById(l.getLikeUserId());
                    tmp.setSchoolName(u.getSchoolName());
                    if (search(theUserThatIHaveSign, l.getLikeUserId())) {
                        tmp.setSigned(true);
                    }
                }
                stringLikesWithMarks.add(tmp);
            }
            advancedLikes.setFullLikes(stringLikesWithMarks);
            List<Pics> logos = picsService.querySelective(null, user.getId(), FileEnum.LOGO.value());
            boolean logo = false;
            if (logos.size() > 0) {
                logo = true;
            }
            advancedLikes.setHasLogo(logo);
            advancedLikesLinkedList.add(advancedLikes);
        }
        return advancedLikesLinkedList;
    }

    private boolean search(List<Integer> theUserThatIHaveSign, Integer likedUserId) {
        for (Integer integer : theUserThatIHaveSign) {
            if (integer.equals(likedUserId)) {
                return true;
            }
        }
        return false;
    }


    public Workbook exportLikesForm(List<User> users) {
        Workbook workbook = new HSSFWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("IMPACT");
        font.setBold(true);
        cellStyle.setFont(font);
        Sheet sheet = workbook.createSheet();
        this.prepare(cellStyle, sheet);
        int i = 0;

        List<AdvancedLikes> advancedLikes = retrieveAdvancedLikes(users);
        for (AdvancedLikes advancedLike : advancedLikes) {
            String schoolName = advancedLike.getSchoolName();
            Row row = sheet.createRow(i + 2);
            i++;
            Cell cell = row.createCell(0);
            cell.setCellValue(schoolName);
            List<String> likesList = advancedLike.getLikesList();
            StringBuilder likes = new StringBuilder();
            for (String s : likesList) {
                likes.append(s).append(",");
            }
            if (likes.length() > 0) {
                likes.deleteCharAt(likes.length() - 1);
            }
            cell = row.createCell(1);
            cell.setCellValue(likes.toString());

            List<String> signList = advancedLike.getSignList();
            StringBuilder signs = new StringBuilder();
            for (String s : signList) {
                signs.append(s).append(",");
            }
            if (signs.length() > 0) {
                signs.deleteCharAt(signs.length() - 1);
            }
            cell = row.createCell(2);
            cell.setCellValue(signs.toString());

            List<StringLikesWithMark> fullLikes = advancedLike.getFullLikes();
            StringBuilder full = new StringBuilder();
            for (StringLikesWithMark fullLike : fullLikes) {
                if (fullLike.isSigned()) {
                    full.append(fullLike.getSchoolName()).append("(已接收 ").append(fullLike.getUpdateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))).append(")").append(",");
                } else {
                    full.append(fullLike.getSchoolName()).append("(未接受)").append(",");
                }
            }
            cell = row.createCell(3);
            cell.setCellValue(full.toString());
            boolean hasLogo = advancedLike.isHasLogo();
            cell = row.createCell(4);
            cell.setCellValue(hasLogo ? "已上传" : "未上传");
        }
        return workbook;
    }

    public void prepare(CellStyle cellStyle, Sheet sheet) {
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("签约意向表");
        row = sheet.createRow(1);

        Cell cell = row.createCell(0);
        cell.setCellValue("学校名称");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(1);
        cell.setCellValue("邀约学校(未接受)");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(2);
        cell.setCellValue("签约成功高校");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(3);
        cell.setCellValue("被邀约学校(未签约)");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(4);
        cell.setCellValue("是否上传学校logo");
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
            User u1 = userService.queryById(likes.getLikeUserId(), User.Column.id, User.Column.schoolName);
            cell.setCellValue(u1.getSchoolName());

            User u2 = userService.queryById(likes.getLikedUserId(), User.Column.id, User.Column.schoolName);
            //cell.setCellValue(u2.getSchoolName());

            cell = row.createCell(1);
            cell.setCellValue(u2.getSchoolName());

            cell = row.createCell(2);
            cell.setCellValue(likes.getAddTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
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
    public PageInfo<LikeWithUser> querySelective(Integer year, String likeSchoolName, Integer page, Integer pageSize, String sort, String order) {
//        if (page != null && pageSize != null) {
//            PageHelper.startPage(page, pageSize);
//        }
        List<LikeWithUser> likeWithUsers = likeWithUserMapper.querySelective(year, likeSchoolName, (page-1)*pageSize, pageSize, sort, order);
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

    public List<Likes> queryByLikeUserId(Integer likeUserId, Column... columns) {
        LikesExample likesExample = new LikesExample();
        Criteria criteria = likesExample.createCriteria();
        criteria.andLikeUserIdEqualTo(likeUserId);
        List<Likes> likes = likesMapper.selectByExampleSelective(likesExample, columns);
        return likes;
    }

    public List<Likes> queryByLikeUserIdAndLikedUserId(Integer likeUserId, Integer likedUserId, Column... columns) {
        LikesExample likesExample = new LikesExample();
        Criteria criteria = likesExample.createCriteria();
        criteria.andLikeUserIdEqualTo(likeUserId);
        criteria.andLikedUserIdEqualTo(likedUserId);
        List<Likes> likes = likesMapper.selectByExampleSelective(likesExample, columns);
        return likes;
    }

    public List<Likes> queryByLikedUserId(Integer likedUserId, Column... columns) {
        LikesExample likesExample = new LikesExample();
        Criteria criteria = likesExample.createCriteria();
        criteria.andLikedUserIdEqualTo(likedUserId);
        List<Likes> likes = likesMapper.selectByExampleSelective(likesExample, columns);
        return likes;
    }

    public PageInfo<Likes> querySelective() {
        return querySelective(null, null, null, null, null, null, null, null, null, null, null);

    }

    public void deleteByLikeUserIdAndLikedUserId(Integer likeUserId, Integer likedUserId) {
        LikesExample likesExample = new LikesExample();
        Criteria criteria = likesExample.createCriteria();
        criteria.andLikeUserIdEqualTo(likeUserId);
        criteria.andLikedUserIdEqualTo(likedUserId);
        likesMapper.deleteByExample(likesExample);
    }

    public Likes queryByLikeId(Integer likeId, Column... columns) {
        LikesExample likesExample = new LikesExample();
        Criteria criteria = likesExample.createCriteria();
        criteria.andIdEqualTo(likeId);
        List<Likes> likes = likesMapper.selectByExampleSelective(likesExample, columns);
        return likes.size() == 0 ? null : likes.get(0);
    }
}
