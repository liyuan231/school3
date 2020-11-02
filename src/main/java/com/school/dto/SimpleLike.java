package com.school.dto;

public class SimpleLike {
    private Integer likeId;
    private Integer likeUserId;
    private Integer likedUserId;
    private String likeSchoolName;
    private String likedSchoolName;

    public Integer getLikeId() {
        return likeId;
    }

    public void setLikeId(Integer likeId) {
        this.likeId = likeId;
    }

    public Integer getLikeUserId() {
        return likeUserId;
    }

    public void setLikeUserId(Integer likeUserId) {
        this.likeUserId = likeUserId;
    }

    public Integer getLikedUserId() {
        return likedUserId;
    }

    public void setLikedUserId(Integer likedUserId) {
        this.likedUserId = likedUserId;
    }

    public String getLikeSchoolName() {
        return likeSchoolName;
    }

    public void setLikeSchoolName(String likeSchoolName) {
        this.likeSchoolName = likeSchoolName;
    }

    public String getLikedSchoolName() {
        return likedSchoolName;
    }

    public void setLikedSchoolName(String likedSchoolName) {
        this.likedSchoolName = likedSchoolName;
    }
}
