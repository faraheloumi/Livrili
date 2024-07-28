package com.example.marketplace.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class WebSocketHandler extends TextWebSocketHandler {
    @Autowired
    WebSocketSessionRegistry socketSessionRegistry;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("added session");
        socketSessionRegistry.registerSession(1L,session);

    }
}
