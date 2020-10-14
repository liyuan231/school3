package com.school.dto;

import java.util.List;

public class SimpleLikes {
    private Integer likeUserId;
    private String likeSchoolName;
    private List<String> likedSchoolNames;

    public Integer getLikeUserId() {
        return likeUserId;
    }

    public void setLikeUserId(Integer likeUserId) {
        this.likeUserId = likeUserId;
    }

    public String getLikeSchoolName() {
        return likeSchoolName;
    }

    public void setLikeSchoolName(String likeSchoolName) {
        this.likeSchoolName = likeSchoolName;
    }

    public List<String> getLikedSchoolNames() {
        return likedSchoolNames;
    }

    public void setLikedSchoolNames(List<String> likedSchoolNames) {
        this.likedSchoolNames = likedSchoolNames;
    }
}
