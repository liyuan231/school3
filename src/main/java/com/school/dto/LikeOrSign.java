package com.school.dto;

import java.time.LocalDateTime;

public class LikeOrSign {
    private Integer signIdOrLikeId;
    private String schoolName;
    private Boolean signed;
    private LocalDateTime updateTime;

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
