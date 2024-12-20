package com.example.sionicAI.user.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponse {

    private String token;

    @Builder
    public LoginResponse(String token){
        this.token = token;
    }

    public static LoginResponse of(String token){
        return LoginResponse.builder()
                            .token(token)
                            .build();
    }
}
