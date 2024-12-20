package com.example.sionicAI.chatthread.infrastructure;

import com.example.sionicAI.chatthread.domain.ChatThread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChatThreadRepository extends JpaRepository<ChatThread, Long> {

    List<ChatThread> findByUserId(long userId);

    Optional<ChatThread> findTopByUserIdOrderByCreatedAtDesc(long userId);

    Optional<ChatThread> findByUserIdAndCreatedAt(long userId, LocalDateTime createdAt);
}
