package com.example.sionicAI.chat.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatResponse {

    private String question;
    private String answer;


    @Builder
    public ChatResponse(String question, String answer){
        this.question = question;
        this.answer = answer;
    }
    public static ChatResponse of(String question, String answer){
        return ChatResponse.builder()
                            .question(question)
                            .answer(answer)
                            .build();
    }
}
