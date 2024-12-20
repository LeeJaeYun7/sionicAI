package com.example.sionicAI.user.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpResponse {
    private boolean isSuccess;

    @Builder
    public SignUpResponse(boolean isSuccess){
        this.isSuccess = isSuccess;
    }

    public static SignUpResponse of(boolean isSuccess){
        return SignUpResponse.builder()
                .isSuccess(isSuccess)
                .build();
    }
}
