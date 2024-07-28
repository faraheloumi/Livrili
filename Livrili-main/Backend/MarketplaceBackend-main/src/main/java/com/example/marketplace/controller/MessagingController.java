package com.example.marketplace.controller;

import com.example.marketplace.model.Conversation;
import com.example.marketplace.model.Message;
import com.example.marketplace.service.CategoryService;
import com.example.marketplace.service.MessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin(origins = "http://localhost:4200")
public class MessagingController {
    @Autowired
    MessagingService messagingService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @MessageMapping("/message")
    public void sendMessage(
            @Payload Message message
    ) {
        messagingTemplate.convertAndSend("/conversation/"+message.getConversation().getId(),message);
        messagingService.saveMessage(message);
    }
    @PostMapping("/api/v1/conversation")
    @ResponseBody
    public Conversation getConversation(@RequestBody Conversation conversation, Authentication authentication){
        Jwt jwt=(Jwt) authentication.getPrincipal();
        Long buyerId = (Long) jwt.getClaims().get("id");
        return messagingService.getOrCreateConversation(buyerId, conversation.getSeller().getId());
    }
    @GetMapping("/api/v1/conversation")
    @ResponseBody
    public List<Conversation> getConversationsByUser(Authentication authentication){
        Jwt jwt=(Jwt) authentication.getPrincipal();
        Long id = (Long) jwt.getClaims().get("id");
        return messagingService.getConversationsByUserId(id);
    }
}