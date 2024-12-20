package com.example.sionicAI.chatthread.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class DeleteChatThreadRequest {
    private LocalDateTime createdAt;
}
