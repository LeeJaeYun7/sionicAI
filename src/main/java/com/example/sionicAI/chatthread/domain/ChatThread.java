package com.example.sionicAI.chatthread.domain;

import com.example.sionicAI.chat.domain.Chat;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table(name="chat_thread")
@NoArgsConstructor
public class ChatThread {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long userId;

    @OneToMany(mappedBy = "chatThread", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chat> chatList;

    private LocalDateTime createdAt;

    @Builder
    public ChatThread(long userId, List<Chat> chatList){
        this.userId = userId;
        this.chatList = chatList;
        this.createdAt = LocalDateTime.now();
    }

    public static ChatThread of(long userId, List<Chat> chatList){
        return ChatThread.builder()
                         .userId(userId)
                         .chatList(chatList)
                         .build();
    }
}
