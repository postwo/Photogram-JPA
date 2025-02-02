package com.example.photogram.domain.image;

import com.example.photogram.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
// 1. 한명의 유저는 여러이미지를 업로드 할수 있다 , 2.하나의 이미지는 한명의 유저가 만들수 있다(2명의 유저가 한개의 이미지를 동시에 올릴수는 없다.)
// 결론적으로 관계는 1 :n ,1:1 이기 때문에 세로로 보면 1보다 n이 크기때문에 1:n의 관계가 된다. 그러므로 유저가 1이다 이미지가 n이고
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String caption; // ex) 오늘 나 너무 피곤해
    private String postImageUrl;// 사진을 전송받ㅇ아서 그 사진을 서버에 특정 폴더에 저장 - DB에 그 저장된 경롤 insert = 사진을 db에 저잔안하고 서버(= 내컴퓨터)에 저장


    @ManyToOne
    @JoinColumn(name = "userId")
    private User user; //누가 업로드 했는지 (유저정보) //오브젝트 자체는 db에 저장을 할수 없기 때문에 fk로 저장된다

    // 생성할거 = 이미지 좋아요
    // 생성할거 = 댓글

    private LocalDateTime createDate;

    @PrePersist //데이터베이스 인서트되기 직전에 실행 //이거는 jpa save등을 사용할때 적용 네이티브쿼리를 사용하면 적용이 안된다.
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
