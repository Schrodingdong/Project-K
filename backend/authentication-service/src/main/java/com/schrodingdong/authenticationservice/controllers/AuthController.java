package com.schrodingdong.authenticationservice.controllers;


import com.schrodingdong.authenticationservice.models.AuthModel;
import com.schrodingdong.authenticationservice.models.LoginParams;
import com.schrodingdong.authenticationservice.models.RegisterParams;
import com.schrodingdong.authenticationservice.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/register/all")
    public ResponseEntity<?> getAllUsers(){
        return ResponseEntity.ok().body(
                authService.getAllUsers()
        );
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Validated @RequestBody RegisterParams params) {
        AuthModel user = new AuthModel();
        try {
            user = authService.register(params.getEmail(), params.getPassword());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        // register the user in the user manager service
        // TODO
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody LoginParams params) {
        // login the user in the user manager service
        String jwt;
        try {
            jwt = authService.login(params.getEmail(), params.getPassword());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body(jwt);
    }

    @PostMapping("/validate-jwt")
    public ResponseEntity<?> validateJwt(@RequestHeader("Authorization") String jwt) {
        // validate the jwt token
        try {
            String tokenPrefix = "Bearer ";
            if (!jwt.startsWith(tokenPrefix)){
                throw new RuntimeException("Invalid token");
            }
            jwt = jwt.substring(tokenPrefix.length());
            authService.validateToken(jwt);
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body("Invalid Token");
        }
        return ResponseEntity.ok().body("Valid Token : " + jwt);
    }

}
