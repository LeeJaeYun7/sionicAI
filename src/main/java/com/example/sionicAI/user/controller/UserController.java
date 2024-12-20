package com.example.sionicAI.user.controller;

import com.example.sionicAI.user.dto.request.LoginRequest;
import com.example.sionicAI.user.dto.request.SignUpRequest;
import com.example.sionicAI.user.dto.response.LoginResponse;
import com.example.sionicAI.user.dto.response.SignUpResponse;
import com.example.sionicAI.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/v1/user/signup")
    public ResponseEntity<SignUpResponse> signUp(@RequestBody SignUpRequest signUpRequest){
        String email = signUpRequest.getEmail();
        String password = signUpRequest.getPassword();
        String name = signUpRequest.getName();
        String memberType = signUpRequest.getMemberType();

        userService.signUp(email, password, name, memberType);

        return ResponseEntity.status(HttpStatus.OK).body(SignUpResponse.of(true));
    }

    @PostMapping("/api/v1/user/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        LoginResponse loginResponse = userService.login(email, password);

        return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
    }
}
