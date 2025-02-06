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


    /* JPA는 User 엔티티의 **기본 키(PK)**가 id라는 것을 자동으로 인식합니다.
       따라서 @JoinColumn(name = "userId")을 선언하면,
       userId라는 컬럼을 Image 테이블에서 외래 키(FK)로 생성하고,
       User 엔티티의 id 값을 이 FK로 저장하게 됩니다.
       즉, "userId 컬럼을 만들라"는 것은 JPA에게 "User 엔티티의 PK(id)를 FK로 사용하라"는 의미가 됩니다.
       따라서 User 엔티티의 id 값이 자동으로 저장 */
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

    // sout 처리할때 테스트 해보기 위해 오버라이딩함 지금은 필요없음
    // 오브젝트를 콘솔에 출력할 때 문제가 될수 있어서 user 부분을 출력되지 않게 함
//    @Override
//    public String toString() {
//        return "Image{" +
//                "id=" + id +
//                ", caption='" + caption + '\'' +
//                ", postImageUrl='" + postImageUrl + '\''        //+ ", user=" + user  //여기서 user를 출력 할떄 무한참조가 발생 그러므로 주석 처리하거나 지워버리면 에러발생이 안한다
//                + ", createDate=" + createDate +
//                '}';
//    }
}
