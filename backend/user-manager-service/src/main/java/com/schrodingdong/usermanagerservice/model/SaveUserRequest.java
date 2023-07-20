package com.schrodingdong.usermanagerservice.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SaveUserRequest {
    @NotBlank
    private String email;
    private String username;
    private String bio;
}
