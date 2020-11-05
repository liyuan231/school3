package com.school.dto;

import com.school.model.User;

public class UserWithMark {
    private User user;
    private Boolean choose;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getChoose() {
        return choose;
    }

    public void setChoose(Boolean choose) {
        this.choose = choose;
    }
}
