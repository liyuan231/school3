package com.school.dto;

import java.time.LocalDateTime;

public class SignWithUser {
    private Integer signId;//意向的id
    private Integer signUserId;
    private String signSchoolName;
    private Integer signedUserId;
    private String signedSchoolName;
    private LocalDateTime addTime;

    public Integer getSignId() {
        return signId;
    }

    public void setSignId(Integer signId) {
        this.signId = signId;
    }

    public Integer getSignUserId() {
        return signUserId;
    }

    public void setSignUserId(Integer signUserId) {
        this.signUserId = signUserId;
    }

    public String getSignSchoolName() {
        return signSchoolName;
    }

    public void setSignSchoolName(String signSchoolName) {
        this.signSchoolName = signSchoolName;
    }

    public Integer getSignedUserId() {
        return signedUserId;
    }

    public void setSignedUserId(Integer signedUserId) {
        this.signedUserId = signedUserId;
    }

    public String getSignedSchoolName() {
        return signedSchoolName;
    }

    public void setSignedSchoolName(String signedSchoolName) {
        this.signedSchoolName = signedSchoolName;
    }

    public LocalDateTime getAddTime() {
        return addTime;
    }

    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
    }
}
