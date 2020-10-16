//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.school.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.school.dao.LikesMapper;
import com.school.dao.UserMapper;
import com.school.exception.LikesAlreadyExistException;
import com.school.exception.LikesNotFoundException;
import com.school.exception.UserLikesNotCorrespondException;
import com.school.exception.UserNotCorrectException;
import com.school.exception.UserNotFoundException;
import com.school.model.Likes;
import com.school.model.LikesExample;
import com.school.model.User;
import com.school.model.Likes.Column;
import com.school.model.LikesExample.Criteria;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import javax.annotation.Resource;

import com.school.model.UserExample;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional
public class LikeServiceImpl {
    @Resource
    private LikesMapper likesMapper;
    @Resource
    private UserMapper userMapper;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private LikeServiceImpl likeService;
    @Autowired
    private UserToRoleServiceImpl userToRoleService;
    public static int size = 0;

    public LikeServiceImpl() {
    }

    public List<Likes> querySelective(Integer id, Integer likeUserId, String likeSchoolName, Integer likedUserId, String likedSchoolName, Integer page, Integer pageSize, String sort, String order, Boolean distinctByLikeSchoolName) {
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
        size = pageInfo.getSize();
        return pageInfo.getList();
    }

    public void add(Likes like) throws UserNotFoundException, LikesAlreadyExistException, UserNotCorrectException {
        Integer likeUserId = like.getLikeuserid();
        String likeSchoolName = like.getLikeschoolname();
        Integer likedUserId = like.getLikeduserid();
        String likedSchoolName = like.getLikedschoolname();
        User byId = this.userService.findById(like.getLikeduserid());
        if (byId == null) {
            throw new UserNotFoundException("用户不存在！");
        } else {
            List<Likes> likes1 = this.querySelective((Integer)null, likeUserId, likeSchoolName, likedUserId, likedSchoolName, (Integer)null, (Integer)null, (String)null, (String)null, (Boolean)null);
            if (likes1.size() >= 1) {
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
        likes.setLikeduserid(likeUserId);
        likes.setLikeduserid(likedUserId);
        likes.setLikedschoolname(likedSchoolName);
        likes.setLikeschoolname(likeSchoolName);
        likes.setDeleted(deleted);
        return this.update(likes);
    }

    public Likes update(Likes like) throws LikesNotFoundException, UserLikesNotCorrespondException {
        List<Likes> likes = this.querySelective(like.getId(), (Integer)null, (String)null, (Integer)null, (String)null, (Integer)null, (Integer)null, (String)null, (String)null, (Boolean)null);
        if (likes.size() == 0) {
            throw new LikesNotFoundException("该则意向不存在，请检查id");
        } else {
            like.setUpdateTime(LocalDateTime.now());
            this.likesMapper.updateByPrimaryKeySelective(like);
            List<Likes> likesInDb = this.querySelective(like.getId(), (Integer)null, (String)null, (Integer)null, (String)null, (Integer)null, (Integer)null, (String)null, (String)null, (Boolean)null);
            return likesInDb.size() == 0 ? null : (Likes)likesInDb.get(0);
        }
    }

    public void delete(Likes like) throws UserLikesNotCorrespondException, LikesNotFoundException {
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

        while(var6.hasNext()) {
            Likes like = (Likes)var6.next();
            Integer integer = (Integer)map.get(like.getLikeuserid());
            if (integer != null) {
                result.add(like);
            } else {
                map.put(like.getLikeduserid(), like.getLikeuserid());
            }
        }

        return result;
    }

    public void add(Integer likeUserId, String likeSchoolName, Integer likedUserId, String likedSchoolName) throws UserNotFoundException, UserNotCorrectException, LikesAlreadyExistException {
        Likes likes = new Likes();
        likes.setLikeduserid(likedUserId);
        likes.setLikeuserid(likeUserId);
        likes.setLikeschoolname(likeSchoolName);
        likes.setLikeschoolname(likedSchoolName);
        this.add(likes);
    }

    public void deleteById(Integer id) throws UserLikesNotCorrespondException, LikesNotFoundException {
        Likes likes = new Likes();
        likes.setId(id);
        this.delete(likes);
    }
    //查询对我有意向的用户
    public List<Likes> matchByLikedUserId() {
        User user = this.userService.retrieveUserByToken();
        LikesExample likesExample = new LikesExample();
        Criteria criteria = likesExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andLikeduseridEqualTo(user.getId());
        return this.likesMapper.selectByExample(likesExample);
    }
    //查询我有意向的用户
    public List<Likes> matchByLikeUserId() {
        User user = this.userService.retrieveUserByToken();
        LikesExample likesExample = new LikesExample();
        Criteria criteria = likesExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andLikeuseridEqualTo(user.getId());
        return this.likesMapper.selectByExample(likesExample);
    }

    public List<Likes> matchByUserId() {
//        User user = this.userService.retrieveUserByToken();
//        Integer userId = user.getId();
        Integer userId = 283;
        List<Likes> result = new LinkedList();
        List<Likes> matches = this.match();
        Iterator var5 = matches.iterator();

        while(var5.hasNext()) {
            Likes next = (Likes)var5.next();
            if (next.getLikeuserid().equals(userId)) {
                result.add(next);
            }
        }

        return result;
    }
    //查询互相有意向的用户
    public List<Integer> matchbyID() throws UserNotFoundException {
        List<Integer> melike = likeService.findidBymelike();
        List<Integer> likeme = likeService.findidByLikeme();
        List<Integer> user = new LinkedList<>();
        for(Integer id:melike){
            for(Integer id2:likeme){
                if(id==id2){
                    user.add(id);
                }
            }
        }
        return user;

    }
    public User findById(Integer id) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo(id);
        User user = userMapper.selectOneByExampleSelective(userExample);
        return user;
    }
    //    查询对我有意向的用户id
    public List<Integer> findidByLikeme() throws UserNotFoundException {
//        User user = userService.retrieveUserByToken();
        List<Likes> matchs = likeService.matchByLikedUserId();
//        Integer userId = user.getId();
        Integer userId = 283;
//      List<Likes> matchs = likeService.matchByLikeUserId();
//        我被喜欢的用户idd
        List<Integer> users = new LinkedList<>();
        for (Likes match : matchs) {
            User user1 = userService.findById(match.getLikeuserid());
            users.add(user1.getId());
        }
        return users;
    }
    //    查询我有意向的用户id
    public List<Integer> findidBymelike() throws UserNotFoundException {
//        User user = userService.retrieveUserByToken();
        List<Likes> matchs = likeService.matchByLikeUserId();
//        Integer userId = user.getId();
        Integer userId = 283;
//      List<Likes> matchs = likeService.matchByLikeUserId();
//        我喜欢的用户id
        List<Integer> users = new LinkedList<>();
        for (Likes match : matchs) {
            User user1 = userService.findById(match.getLikeduserid());
            users.add(user1.getId());
        }
        return users;
    }

    public Workbook exportLikesForm() {
        List<Likes> likes = this.querySelective((Integer)null, (Integer)null, (String)null, (Integer)null, (String)null, (Integer)null, (Integer)null, (String)null, (String)null, (Boolean)null);
        Map<String, List<String>> map = new TreeMap();
        Iterator var3 = likes.iterator();

        while(var3.hasNext()) {
            Likes like = (Likes)var3.next();
            String likeSchoolName = like.getLikeschoolname();
            String likedSchoolName = like.getLikedschoolname();
            List<String> list = (List)map.get(likeSchoolName);
            if (list == null) {
                list = new LinkedList();
            }

            ((List)list).add(likedSchoolName);
            map.put(likeSchoolName, list);
        }

        Workbook workbook = new HSSFWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("IMPACT");
        font.setBold(true);
        cellStyle.setFont(font);
        Sheet sheet = workbook.createSheet();
        this.prepare(cellStyle, sheet);
        int i = 0;

        for(Iterator var10 = map.entrySet().iterator(); var10.hasNext(); ++i) {
            Entry<String, List<String>> entry = (Entry)var10.next();
            Row row = sheet.createRow(i + 2);
            Cell cell = row.createCell(0);
            cell.setCellValue((String)entry.getKey());
            cell = row.createCell(1);
            StringBuilder s = new StringBuilder(Arrays.toString(((List)entry.getValue()).toArray()));
            if (s.length() > 0) {
                s.deleteCharAt(s.length() - 1);
                s.deleteCharAt(0);
            }

            cell.setCellValue(s.toString());
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
        cell.setCellValue("签约意向");
        cell.setCellStyle(cellStyle);
    }
}
