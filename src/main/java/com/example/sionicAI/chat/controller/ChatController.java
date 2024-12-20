package com.example.sionicAI.chat.controller;

import com.example.sionicAI.chat.dto.request.ChatRequest;
import com.example.sionicAI.chat.service.ChatService;
import com.example.sionicAI.chatthread.dto.response.ChatThreadResponse;
import com.example.sionicAI.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final JwtTokenUtil jwtUtil;
    private final ChatService chatService;

    @PostMapping("/api/v1/chat/create")
    public ResponseEntity<ChatThreadResponse> createChat(@RequestBody ChatRequest chatRequest, @RequestHeader("Authorization") String authorizationHeader){
        String question = chatRequest.getQuestion();

        String token = authorizationHeader.replace("Bearer ", "");
        String email = jwtUtil.getEmailFromToken(token);

        boolean isStreaming = chatRequest.isStreaming();
        String model = chatRequest.getModel();

        ChatThreadResponse chatThreadResponse = chatService.createChat(email, question, isStreaming, model);

        return ResponseEntity.status(HttpStatus.OK).body(chatThreadResponse);
    }
}
