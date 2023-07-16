package com.schrodingdong.authenticationservice.models;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity @Data
public class JwtBlacklistModel {
    @Id
    private Long id;
    @NotNull
    private String jwt;
}
