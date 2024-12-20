package com.example.sionicAI.chat.domain;

import com.example.sionicAI.chatthread.domain.ChatThread;
import com.example.sionicAI.user.domain.Role;
import com.example.sionicAI.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "chat")
@NoArgsConstructor
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long userId;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private String answer;

    @ManyToOne
    @JoinColumn(name = "chat_thread_id")
    private ChatThread chatThread;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public Chat(long userId, String question, String answer){
        this.userId = userId;
        this.question = question;
        this.answer = answer;
        this.createdAt = LocalDateTime.now();
    }

    public static Chat of(long userId, String question, String answer){
        return Chat.builder()
                    .userId(userId)
                    .question(question)
                    .answer(answer)
                    .build();
    }

    public void updateChatThread(ChatThread chatThread){
        this.chatThread = chatThread;
    }
}
