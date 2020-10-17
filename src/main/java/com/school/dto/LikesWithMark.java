package com.school.dto;

import com.school.model.Likes;

public class LikesWithMark {
    private Likes likes;
    private Boolean signed;

    public Likes getLikes() {
        return likes;
    }

    public void setLikes(Likes likes) {
        this.likes = likes;
    }

    public Boolean getSigned() {
        return signed;
    }

    public void setSigned(Boolean signed) {
        this.signed = signed;
    }
}
