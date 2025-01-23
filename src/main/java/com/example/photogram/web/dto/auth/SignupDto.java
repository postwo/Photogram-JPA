package com.example.photogram.web.dto.auth;

import com.example.photogram.domain.user.User;
import lombok.Data;

@Data //getter,setter
public class SignupDto {
    private String username;
    private String password;
    private String email;
    private String name;


    public User toEntity() {
        return User.builder() // 4개의 정보를 기반으로 객체를 생성
                .username(username)
                .password(password)
                .email(email)
                .name(name)
                .build();
    }
}
