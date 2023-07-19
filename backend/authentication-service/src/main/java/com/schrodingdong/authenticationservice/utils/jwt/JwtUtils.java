package com.schrodingdong.authenticationservice.utils.jwt;

import com.schrodingdong.authenticationservice.models.AuthModel;
import com.schrodingdong.authenticationservice.models.JwtBlacklistModel;
import com.schrodingdong.authenticationservice.repository.JwtBlacklistRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class JwtUtils {
    // 5 hour validity
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    public final Logger LOG = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwt.secret}")
    private String secret;
    @Autowired
    private JwtBlacklistRepository jwtBlacklistRepository;

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

    // ==========================================================================================
    // Methods to trigger on each request =======================================================
    // ==========================================================================================

    /**
     * Validates a JWT token
     * @param token JWT token
     * @throws RuntimeException if the token is invalid
     * */
    public void validateToken(String token) throws RuntimeException{
        try {
            if (isBlackListed(token)) throw new RuntimeException("Token Blacklisted");
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        } catch (Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException("Invalid token : " + token);
        }
    }

    /**
     * Adds a token to the blacklist
     * @param token
     */
    public void blackListToken(String token){
        JwtBlacklistModel jwtBlacklistModel = new JwtBlacklistModel();
        jwtBlacklistModel.setJwt(token);
        jwtBlacklistRepository.save(jwtBlacklistModel);
    }

    /**
     * Checks if the token is blacklisted
     * @param token
     * @throws RuntimeException if the token is blacklisted
     */
    public boolean isBlackListed(String token) {
        return jwtBlacklistRepository.existsByJwt(token);
    }

    public String getTokenSubject(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public static String extractJwt(String rawJwt) {
        String tokenPrefix = "Bearer ";
        if (!rawJwt.startsWith(tokenPrefix)){
            throw new RuntimeException("Invalid token syntax : " + rawJwt);
        }
        return rawJwt.substring(tokenPrefix.length());
    }

    public List<String> getAllBlacklistedTokens() {
        return jwtBlacklistRepository.findAll().stream().map(e -> e.getJwt()).toList();
    }
}
