package com.schrodingdong.usermanagerservice.model;

import lombok.Data;

@Data
public class UpdateUserParams {
    private String username;
    private String bio;
}
