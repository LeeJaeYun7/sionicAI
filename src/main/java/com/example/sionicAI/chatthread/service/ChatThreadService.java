package com.example.sionicAI.chatthread.service;

import com.example.sionicAI.chat.domain.Chat;
import com.example.sionicAI.chat.dto.response.ChatResponse;
import com.example.sionicAI.chatthread.domain.ChatThread;
import com.example.sionicAI.chatthread.dto.response.ChatThreadResponse;
import com.example.sionicAI.chatthread.infrastructure.ChatThreadRepository;
import com.example.sionicAI.user.domain.User;
import com.example.sionicAI.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final UserService userService;

    private String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }


    public ChatThread createChatThread(long userId, List<Chat> chatList){
        ChatThread chatThread = ChatThread.of(userId, chatList);
        return chatThreadRepository.save(chatThread);
    }

    public List<ChatThreadResponse> getUserThreadList(String userType){

        List<ChatThread> userThreadList;

        if(userType.equals("ADMIN")){
            userThreadList = chatThreadRepository.findAll();
        }else{
            String email = getCurrentUserEmail();
            User user = userService.getUserByEmail(email);
            long userId = user.getId();
            userThreadList = chatThreadRepository.findByUserId(userId);
        }

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
