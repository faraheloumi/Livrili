package com.example.marketplace.config;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.StompSubProtocolHandler;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        //StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        //String username = (String) headerAccessor.getSessionAttributes().get("username");
        System.out.println("user disconnected");
    }
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        //StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        //String username = (String) headerAccessor.getSessionAttributes().get("username");
        StompSubProtocolHandler stompSubProtocolHandler=(StompSubProtocolHandler) event.getSource();
        System.out.println("user connected");
    }

}
