package com.school.dao.golden;

import java.util.List;

import com.school.dto.golden.ch_bothlike;
import com.school.dto.golden.likelist;
import com.school.model.Pics;
import com.school.model.User;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;


@Repository
@Primary
public class userdaoimpl extends SqlSessionDaoSupport implements userdao{
	@Autowired
	  public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
	    super.setSqlSessionFactory(sqlSessionFactory);
	  }
	String ns_1="golden.mapper.LikesMapper.";
	String ns_2="golden.mapper.PicsMapper.";
	String ns_3="golden.mapper.UserMapper.";
	@Override
	public List<likelist> select_likes(Integer id) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList(String.valueOf(ns_1)+"select_like_list",id);
	}
	@Override
	public boolean select_both(String sch_name, Integer id) {
		// TODO Auto-generated method stub
		ch_bothlike ch =new ch_bothlike();
		ch.setId(id);
		ch.setSch_name(sch_name);
		if(getSqlSession().selectOne(String.valueOf(ns_1)+"select_both",ch)!=null)
			return true;
		return false;
	}
	@Override
	public User select_user(Integer user_id) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne(String.valueOf(ns_3)+"selectByPrimaryKey",user_id);
	}
	@Override
	public List<Pics> select_pic(Integer user_id) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList(String.valueOf(ns_2)+"select_pics_list",user_id);	
		}
}
