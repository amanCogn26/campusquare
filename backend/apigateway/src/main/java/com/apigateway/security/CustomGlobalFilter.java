package com.apigateway.security;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomGlobalFilter implements GlobalFilter, Ordered{

        @Autowired
        private JwtTokenProvider jwtTokenProvider;

        @Override
        public Mono<Void> filter(ServerWebExchange exchange,
                                 GatewayFilterChain chain) {


            log.info("GATEWAY FILTER HIT → PATH = {}"
                    ,exchange.getRequest().getPath());

            if (exchange.getRequest().getMethod() == HttpMethod.OPTIONS) {
                return chain.filter(exchange);
            }

            String path = exchange.getRequest().getPath().value();

            log.info("Path is {}",path);

            if (path.startsWith("/auth")) {
                return chain.filter(exchange);
            }
            if (path.startsWith("/ws") || path.startsWith("/chat-ws") || path.startsWith("/notification-ws")) {
                return chain.filter(exchange);
            }

            String authHeader = exchange.getRequest()
                    .getHeaders()
                    .getFirst(HttpHeaders.AUTHORIZATION);

            log.info("auth header is {}",authHeader);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            log.info("true");

            String token = authHeader.substring(7);

            log.info("token {}" , token);

            if (!jwtTokenProvider.isTokenValid(token)) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String role=jwtTokenProvider.extractRole(token);
            Long userId=jwtTokenProvider.extractUserId(token);
            Long collegeId= jwtTokenProvider.extractCollegeId(token);

            log.info("Role: {}, UserId: {}, CollegeId: {}",
                    role, userId, collegeId);

            ServerWebExchange mutatedExchange = exchange.mutate()
                    .request(builder -> builder.headers(headers -> {

                        //header only accept string value

                        headers.set("X-User-Id", String.valueOf(userId));
                        headers.set("X-College-Id",String.valueOf(collegeId));
                        headers.set("X-Role", role);
                    }))
                    .build();

            return chain.filter(mutatedExchange);
        }

        @Override
        public int getOrder() {
            return Ordered.HIGHEST_PRECEDENCE;
        }
}
