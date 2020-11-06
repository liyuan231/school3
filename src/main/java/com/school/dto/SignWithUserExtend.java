package com.school.dto;

public class SignWithUserExtend {
    private SignWithUser signWithUser;
    private Boolean signUserLogo;
    private Boolean signedUserLogo;
    private Boolean signUserSignature;
    private Boolean signedUserSignature;

    public Boolean getSignUserLogo() {
        return signUserLogo;
    }

    public SignWithUser getSignWithUser() {
        return signWithUser;
    }

    public void setSignWithUser(SignWithUser signWithUser) {
        this.signWithUser = signWithUser;
    }

    public void setSignUserLogo(Boolean signUserLogo) {
        this.signUserLogo = signUserLogo;
    }

    public Boolean getSignedUserLogo() {
        return signedUserLogo;
    }

    public void setSignedUserLogo(Boolean signedUserLogo) {
        this.signedUserLogo = signedUserLogo;
    }

    public Boolean getSignUserSignature() {
        return signUserSignature;
    }

    public void setSignUserSignature(Boolean signUserSignature) {
        this.signUserSignature = signUserSignature;
    }

    public Boolean getSignedUserSignature() {
        return signedUserSignature;
    }

    public void setSignedUserSignature(Boolean signedUserSignature) {
        this.signedUserSignature = signedUserSignature;
    }
}
