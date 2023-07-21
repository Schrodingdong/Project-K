package com.schrodingdong.usermanagerservice.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SaveUserParams {
    @NotBlank
    private String email;
    private String username;
    private String bio;
}
