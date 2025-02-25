package com.example.photogram.domain.liks;

import com.example.photogram.domain.image.Image;
import com.example.photogram.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(
        uniqueConstraints={ //like를 중복해서 못하게 하기 위해 작성
                @UniqueConstraint(
                        name = "likes_uk", //이거는 유니크 제약조건이름이다
                        columnNames={"imageId","userId"} //이거는 유니크 제약조건을 여러개 걸때 사용, 실제 데이터베이스에 컬럼명을 걸어야한다.
                        //"imageId","userId" 두개는 중복 될수없다 이거를 위해서 이거를 사용
                )
        }
)
public class Likes { //likes로 하는 이유는 mariadb나 mysql이 like가 키워드여서 안 만들어지기 때문에 이렇게 작성

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //이미지에서 like를 참조 likes 에서 이미지 참조 이렇게 되서 무한참된다 그래서 지금 이미지를 가지고 오다가 마는 현상이 발생
    // 1:n : like many이다
    @JoinColumn(name = "imageId") //데이터베이스에 만들어질때 imageid로 생성
    @ManyToOne //manytoone은 기본전략이 eager전략이다 ,onetomany 기본전략은 lazy전략이다
    private Image image; //좋아요할 이미지 //하나의 이미지는 좋아요를 여러개 , 하나의 좋아요를 여러가지 이미지에 할수 있다는 말이 안된다 -> 하나의 좋아요는 하나의 이미지

    @JsonIgnoreProperties({"images"}) // likes 안의 images만 안나오면 이미지가 안나오는걸 해결할수 있다
    @JoinColumn(name = "userId") //데이터베이스에 만들어질때 userId로 생성
    @ManyToOne
    private User user; //누가 좋아요를 했는지

    // 네이티브 쿼리르 사용하면 PrePersist이거는 동작을 안하기 때문에 날짜가 안들어간거다 그러므로 지워도 되고 주석 처리 해도 상관없다
    private LocalDateTime createDate;

    // 네이티브 쿼리르 사용하면 PrePersist이거는 동작을 안하기 때문에 날짜가 안들어간거다 그러므로 지워도 되고 주석 처리 해도 상관없다
    @PrePersist //데이터베이스 인서트되기 직전에 실행 //이거는 jpa save등을 사용할때 적용 네이티브쿼리를 사용하면 적용이 안되다.
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
