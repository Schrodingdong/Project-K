package com.schrodingdong.usermanagerservice.controller;

import com.schrodingdong.usermanagerservice.model.SaveUserRequest;
import com.schrodingdong.usermanagerservice.model.UserModel;
import com.schrodingdong.usermanagerservice.serivce.UserService;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/save")
    public ResponseEntity<?> saveUser(@RequestBody @Validated SaveUserRequest body) {
        UserModel user = userService.createUser(body.getEmail(), body.getUsername(), "");
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getUserByEmail(@RequestParam @NotBlank String email) {
        UserModel user;
        try {
             user = userService.getUser(email);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("User of email : '"+ email +"' not found");
        }
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody @Validated SaveUserRequest body) {
        UserModel user;
        try {
            user = userService.updateUser(body.getEmail(), body.getUsername(), body.getBio());
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("User of email : '"+ body.getEmail() +"' not found");
        }
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam @NotBlank String email) {
        userService.deleteUser(email);
        return ResponseEntity.ok().body("User of email : '"+ email +"' deleted");
    }
}
