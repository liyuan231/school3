package com.school.service.golden;

import java.util.List;

import com.school.dto.golden.likelist;
import com.school.model.Pics;
import com.school.model.User;




public interface userservice {

	public Object select_sch(Integer id);

	public int insert_ssch(Integer id);

	public List<likelist> select_likes(Integer id);

	public boolean select_both(Integer host_id, Integer liked_id);

	public User select_user(Integer user_id);

	public List<Pics> select_pic(Integer user_id);
	
}
