package com.example.sionicAI.chatthread.controller;

import com.example.sionicAI.chatthread.dto.request.DeleteChatThreadRequest;
import com.example.sionicAI.chatthread.dto.response.ChatThreadResponse;
import com.example.sionicAI.chatthread.service.ChatThreadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatThreadController {

    private final ChatThreadService chatThreadService;

    @GetMapping("/api/v1/chatThread")
    public ResponseEntity<List<ChatThreadResponse>> getUserThreadList(@RequestParam("userId") long userId){
        List<ChatThreadResponse> userThreadList = chatThreadService.getUserThreadList(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userThreadList);
    }

    @DeleteMapping("/api/v1/chatThread")
    public ResponseEntity<Boolean> deleteChatThread(@RequestBody DeleteChatThreadRequest deleteChatThreadRequest){
           long userId = deleteChatThreadRequest.getUserId();
           LocalDateTime createdAt = deleteChatThreadRequest.getCreatedAt();

           boolean result = chatThreadService.deleteChatThread(userId, createdAt);

           return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
