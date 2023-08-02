package com.schrodingdong.apigatewayservice;

import com.schrodingdong.apigatewayservice.configuration.JwtValidationGatewayFilterFactory;
import com.schrodingdong.apigatewayservice.utils.JwtUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ApiGatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayServiceApplication.class, args);
	}

	@Autowired
	private JwtValidationGatewayFilterFactory jwtValidationFilter;

	@Bean
	public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
		GatewayFilter filter = jwtValidationFilter.apply(new JwtValidationGatewayFilterFactory.Config());
		return builder.routes()
				.route(r -> r.path("/auth/**")
						.filters(f -> f
								.rewritePath("/(?<path>.*)", "/api/v1/$\\{path}")
						// .filter(filter) // to use with other routes !
						)
						.uri("lb://AUTHENTICATION-SERVICE"))
				.route(r -> r.path("/quote/**")
						.filters(f -> f
								.rewritePath("/(?<path>.*)", "/api/v1/$\\{path}")
								.filter(filter))
						.uri("lb://QUOTE-MANAGER-SERVICE"))
				.route(r -> r.path("/user/**")
						.filters(f ->f
								.rewritePath("/(?<path>.*)", "/api/v1/$\\{path}")
								.filter(filter))
						.uri("lb://USER-MANAGER-SERVICE"))
				.build();
	}
}
