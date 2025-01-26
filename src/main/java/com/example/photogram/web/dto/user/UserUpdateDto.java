package com.example.photogram.web.dto.user;

import com.example.photogram.domain.user.User;
import lombok.Data;

@Data
public class UserUpdateDto {

    private String name; //필수
    private String password; //필수

    private String website;
    private String bio;
    private String phone;
    private String gender;

    public User toEntity() { // Entity 객체로 변환하는 역할
        return User.builder() // 4개의 정보를 기반으로 객체를 생성
                .name(name)
                .password(password)
                .website(website)
                .bio(bio)
                .phone(phone)
                .gender(gender)
                .build();
    }
}
