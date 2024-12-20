package com.example.sionicAI.chat.infrastructure;

import com.example.sionicAI.chat.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findFirstByUserId(long userId);
    Optional<Chat> findTopByUserIdOrderByCreatedAtDesc(long userId);
}
