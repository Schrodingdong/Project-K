package com.schrodingdong.authenticationservice.services;

import com.schrodingdong.authenticationservice.models.AuthModel;
import com.schrodingdong.authenticationservice.repository.AuthenticationRepository;
import com.schrodingdong.authenticationservice.utils.jwt.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthService {
    private final AuthenticationRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    /**
     * Method to register a user to the DB, if the user-email already exists, throw an exception
     * @param email user email
     * @param password password that will be encrypted
     * @return - the user that was created
     * @Throws - RuntimeException if the user already exists
     * */
    public AuthModel register(String email, String password) {
        if (authRepository.existsByEmail(email)){
            throw new RuntimeException("User already exists");
        }
        AuthModel user = new AuthModel();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        AuthModel u = authRepository.save(user);
        return u;
    }

    /**
     * Login method to check if the user exists and if the password is correct, and generate JWT token
     * @param email user email
     * @param password non encrypted password
     * @return - JWT token for the user
     * */
    public String login(String email, String password) {
        // Check if the user exists
        if (!authRepository.existsByEmail(email)){
            throw new RuntimeException("User does not exist");
        }
        // Check if the password is correct
        AuthModel user = authRepository.findByEmail(email);
        if (!passwordEncoder.matches(password, user.getPassword())){
            throw new RuntimeException("Wrong password");
        }
        // generate JWT token
        String token = jwtUtils.generateToken(user);
        return token;
    }

    /**
     * Validate the given token
     * @param token token to validate
     * @throws RuntimeException if token is invalid
     */
    public void validateToken(String token) throws RuntimeException{
        jwtUtils.validateToken(token);
    }

    public List<AuthModel> getAllUsers() {
        return authRepository.findAll();
    }
}
