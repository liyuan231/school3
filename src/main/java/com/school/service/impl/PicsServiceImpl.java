//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.school.service.impl;

import com.school.dao.PicsMapper;
import com.school.exception.UserNotFoundException;
import com.school.model.Pics;
import com.school.model.PicsExample;
import com.school.model.PicsExample.Criteria;
import com.school.model.User;
import com.school.utils.FileEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
public class PicsServiceImpl {
    @Value("${file.path}")
    private String filePath;
    @Resource
    private PicsMapper picsMapper;
    @Autowired
    private UserServiceImpl userService;

    public PicsServiceImpl() {
    }

    public void insert(Integer userId, String fileName, int type) {
        Pics pics = new Pics();
        pics.setUserId(userId);
        pics.setLocation(fileName);
        pics.setType(type);
        pics.setAddTime(LocalDateTime.now());
        pics.setUpdateTime(LocalDateTime.now());
        this.picsMapper.insertSelective(pics);
    }

    public List<Pics> querySelective(Integer id, Integer userId, Integer type) {
        return querySelective(id, userId, type, null, null);
    }

    public List<Pics> querySelective(Integer id, Integer userId, Integer type, String sort, String order) {
        PicsExample picsExample = new PicsExample();
        if (StringUtils.hasText(sort) && StringUtils.hasText(order)) {
            picsExample.setOrderByClause(sort + " " + order);
        }
        Criteria criteria = picsExample.createCriteria();
        if (!StringUtils.isEmpty(id)) {
            criteria.andIdEqualTo(id);
        }

        if (!StringUtils.isEmpty(userId)) {
            criteria.andUserIdEqualTo(userId);
        }

        if (!StringUtils.isEmpty(type)) {
            criteria.andTypeEqualTo(type);
        }
        criteria.andDeletedEqualTo(false);
        return this.picsMapper.selectByExampleSelective(picsExample);
    }

    public void add(User user, String location, Integer type) throws IOException, UserNotFoundException {
        if (type == FileEnum.AVATAR_URL.value()) {
//            user.setAvatarurl(location);
            this.userService.update(user);
        } else {
            this.add(user.getId(), location, type);
        }

    }

    public void store(MultipartFile file, String location) throws IOException {
        File fileInServer = new File(this.filePath + location);
        file.transferTo(fileInServer);
    }

    private String getRandomFileName(String fileName) {
        String format = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        UUID uuid = UUID.randomUUID();
        return uuid + "." + format;
    }

    private void add(Integer userId, String fileName, Integer type) throws IOException, UserNotFoundException {
        Pics pics = new Pics();
        pics.setUserId(userId);
        pics.setLocation(fileName);
        pics.setType(type);
        this.add(pics);
    }

    private void add(Pics pics) {
        pics.setAddTime(LocalDateTime.now());
        pics.setUpdateTime(LocalDateTime.now());
        this.picsMapper.insertSelective(pics);
    }

    public Pics update(Integer id, Integer userId, String location, Integer type, Boolean deleted) {
        Pics pics = new Pics();
        pics.setId(id);
        pics.setUserId(userId);
        pics.setLocation(location);
        pics.setType(type);
        pics.setDeleted(deleted);
        return this.update(pics);
    }

    public Pics update(Pics pics) {
        List<Pics> picsInDB = this.querySelective(pics.getId(), (Integer) null, (Integer) null);
        if (picsInDB.size() == 0) {
            throw new NullPointerException("图片不存在！");
        } else {
            pics.setUpdateTime(LocalDateTime.now());
            this.picsMapper.updateByPrimaryKeySelective(pics);
            List<Pics> pics1 = this.querySelective(pics.getId(), (Integer) null, (Integer) null);
            return pics1.size() == 0 ? null : pics1.get(0);
        }
    }

    public void delete(Pics pics) {
        picsMapper.deleteByPrimaryKey(pics.getId());
//        pics.setDeleted(true);
//        this.update(pics);
    }

    public Pics upload(Integer userId, Integer type, MultipartFile file) throws IOException, UserNotFoundException {
        List<User> users = this.userService.querySelectiveLike(userId, (String) null, (String) null, (String) null, (String) null, (String) null, (Integer) null, (String) null, (String) null, (Integer) null, (String) null, (String) null, (Integer) null, (String) null, (Integer) null, (Integer) null, (String) null, (String) null);
        if (users.size() == 0) {
            throw new UserNotFoundException("用户不存在！");
        } else {
            User user = (User) users.get(0);
            List<Pics> pics = this.querySelective((Integer) null, userId, type);
            String location = this.getRandomFileName((String) Objects.requireNonNull(file.getOriginalFilename()));
            this.store(file, location);
            if (pics.size() != 0) {
                Pics pics1 = (Pics) pics.get(0);
                this.update(pics1.getId(), user.getId(), location, type, (Boolean) null);
                List<Pics> pics2 = this.querySelective(pics1.getId(), (Integer) null, (Integer) null);
                return (Pics) pics2.get(0);
            } else {
                this.add(user, location, type);
                List<Pics> pics1 = this.querySelective((Integer) null, userId, type);
                return (Pics) pics1.get(0);
            }
        }
    }

    public List<Pics> querySelective(Integer userId, int type) {
        return querySelective(null,userId,type);
    }
}
