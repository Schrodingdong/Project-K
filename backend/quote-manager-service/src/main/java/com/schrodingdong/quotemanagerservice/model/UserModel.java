package com.schrodingdong.quotemanagerservice.model;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class UserModel {
    private final String email;
    private String username;
    private String bio;

    public UserModel(String email, String username, String bio) {
        this.email = email;
        this.username = username;
        this.bio = bio;
    }
}
