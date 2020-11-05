package com.school.dto;

import java.time.LocalDateTime;

public class SimpleIntention {
    private Integer likeId;
    private Integer schoolId;
    private String schoolName;
    private Boolean logo;
    private LocalDateTime updateTime;

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getLikeId() {
        return likeId;
    }

    public void setLikeId(Integer likeId) {
        this.likeId = likeId;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Boolean getLogo() {
        return logo;
    }

    public void setLogo(Boolean logo) {
        this.logo = logo;
    }
}
