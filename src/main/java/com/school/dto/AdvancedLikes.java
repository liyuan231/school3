package com.school.dto;

import com.school.model.Likes;
import com.school.model.Sign;

import java.util.List;

public class AdvancedLikes {
    private Integer id;
    private String schoolname;
    private List<Sign> signList;
    private List<Likes> likesList;


    public AdvancedLikes(Integer id, String schoolname, List<Sign> signList, List<Likes> likesList, List<FullLikes> fullLikes, boolean hasLogo, boolean hasSignature) {
        this.id = id;
        this.schoolname = schoolname;
        this.signList = signList;
        this.likesList = likesList;
        this.fullLikes = fullLikes;
        this.hasLogo = hasLogo;
        this.hasSignature = hasSignature;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSchoolname() {
        return schoolname;
    }

    public void setSchoolname(String schoolname) {
        this.schoolname = schoolname;
    }

    private List<FullLikes> fullLikes;

    private boolean hasLogo;
    private boolean hasSignature;

    public AdvancedLikes() {
    }


    public List<Likes> getLikesList() {
        return likesList;
    }

    public void setLikesList(List<Likes> likesList) {
        this.likesList = likesList;
    }

    public AdvancedLikes(List<Sign> signList, List<Likes> likesList, List<FullLikes> fullLikes, boolean hasLogo, boolean hasSignature) {
        this.signList = signList;
        this.likesList = likesList;
        this.fullLikes = fullLikes;
        this.hasLogo = hasLogo;
        this.hasSignature = hasSignature;
    }

    public List<Sign> getSignList() {
        return signList;
    }

    public void setSignList(List<Sign> signList) {
        this.signList = signList;
    }

    public List<FullLikes> getFullLikes() {
        return fullLikes;
    }

    public void setFullLikes(List<FullLikes> fullLikes) {
        this.fullLikes = fullLikes;
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
