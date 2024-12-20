package com.example.sionicAI.chatthread.dto.response;

import com.example.sionicAI.chat.dto.response.ChatResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ChatThreadResponse {

    private long userId;
    private List<ChatResponse> chatList;
    private LocalDateTime createdAt;

    @Builder
    public ChatThreadResponse(long userId, List<ChatResponse> chatList, LocalDateTime createdAt){
        this.userId = userId;
        this.chatList = chatList;
        this.createdAt = createdAt;
    }
    public static ChatThreadResponse of(long userId, List<ChatResponse> chatList, LocalDateTime createdAt){
        return ChatThreadResponse.builder()
                                 .userId(userId)
                                 .chatList(chatList)
                                 .createdAt(createdAt)
                                 .build();
    }
}
