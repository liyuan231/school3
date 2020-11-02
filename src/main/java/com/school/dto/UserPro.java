package com.school.dto;

import com.school.model.User;

public class UserPro extends User {
    private String logo;

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}
}
