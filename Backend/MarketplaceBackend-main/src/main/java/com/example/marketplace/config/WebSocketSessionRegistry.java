package com.example.marketplace.config;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketSessionRegistry {
    private final Map<Long, List<WebSocketSession>> sessionsMap = new ConcurrentHashMap<>();

    public void registerSession(Long userId, WebSocketSession session) {
        sessionsMap.compute(userId, (key, sessions) -> {
            if (sessions == null) {
                sessions = new ArrayList<>();
            }
            sessions.add(session);
            return sessions;
        });
    }

    public void unregisterSession(String userId) {
        List<WebSocketSession> sessions = sessionsMap.remove(userId);
        if (sessions != null) {
            for (WebSocketSession session : sessions) {
                try {
                    session.close();
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        }
    }
}
