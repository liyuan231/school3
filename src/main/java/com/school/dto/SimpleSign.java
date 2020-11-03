package com.school.dto;

public class SimpleSign {
    private Integer signId;
    private String signSchoolName;
    private String signedSchoolName;

    public Integer getSignId() {
        return signId;
    }

    public void setSignId(Integer signId) {
        this.signId = signId;
    }

    public String getSignSchoolName() {
        return signSchoolName;
    }

    public void setSignSchoolName(String signSchoolName) {
        this.signSchoolName = signSchoolName;
    }

    public String getSignedSchoolName() {
        return signedSchoolName;
    }

    public void setSignedSchoolName(String signedSchoolName) {
        this.signedSchoolName = signedSchoolName;
    }
}
