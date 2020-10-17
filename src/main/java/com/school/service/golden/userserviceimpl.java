package com.school.service.golden;

import java.util.List;

import com.school.dao.golden.userdao;
import com.school.dto.golden.likelist;
import com.school.model.Pics;
import com.school.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userservice")
public class userserviceimpl{
    @Autowired
    userdao user_dao;


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


    public boolean select_both(String sch_name, Integer id) {
        // TODO Auto-generated method stub
        return user_dao.select_both(sch_name,id);
    }


    public User select_user(Integer user_id) {
        // TODO Auto-generated method stub
        return user_dao.select_user(user_id);
    }


    public List<Pics> select_pic(Integer user_id) {
        // TODO Auto-generated method stub
        return user_dao.select_pic(user_id);
    }

}
