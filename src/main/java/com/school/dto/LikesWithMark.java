package com.school.dto;

import com.school.model.Likes;

public class LikesWithMark {
    private Likes likes;
    private Boolean signed;
    private Integer signId;
    private String logo1;
    private String logo2;

    public Integer getSignId() {
        return signId;
    }

    public void setSignId(Integer signId) {
        this.signId = signId;
    }

    public Likes getLikes() {
        return likes;
    }

    public void setLikes(Likes likes) {
        this.likes = likes;
    }

    public Boolean getSigned() {
        return signed;
    }

    public void setSigned(Boolean signed) {
        this.signed = signed;
    }

	public String getLogo1() {
		return logo1;
	}

	public void setLogo1(String logo1) {
		this.logo1 = logo1;
	}

	public String getLogo2() {
		return logo2;
	}

	public void setLogo2(String logo2) {
		this.logo2 = logo2;
	}
    
}
