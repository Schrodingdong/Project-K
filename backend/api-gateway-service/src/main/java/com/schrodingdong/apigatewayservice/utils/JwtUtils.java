package com.schrodingdong.apigatewayservice.utils;

import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

    public static String extractJwt(String rawJwt) {
        String tokenPrefix = "Bearer ";
        if (!rawJwt.startsWith(tokenPrefix)){
            throw new RuntimeException("Invalid token syntax : " + rawJwt);
        }
        return rawJwt.substring(tokenPrefix.length());
    }
}
