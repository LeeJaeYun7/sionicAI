package com.example.sionicAI.chatthread.dto.request;

import com.example.sionicAI.chat.dto.request.ChatRequest;
import lombok.Getter;

@Getter
public class ChatThreadRequest {

    private long userId;
    private ChatRequest chatRequest;
}
