package io.samwells.instant_messenger;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Component
public class UserHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        // We use a User-Id header to identify the user, this would normally come from a JWT or similar
        var userIdHeaders = request.getHeaders().get("User-Id");
        if (userIdHeaders == null || userIdHeaders.isEmpty()) {
            response.setStatusCode(HttpStatus.BAD_REQUEST);
            response.getBody().write("Missing User-Id header".getBytes()); 
            return false;
        }
        var userId = Integer.parseInt(userIdHeaders.get(0));
        attributes.put("userId", userId);
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {}
}
