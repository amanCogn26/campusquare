package com.notificationservice.security;

import com.notificationservice.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthChannelInterceptor
        implements ChannelInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Message<?> preSend(
            Message<?> message,
            MessageChannel channel) {

        log.info("message: {}",message);
        log.info("message: {}",channel);

        log.info("enter into the authchannelinterceptor");

        StompHeaderAccessor accessor =
                StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(
                accessor.getCommand())) {

            String auth =
                    accessor.getFirstNativeHeader(
                            "Authorization");

            log.info("auth of websocket {}",auth);

            if(auth==null || !auth.startsWith("Bearer ")){
                throw new IllegalArgumentException("Missing Authorization header in the web connection");
            }

            String token = auth.substring(7);

            log.info(token);

            if (!jwtTokenProvider.isTokenValid(token)) {
                throw new IllegalArgumentException("Invalid JWT");
            }

            Long userId = jwtTokenProvider.extractUserId(token);
            String role = jwtTokenProvider.extractRole(token);
            Long collegeId= jwtTokenProvider.extractCollegeId(token);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                            userId,
                            null,
                    List.of(
                            new SimpleGrantedAuthority(role)
                    )
            );

            accessor.setUser(authentication);

            accessor.getSessionAttributes()
                    .put("collegeId", collegeId);

        }

        return message;
    }
}