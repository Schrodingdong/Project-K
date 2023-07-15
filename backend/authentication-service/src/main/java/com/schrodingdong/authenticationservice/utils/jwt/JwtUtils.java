package com.schrodingdong.authenticationservice.utils.jwt;

import com.schrodingdong.authenticationservice.models.AuthModel;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
public class JwtUtils {
    // 5 hour validity
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    @Value("${jwt.secret}")
    private String secret;

    /**
     * Generates a JWT auth token for a given user email
     * @param user user model containing the email
     * @return JWT string
     * */
    public String generateToken(AuthModel user){
        Map<String, Object> claims = new HashMap<>();
        final String subject = user.getEmail();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * Validates a JWT token
     * @param token JWT token
     * @throws RuntimeException if the token is invalid
     * */
    public void validateToken(String token) throws RuntimeException{
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        } catch (Exception e){
            throw new RuntimeException("Invalid token");
        }
    }
}
