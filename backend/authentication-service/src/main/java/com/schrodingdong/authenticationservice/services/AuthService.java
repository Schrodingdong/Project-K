package com.schrodingdong.authenticationservice.services;

import com.schrodingdong.authenticationservice.amqp.QueueSenderRegistration;
import com.schrodingdong.authenticationservice.models.AuthModel;
import com.schrodingdong.authenticationservice.repository.AuthenticationRepository;
import com.schrodingdong.authenticationservice.repository.JwtBlacklistRepository;
import com.schrodingdong.authenticationservice.utils.jwt.JwtUtils;
import lombok.AllArgsConstructor;

import org.json.simple.JSONObject;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthService {
    private final AuthenticationRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final QueueSenderRegistration queueSenderRegistration;
    private final JwtUtils jwtUtils;

    /**
     * Method to register a user to the DB, if the user-email already exists, throw an exception
     * @param email user email
     * @param password password that will be encrypted
     * @return - the user that was created
     * @throws IOException 
     * @Throws - RuntimeException if the user already exists
     * */
    public AuthModel register(String email, String password, String username) throws IOException, RuntimeException {
        if (authRepository.existsByEmail(email)){
            throw new RuntimeException("User already exists : " + email);
        }
        AuthModel user = new AuthModel();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        AuthModel u = authRepository.save(user);
        if (u == null) {
        	throw new IOException("Unable to save the user");
        }
        // register the user in the user manager service
        JSONObject registrationMessage = new JSONObject();
        registrationMessage.put("userEmail", email);
        registrationMessage.put("username", username);
        
        
        queueSenderRegistration.send(registrationMessage.toString());
        
        
        
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
            throw new RuntimeException("User does not exist : " + email);
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
     * Validate the given token, and checks if its blacklisted
     * @param token token to validate
     * @throws RuntimeException if token is invalid
     */
    public void validateToken(String token) throws RuntimeException{
        jwtUtils.validateToken(token);
    }

    public boolean isTokenSubjectSameAsEmail(String token, String email) throws RuntimeException{
        String tokenSubject = jwtUtils.getTokenSubject(token);
        return tokenSubject.equals(email);
    }

    /**
     * Logout method to add the token to the blacklist
     * @param token token to add to the blacklist
     */
    public void logout(String token) throws RuntimeException{
        jwtUtils.blackListToken(token);
    }

    /**
     * check if the token is blacklisted
     * @param token token to check
     * @return true if the token is blacklisted
     */
    public boolean isTokenBlacklisted(String token){
        return jwtUtils.isBlackListed(token);
    }

    public List<AuthModel> getAllUsers() {
        return authRepository.findAll();
    }
    public List<String> getAllBlackListedTokens() {
        return jwtUtils.getAllBlacklistedTokens();
    }
}
