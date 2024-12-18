package com.example.gateway.filter;

import com.example.gateway.util.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Predicate;

@Component
public class JwtAuthenticationFilter implements GatewayFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        final List<String> apiEndpoints = List.of("/v1/auth/login", "/v1/auth/register", "/eureka");

        // if the incoming request (r) is not one of the apiEndpoints then auth token is not required
        Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpoints.stream()
                .noneMatch(uri -> r.getURI().getPath().contains(uri));

        // if uri is not one of login/register/eureka, means auth is required (true)
        if (isApiSecured.test(request)) {

            // if auth is missing, error response
            if (authMissing(request)) {
                return onError(exchange);
            }

            // get the token from header
            String token = request.getHeaders().getOrEmpty("Authorization").get(0);

            // separate token from 'Bearer'
            if (token != null && token.startsWith("Bearer")) {
                token = token.substring(7);
            }

            try {
                // validate token
                jwtUtil.validateToken(token);
            }
            catch (Exception e) {
                onError(exchange);
            }
            
        }

        return chain.filter(exchange);
    }

    private boolean authMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }

    private Mono<Void> onError(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }
}
