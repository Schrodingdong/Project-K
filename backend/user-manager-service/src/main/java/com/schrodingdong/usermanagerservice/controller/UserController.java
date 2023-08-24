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

import java.util.List;
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

    /***
     * Get all users with the exception of the user with sbject
     * @return
     */
    @GetMapping("/get/all")
    public ResponseEntity<?> getAllUsers(@RequestHeader("User-Email") String email) {
        List<UserModel> userList = userService.getAllUsers().stream().filter(user -> !user.getEmail().equals(email)).toList();
        return ResponseEntity.ok().body(userList);
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
    public ResponseEntity<?> updateUser(@RequestHeader("User-Email") String email, @RequestBody UpdateUserParams body) {
        UserModel user;
        try {
            user = userService.updateUser(email, body.getUsername(), body.getBio());
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("User of email : '"+ email +"' not found");
        }
        return ResponseEntity.ok().body(user);
    }


    @PutMapping("/follow")
    public ResponseEntity<?> followUser(@RequestHeader("User-Email") String email, @RequestParam @NotBlank String toFollow) {
        UserModel user;
        try {
            user = userService.followUser(email, toFollow);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/unfollow")
    public ResponseEntity<?> unfollowUser(@RequestHeader("User-Email") String email, @RequestParam @NotBlank String toUnfollow) {
        try {
            userService.unfollowUser(email, toUnfollow);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body("Successfully unfollowed '"+ toUnfollow +"'");
    }
}
