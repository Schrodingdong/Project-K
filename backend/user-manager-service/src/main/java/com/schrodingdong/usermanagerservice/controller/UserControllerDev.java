package com.schrodingdong.usermanagerservice.controller;

import com.schrodingdong.usermanagerservice.serivce.UserService;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user/dev")
@Profile("dev")
@AllArgsConstructor
public class UserControllerDev {
    private final UserService userService;

    @GetMapping("/get/all")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<?> deleteUser(@RequestParam @NotBlank String email) {
        userService.deleteAllUsers();
        return ResponseEntity.ok().body("All users deleted");
    }
}
