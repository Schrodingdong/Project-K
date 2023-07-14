package com.schrodingdong.authenticationservice.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginParams {
    @NotBlank(message = "The email must not be null")
    @Email(message = "The email must be a valid email")
    public String email;
    @NotBlank(message = "The email must not be null")
    public String password;
}
