package com.schrodingdong.apigatewayservice.configuration;

import com.schrodingdong.apigatewayservice.FeignClients.AuthenticationFeign;
import com.schrodingdong.apigatewayservice.amqp.QueueSender;
import com.schrodingdong.apigatewayservice.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/**
 * This class is used to validate JWTs.
 * It is a GatewayFilterFactory, which means it is a filter that can be applied to a route.
 */
@Component
public class JwtValidationGatewayFilterFactory
        extends AbstractGatewayFilterFactory<JwtValidationGatewayFilterFactory.Config> {
    @Autowired
    private QueueSender queueSender;
    private final Logger LOG = LoggerFactory.getLogger(JwtValidationGatewayFilterFactory.class);
    private static Boolean IS_JWT_VALID = null;
    private int count = 0;
    private final int MAX_COUNT = 100;

    public JwtValidationGatewayFilterFactory(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            LOG.info("Validating JWT...");
            // JWT Extraction
            String jwt = exchange.getRequest().getHeaders().getFirst("Authorization");
            String extractedJwt = JwtUtils.extractJwt(jwt);
            // JWT Send for validation
            IS_JWT_VALID = null;
            queueSender.send(extractedJwt);
            // Waiting for response
            while(IS_JWT_VALID == null && count < MAX_COUNT){
                try {
                    Thread.sleep(100);
                    count++;
                } catch (InterruptedException e) {
                    LOG.error("Error while waiting for JWT validation response: " + e.getMessage());
                }
            }
            // Check response
            if(!IS_JWT_VALID){
                LOG.warn("JWT is not valid !");
                return exchange.getResponse().setComplete().then(Mono.fromRunnable(() -> {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                }));
            }
            // add header with the subejct in it
            String extractedUserEmail =JwtUtils.getSubjectFromToken(extractedJwt);
            LOG.info("The extracted User Email : " + extractedUserEmail);
            ServerHttpRequest req = exchange.getRequest().mutate()
            	.header("User-Email", extractedUserEmail)
            	.build();
            ServerWebExchange exchangeWithNewHeader = exchange.mutate()
            		.request(req)
            		.build();
            return chain.filter(exchangeWithNewHeader);
        };
    }

    /**
     * Store the response of the JWT validation for use in the GatewayFilter
     */
    public static void setIsJwtValid(boolean isJwtValid){
        IS_JWT_VALID = isJwtValid;
    }

    public static class Config{}
}
