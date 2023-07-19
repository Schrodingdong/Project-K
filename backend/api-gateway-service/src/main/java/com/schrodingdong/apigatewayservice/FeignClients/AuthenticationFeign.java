package com.schrodingdong.apigatewayservice.FeignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "authentication-service")
public interface AuthenticationFeign {
    @PostMapping("api/v1/auth/validate-jwt")
    ResponseEntity<?> validateJwt(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt);
}
