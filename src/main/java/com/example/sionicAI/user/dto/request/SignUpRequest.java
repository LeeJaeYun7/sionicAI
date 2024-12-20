package com.example.sionicAI.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequest {

    private String email;
    private String password;
    private String name;
    private String memberType;
}
