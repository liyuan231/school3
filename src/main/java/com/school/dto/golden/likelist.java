package com.school.dto.golden;

public class likelist {
     String sch_name;
     Integer liked_id;
     public Integer getLiked_id() {
		return liked_id;
	}
	public void setLiked_id(Integer liked_id) {
		this.liked_id = liked_id;
	}
	boolean flag=false;
	public String getSch_name() {
		return sch_name;
	}
	public void setSch_name(String sch_name) {
		this.sch_name = sch_name;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
     
}
