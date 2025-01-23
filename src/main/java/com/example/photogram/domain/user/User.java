package com.example.photogram.domain.user;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


//JPA - java persistence API(자바로 데이터를 영구적으로 저장(DB)할 수 있는 api를 제공)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity // 디비에 테이블을 생성
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //번호 증가 전략이 데이터베이스를 따라간다
    private Long id;

    private String username;
    private String password;

    private String name; //웹사이트
    private String website;
    private String bio; //자기소개
    private String email;
    private String phone;
    private String gender;

    private String profileImageUrl; //사진
    private String role; //권한

    private LocalDateTime createDate;

    @PrePersist //데이터베이스 인서트되기 직전에 실행
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
