package com.example.sionicAI.chat.service;

import com.example.sionicAI.chat.domain.Chat;
import com.example.sionicAI.chat.dto.response.ChatResponse;
import com.example.sionicAI.chat.infrastructure.ChatRepository;
import com.example.sionicAI.chatthread.domain.ChatThread;
import com.example.sionicAI.chatthread.dto.response.ChatThreadResponse;
import com.example.sionicAI.chatthread.infrastructure.ChatThreadRepository;
import com.example.sionicAI.chatthread.service.ChatThreadService;
import com.example.sionicAI.openai.service.OpenAIService;
import com.example.sionicAI.user.domain.User;
import com.example.sionicAI.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final UserService userService;
    private final OpenAIService openAIService;
    private final ChatThreadService chatThreadService;
    private final ChatRepository chatRepository;

    public boolean isTimeToCreateNewThread(long userId) {
        Optional<Chat> lastChat = chatRepository.findTopByUserIdOrderByCreatedAtDesc(userId);

        if (lastChat.isEmpty()) {
            return true;
        }

        LocalDateTime lastQuestionTime = lastChat.get().getCreatedAt();
        LocalDateTime now = LocalDateTime.now();

        System.out.println("lastQuestionTime");
        System.out.println(lastQuestionTime);
        System.out.println("now");
        System.out.println(now);

        long minutesBetween = Duration.between(lastQuestionTime, now).toMinutes();

        return minutesBetween >= 30;
    }

    public ChatThreadResponse createChat(String email, String question, boolean isStreaming, String model){

        User user = userService.getUserByEmail(email);
        long userId = user.getId();

        String answer = openAIService.generateAnswer(question, isStreaming, model);

        Chat chat = Chat.of(userId, question, answer);

        // 새로운 스레드 생성
        if (isTimeToCreateNewThread(userId)) {
            List<Chat> chatList = new ArrayList<>();
            chatList.add(chat);

            ChatThread chatThread = chatThreadService.createChatThread(userId, chatList);

            chat.updateChatThread(chatThread);
            chatRepository.save(chat);

            ChatResponse chatResponse = ChatResponse.of(question, answer);
            List<ChatResponse> chatResponseList = new ArrayList<>();
            chatResponseList.add(chatResponse);

            return ChatThreadResponse.of(userId, chatResponseList, chatThread.getCreatedAt());

        } else {
            Optional<ChatThread> lastChatThread = chatThreadService.getLastChatThread(userId);

            chat.updateChatThread(lastChatThread.get());
            chatRepository.save(chat);

            List<Chat> chatList = lastChatThread.get().getChatList();
            chatList.add(chat);

            List<ChatResponse> chatResponseList = chatList.stream()
                                                          .map(existingChat -> ChatResponse.of(existingChat.getQuestion(), existingChat.getAnswer()))
                                                          .collect(Collectors.toList());

            return ChatThreadResponse.of(userId, chatResponseList, lastChatThread.get().getCreatedAt());
        }
    }
}
