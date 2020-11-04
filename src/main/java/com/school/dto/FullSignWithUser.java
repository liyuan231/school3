package com.school.dto;

public class FullSignWithUser {
    private Integer signId;

    private Integer signUserId;
    private String signUserSchoolName;
    private String signUserLogo;

    private Integer signedUserId;
    private String signedUserSchoolName;
    private String signedUserLogo;

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

    public String getSignUserSchoolName() {
        return signUserSchoolName;
    }

    public void setSignUserSchoolName(String signUserSchoolName) {
        this.signUserSchoolName = signUserSchoolName;
    }

    public String getSignUserLogo() {
        return signUserLogo;
    }

    public void setSignUserLogo(String signUserLogo) {
        this.signUserLogo = signUserLogo;
    }

    public Integer getSignedUserId() {
        return signedUserId;
    }

    public void setSignedUserId(Integer signedUserId) {
        this.signedUserId = signedUserId;
    }

    public String getSignedUserSchoolName() {
        return signedUserSchoolName;
    }

    public void setSignedUserSchoolName(String signedUserSchoolName) {
        this.signedUserSchoolName = signedUserSchoolName;
    }

    public String getSignedUserLogo() {
        return signedUserLogo;
    }

    public void setSignedUserLogo(String signedUserLogo) {
        this.signedUserLogo = signedUserLogo;
    }
}
