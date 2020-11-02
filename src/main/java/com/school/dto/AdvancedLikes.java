package com.school.dto;

import java.util.List;

public class AdvancedLikes {
    //    private Integer likeId;
    private Integer userId;
    private String schoolName;
    private List<String> likesList;
    private List<String> signList;
    private List<StringLikesWithMark> fullLikes;
    private boolean hasLogo;
    private boolean hasSignature;

    public List<StringLikesWithMark> getFullLikes() {
        return fullLikes;
    }

    public void setFullLikes(List<StringLikesWithMark> fullLikes) {
        this.fullLikes = fullLikes;
    }

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

    public List<String> getLikesList() {
        return likesList;
    }

    public void setLikesList(List<String> likesList) {
        this.likesList = likesList;
    }

    public List<String> getSignList() {
        return signList;
    }

    public void setSignList(List<String> signList) {
        this.signList = signList;
    }


    public boolean isHasLogo() {
        return hasLogo;
    }

    public void setHasLogo(boolean hasLogo) {
        this.hasLogo = hasLogo;
    }

    public boolean isHasSignature() {
        return hasSignature;
    }

    public void setHasSignature(boolean hasSignature) {
        this.hasSignature = hasSignature;
    }
}
