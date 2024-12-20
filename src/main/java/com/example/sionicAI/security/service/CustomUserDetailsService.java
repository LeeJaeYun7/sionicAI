package com.example.sionicAI.security.service;

import com.example.sionicAI.user.domain.User;
import com.example.sionicAI.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        User foundUser = user.get();

        Collection<SimpleGrantedAuthority> authorities = foundUser.getRole() != null
                ? Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + foundUser.getRole().name()))  // "ROLE_" 접두사를 붙여 권한 생성
                : Collections.emptyList();

        return new org.springframework.security.core.userdetails.User(
                foundUser.getEmail(),
                foundUser.getPassword(),
                authorities
        );
    }
}
