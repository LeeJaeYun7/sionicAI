package com.example.sionicAI.user.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor
public class User {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     @Column(nullable = false, unique = true)
     private String email;

     @Column(nullable = false)
     private String password;

     @Column(nullable = false)
     private String name;

     @Column(nullable = false)
     private LocalDateTime createdAt;

     @Enumerated(EnumType.STRING)
     @Column(nullable = false)
     private Role role;

     @Builder
     public User(String email, String password, String name, Role role){
          this.email = email;
          this.password = password;
          this.name = name;
          this.createdAt = LocalDateTime.now();
          this.role = role;
     }

     public static User of(String email, String password, String name, String memberType){
             Role role = Role.valueOf(memberType.toUpperCase());

             return User.builder()
                        .email(email)
                        .password(password)
                        .name(name)
                        .role(role)
                        .build();
     }
}
