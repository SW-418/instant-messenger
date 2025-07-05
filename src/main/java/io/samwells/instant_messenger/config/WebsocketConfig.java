package io.samwells.instant_messenger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

import io.samwells.instant_messenger.MessageHandler;

@Configuration
@EnableWebSocket
public class WebsocketConfig implements WebSocketConfigurer {
    private final HandshakeInterceptor handshakeInterceptor;
    
    public WebsocketConfig(HandshakeInterceptor handshakeInterceptor) {
        this.handshakeInterceptor = handshakeInterceptor;
    }
    
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
        .addHandler(webSocketHandler(), "/ws")
        .addInterceptors(handshakeInterceptor);
    }

    @Bean
    public WebSocketHandler webSocketHandler() {
        return new MessageHandler();
    }
}
