package com.interactionservice.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class InternalServiceAuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String internalKey = request.getHeader("X-Internal-Service-Key");
        String gatewayRole = request.getHeader("X-Role");
        String collegeId = request.getHeader("X-College-Id");
        String userId = request.getHeader("X-User-Id");

        log.info("collegeId is: {}" ,collegeId);
        log.info("userID is: {}" ,userId);
        log.info("gatewayRole is: {}" ,gatewayRole);
        log.info("internalKey is: {}" ,internalKey);

        if ("MySecretKey123".equals(internalKey)) {

            var auth = new UsernamePasswordAuthenticationToken("internal", null,
                    List.of(new SimpleGrantedAuthority("ROLE_SERVICE")));

            SecurityContextHolder.getContext().setAuthentication(auth);

        } else if (gatewayRole != null) {

            String finalRole = gatewayRole.startsWith("ROLE_") ? gatewayRole : "ROLE_" + gatewayRole;

            var auth = new UsernamePasswordAuthenticationToken(userId, null,
                    List.of(new SimpleGrantedAuthority(finalRole)));

            SecurityContextHolder.getContext().setAuthentication(auth);
        } else {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        chain.doFilter(request, response);
    }
}