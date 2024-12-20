package com.example.sionicAI.user.service;

import com.example.sionicAI.user.domain.User;
import com.example.sionicAI.user.dto.response.LoginResponse;
import com.example.sionicAI.user.infrastructure.UserRepository;
import com.example.sionicAI.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

    public void signUp(String email, String password, String name, String memberType){
        String hashedPassword = passwordEncoder.encode(password);
        User user = User.of(email, hashedPassword, name, memberType);
        userRepository.save(user);
    }

    public LoginResponse login(String email, String password){
        Optional<User> user = userRepository.findByEmail(email);

        if(user.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        if (passwordEncoder.matches(password, user.get().getPassword())) {
            String token = jwtTokenUtil.generateToken(email);
            return LoginResponse.of(token);
        } else {
            throw new RuntimeException("Invalid password");
        }
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email).get();
    }
}
