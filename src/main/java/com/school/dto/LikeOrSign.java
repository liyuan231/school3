package com.school.dto;

import java.time.LocalDateTime;

public class LikeOrSign {
    private Integer signIdOrLikeId;
    private String schoolName;
    private Integer schoolId;
    private Boolean signed;
    private LocalDateTime signTime;
    private LocalDateTime updateTime;
    private String logo;

    public LocalDateTime getSignTime() {
        return signTime;
    }

    public void setSignTime(LocalDateTime signTime) {
        this.signTime = signTime;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Boolean getSigned() {
        return signed;
    }

    public void setSigned(Boolean signed) {
        this.signed = signed;
    }

    public Integer getSignIdOrLikeId() {
        return signIdOrLikeId;
    }

    public void setSignIdOrLikeId(Integer signIdOrLikeId) {
        this.signIdOrLikeId = signIdOrLikeId;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
