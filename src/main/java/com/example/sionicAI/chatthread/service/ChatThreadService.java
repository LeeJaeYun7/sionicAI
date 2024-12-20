package com.example.sionicAI.chatthread.service;

import com.example.sionicAI.chat.domain.Chat;
import com.example.sionicAI.chat.dto.response.ChatResponse;
import com.example.sionicAI.chatthread.domain.ChatThread;
import com.example.sionicAI.chatthread.dto.response.ChatThreadResponse;
import com.example.sionicAI.chatthread.infrastructure.ChatThreadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatThreadService {

    private final ChatThreadRepository chatThreadRepository;

    public ChatThread createChatThread(long userId, List<Chat> chatList){
        ChatThread chatThread = ChatThread.of(userId, chatList);
        return chatThreadRepository.save(chatThread);
    }

    public List<ChatThreadResponse> getUserThreadList(long userId){

        List<ChatThread> userThreadList = chatThreadRepository.findByUserId(userId);

        return userThreadList.stream()
                .map(chatThread -> {

                    List<Chat> chatList = chatThread.getChatList();
                    List<ChatResponse> chatResponseList = new ArrayList<>();

                    for(Chat chat: chatList){
                        chatResponseList.add(ChatResponse.of(chat.getQuestion(), chat.getAnswer()));
                    }

                    return ChatThreadResponse.of(chatThread.getUserId(), chatResponseList, chatThread.getCreatedAt());
                })
                .collect(Collectors.toList());
    }
    public Optional<ChatThread> getLastChatThread(long userId){
        return chatThreadRepository.findTopByUserIdOrderByCreatedAtDesc(userId);
    }

    public boolean deleteChatThread(long userId, LocalDateTime createdAt){
        Optional<ChatThread> chatThread = chatThreadRepository.findByUserIdAndCreatedAt(userId, createdAt);

        if (chatThread.isPresent()) {
            chatThreadRepository.delete(chatThread.get());
            return true;
        }

        return false;
    }
}
