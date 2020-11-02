package com.school.dao;

import com.school.dto.golden.likelist;
import com.school.model.Pics;
import com.school.model.User;

import java.util.List;


public interface userdao {

	List<likelist> select_likes(Integer id);

//	boolean select_both(String sch_name, Integer id);

	User select_user(Integer user_id);

	List<Pics> select_pic(Integer user_id);

}
