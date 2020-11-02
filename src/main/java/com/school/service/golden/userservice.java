package com.school.service.golden;

import com.school.dto.golden.likelist;
import com.school.model.Likes;
import com.school.model.Pics;
import com.school.model.User;

import java.util.List;


public interface userservice {

	public List<User> get_all_user(Integer id);
	
	public List<Likes> get_all_likes(Integer id);
	
	public Object select_sch(Integer id);

	public int insert_ssch(Integer id);

	public List<likelist> select_likes(Integer id);

	public boolean select_both(Integer host_id, Integer liked_id);

	public User select_user(Integer user_id);

	public List<Pics> select_pic(Integer user_id);
	
	public List<Likes> get_like_me(Integer id);
	
	public String get_logo(Integer likeUserId);
	
//	public List<User> get_not_like();
	
}
