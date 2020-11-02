package com.school.dto;

import java.util.List;

public class SimpleLikeUser {
    private Integer userId;
    private String schoolName;
    private List<SimpleLike> likes;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public List<SimpleLike> getLikes() {
        return likes;
    }

    public void setLikes(List<SimpleLike> likes) {
        this.likes = likes;
    }
}
