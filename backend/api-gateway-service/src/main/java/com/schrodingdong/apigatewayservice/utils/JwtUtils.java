package com.schrodingdong.apigatewayservice.utils;

import java.util.Base64;

import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

	/***
	 * Extract the JWT token from the header 
	 * @param rawJwt The raw token from the header
	 * @return The jwt token alone
	 */
    public static String extractJwt(String rawJwt) {
        String tokenPrefix = "Bearer ";
        if (!rawJwt.startsWith(tokenPrefix)){
            throw new RuntimeException("Invalid token syntax : " + rawJwt);
        }
        return rawJwt.substring(tokenPrefix.length());
    }
    
    /***
     * Extracts the subject from the token
     * @param jwt
     * @return token's subject
     */
    public static String getSubjectFromToken(String jwtToken) {
    	String[] token = jwtToken.split("\\.");
    	if (token.length != 3) return null;
    	String payload = new String(Base64.getUrlDecoder().decode(token[1]));
    	String subBoilerPlate = "\"sub\":\"";
    	int subStartIndex = payload.indexOf(subBoilerPlate)+subBoilerPlate.length();
    	int subEndIndex = payload.indexOf("\"", subStartIndex);
    	return payload.substring(subStartIndex, subEndIndex);
    }
}
