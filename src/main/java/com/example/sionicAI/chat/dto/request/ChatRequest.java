package com.example.sionicAI.chat.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatRequest {
    private String question;
    private boolean isStreaming;
    private String model;
}
