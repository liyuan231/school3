package com.school.dto;

import com.school.model.Likes;

public class FullLikes {
    private Likes likes;
    private boolean signed;

    public Likes getLikes() {
        return likes;
    }

    public void setLikes(Likes likes) {
        this.likes = likes;
    }

    public void setSigned(boolean signed) {
        this.signed = signed;
    }

    public boolean isSigned() {
        return signed;
    }
}
