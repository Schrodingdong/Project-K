package com.schrodingdong.authenticationservice.controllers;


import com.schrodingdong.authenticationservice.models.AuthModel;
import com.schrodingdong.authenticationservice.models.LoginParams;
import com.schrodingdong.authenticationservice.models.RegisterParams;
import com.schrodingdong.authenticationservice.services.AuthService;
import com.schrodingdong.authenticationservice.utils.jwt.JwtUtils;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/register")
    public ResponseEntity<?> register(@Validated @RequestBody RegisterParams params) {
        AuthModel user = new AuthModel();
        try {
            user = authService.register(params.getEmail(), params.getPassword(), params.getUsername());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody LoginParams params, @RequestHeader Map<String, String> requestHeaders){
        // check on token for bypassing the Login7
        try {
            String jwt = JwtUtils.extractJwt(requestHeaders.get("authorization"));
            if (jwt != null) {
                // check token validity
                authService.validateToken(jwt);
                // check token issuer and compare it with credentials
                boolean subjComp = authService.isTokenSubjectSameAsEmail(jwt, params.getEmail());
                if (subjComp)
                    return ResponseEntity.ok().body(jwt);
            }
        } catch (RuntimeException e) {
            LOG.warn("needs login : " + e.getMessage());
        }
        String jwt;
        try {
            jwt = authService.login(params.getEmail(), params.getPassword());
        } catch (Exception e) {
            LOG.warn(e.getMessage());
            return ResponseEntity.badRequest().body("Login not possible");
        }
        return ResponseEntity.ok().body(jwt);
    }

    @PostMapping("/validate-jwt")
    public ResponseEntity<?> validateJwt(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt) {
        // validate the jwt token
        try {
            jwt = JwtUtils.extractJwt(jwt);
            authService.validateToken(jwt);
        } catch (RuntimeException e) {
            LOG.warn(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.ok().body("Valid Token : " + jwt);
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String jwt) {
        try {
            String tokenPrefix = "Bearer ";
            if (!jwt.startsWith(tokenPrefix)){
                LOG.warn("Invalid token syntax: " + jwt);
                throw new RuntimeException("Invalid token");
            }
            jwt = jwt.substring(tokenPrefix.length());
            authService.logout(jwt);
        } catch (RuntimeException e) {
            LOG.warn(e.getMessage());
            return ResponseEntity.internalServerError().body("Invalid Token");
        }
        return ResponseEntity.ok().body("Logged out");
    }

}
