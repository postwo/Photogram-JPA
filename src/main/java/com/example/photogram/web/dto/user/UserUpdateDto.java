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
                .name(name)// 이름을 기재 안했으면 공백으로 데이터베이스에 들어가게 된다, validation 체크를 해주면 된다.
                .password(password) //패스워드를 기재 안했으면 공백으로 데이터베이스에 들어가게 된다, validation 체크를 해주면 된다.
                .website(website)
                .bio(bio)
                .phone(phone)
                .gender(gender)
                .build();
    }
}
