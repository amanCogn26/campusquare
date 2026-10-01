package com.chatservice.config;

import com.chatservice.security.AuthChannelInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig
        implements WebSocketMessageBrokerConfigurer {

    private final AuthChannelInterceptor authChannelInterceptor;

    //enabling the interceptor for authentication of the websocket request
    @Override
    public  void configureClientInboundChannel(ChannelRegistration channelRegistration){

        channelRegistration.interceptors(authChannelInterceptor);
    }


    //used to established the connection of websocket with the url
    @Override
    public void registerStompEndpoints(
            StompEndpointRegistry registry) {

        registry.addEndpoint("/chat-ws")
                .setAllowedOriginPatterns("*");
    }


    //registration of broker with the topics
    @Override
    public void configureMessageBroker(
            MessageBrokerRegistry registry) {

        //telling that this is the
        registry.enableSimpleBroker("/topic");

        registry.setApplicationDestinationPrefixes("/app");
    }
}