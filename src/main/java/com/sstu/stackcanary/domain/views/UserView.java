package com.sstu.stackcanary.domain.views;

import com.sstu.stackcanary.domain.User;

public class UserView {
    final private Integer id;
    final private String username;

    public UserView(final User u) {
        this.id = u.getId();
        this.username = u.getUsername();
    }
}
