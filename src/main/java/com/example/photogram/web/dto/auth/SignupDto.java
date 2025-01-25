package com.example.photogram.web.dto.auth;

import com.example.photogram.domain.user.User;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

@Data //getter,setter
public class SignupDto {
    //https://bamdule.tistory.com/35 (@Valid 어노테이션 종류)

    @Max(20)
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String email;
    @NotBlank
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
