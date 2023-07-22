package com.schrodingdong.usermanagerservice.controller;

import com.schrodingdong.usermanagerservice.model.SaveUserParams;
import com.schrodingdong.usermanagerservice.model.UpdateUserParams;
import com.schrodingdong.usermanagerservice.model.UserModel;
import com.schrodingdong.usermanagerservice.serivce.UserService;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
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
    public ResponseEntity<?> saveUser(@RequestBody @Validated SaveUserParams body) {
        // check if it already exists
        if (userService.userExists(body.getEmail())) {
            return ResponseEntity.badRequest().body("User of email : '"+ body.getEmail() +"' already exists");
        }
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
    @GetMapping("/get/following")
    public ResponseEntity<?> getFollowingOf(@RequestParam String email) {
        UserModel user;
        try {
            user = userService.getUser(email);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("User of email : '"+ email +"' not found");
        }
        return ResponseEntity.ok().body(user.getFollowing());
    }
    @GetMapping("/get/followers")
    public ResponseEntity<?> getFollowersOf(@RequestParam String email) {
        UserModel user;
        try {
            user = userService.getUser(email);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("User of email : '"+ email +"' not found");
        }
        return ResponseEntity.ok().body(user.getFollowers());
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestParam String email, @RequestBody UpdateUserParams body) {
        UserModel user;
        try {
            user = userService.updateUser(email, body.getUsername(), body.getBio());
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("User of email : '"+ email +"' not found");
        }
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam @NotBlank String email) {
        userService.deleteUser(email);
        return ResponseEntity.ok().body("User of email : '"+ email +"' deleted");
    }

    @PutMapping("/follow")
    public ResponseEntity<?> followUser(@RequestParam @NotBlank String email, @RequestParam @NotBlank String followEmail) {
        UserModel user;
        try {
            user = userService.followUser(email, followEmail);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/unfollow")
    public ResponseEntity<?> unfollowUser(@RequestParam @NotBlank String email, @RequestParam @NotBlank String toUnfollow) {
        try {
            userService.unfollowUser(email, toUnfollow);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body("Successfully unfollowed '"+ toUnfollow +"'");
    }
}
