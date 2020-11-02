package com.school.dto.golden;

import org.springframework.stereotype.Component;

@Component
public class ch_bothlike {
    Integer host_id;
    Integer liked_id;
    


	public Integer getHost_id() {
		return host_id;
	}
	public void setHost_id(Integer host_id) {
		this.host_id = host_id;
	}
	public Integer getLiked_id() {
		return liked_id;
	}
	public void setLiked_id(Integer liked_id) {
		this.liked_id = liked_id;
	}
}
