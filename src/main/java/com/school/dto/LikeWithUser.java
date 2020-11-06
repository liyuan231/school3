package com.school.dto;

import java.time.LocalDateTime;

public class LikeWithUser {
    private Integer likeId;//意向的id
    private Integer likeUserId;
    private String likeSchoolName;
    private Integer likedUserId;
    private String likedSchoolName;
    private LocalDateTime addTime;
    private String logo;
    private String signature;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
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

    public Integer getLikeId() {
        return likeId;
    }

    public void setLikeId(Integer likeId) {
        this.likeId = likeId;
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

    public LocalDateTime getAddTime() {
        return addTime;
    }

    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
    }
}
