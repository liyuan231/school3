package com.school.service.golden;

import com.school.dao.Likes_1;
import com.school.dao.Pics_2;
import com.school.dao.UserMapper;
import com.school.dao.golden.userdao;
import com.school.dto.golden.ch_bothlike;
import com.school.dto.golden.likelist;
import com.school.model.Pics;
import com.school.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userservice")
public class userserviceimpl implements userservice{
    @Autowired
    userdao user_dao;
    
    @Autowired
    Likes_1 likes_1;
    
    @Autowired
    ch_bothlike both_id;
    
    @Autowired
    UserMapper user_mapper;
    
    @Autowired
    Pics_2 pics;


    public Object select_sch(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }


    public int insert_ssch(Integer id) {
        // TODO Auto-generated method stub
        return 0;
    }


    public List<likelist> select_likes(Integer id) {
        // TODO Auto-generated method stub
        return user_dao.select_likes(id);
    }


    public boolean select_both(Integer host_id, Integer liked_id) {
        // TODO Auto-generated method stub
    	both_id.setHost_id(host_id);
    	both_id.setLiked_id(liked_id);
        if(likes_1.select_both(both_id)==null)
        	return false;
        return true;
    }


    public User select_user(Integer user_id) {
        // TODO Auto-generated method stub
        return user_mapper.selectByPrimaryKey(user_id);
    }


    public List<Pics> select_pic(Integer user_id) {
        // TODO Auto-generated method stub
        return pics.select_pics(user_id);
    }

}
