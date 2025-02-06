package com.example.photogram.domain.user;

import com.example.photogram.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


//JPA - java persistence API(자바로 데이터를 영구적으로 저장(DB)할 수 있는 api를 제공)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity // 디비에 테이블을 생성
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //번호 증가 전략이 데이터베이스를 따라간다
    private int id;

    @Column(length = 20, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    private String website; //웹사이트
    private String bio; //자기소개
    @Column(nullable = false)
    private String email;
    private String phone;
    private String gender;

    private String profileImageUrl; //사진
    private String role; //권한

    //양방향 매핑 // 한명의 유저는 여러개의 이미지를 만들수 있다.
    //1.mappedBy(==나는 연관관계의 주인이 아니다 그러므로 테이블에 컬럼을 만들지마)에는 이미지 클래스에 있는 변수를 넣어줘야 한다
    //2. User를 select할 때 해당 user id(User 테이블에 있는 userid)로 등록된 image들을 다 가져와 == ex) 1번 사용자가 저장한 이미지를 다 조회해서 가져오라는 의미이다.
    //3. 결론 = mappedBy 를 붙이면 나는 연관관계의 주인이 아니다 연관관계 주인은(지금 여기서 주인은 Image 이다) Image 테이블에 있는 user가 주인이다 그러므로 테이블에 컬럼을 만들지마
    //Lazy = User를 Select할 때 해당 User id로 등록된 image들을 가져오지마 -> 대신 getImages() 메서드의 image들이 호출될때 가져와
    //Eager = User를 Select할 때 해당 User id로 등록된 image들을 전부 Join해서 가져와
    // 유저프로필(마이페이지) 갈때 유저정보도 가져가면서 이미지를 같이 가져올려면 양방향 매핑을 하면된다.
    // 이렇게 양방향으로 하는이유는 프로필(=마이페이지)에서 User 정보를 통해서 이미지 정보도 가지고 오기 위해서다
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY) //양방향매핑
    @JsonIgnoreProperties({"user"})//이거는 쉽게 생각하면 응답할때 무한참조를 방지한다고 생각하면 된다 == 양방향 무한참조 해결방법 //Image 안에 있는 user(getter)를 무시하고 파싱한다 이렇게 하면 org.springframework.http.converter.HttpMessageNotWritableException 이에러를 해결할수 있다
    private List<Image> images; //이미지 여러개  db는 하나의 컬럼에 하나의 데이터만 넣을수 있다 = 그러므로 이부분은 데이터베이스에 만들지 말라고 명시해줘야 한다

    private LocalDateTime createDate;

    @PrePersist //데이터베이스 인서트되기 직전에 실행
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
