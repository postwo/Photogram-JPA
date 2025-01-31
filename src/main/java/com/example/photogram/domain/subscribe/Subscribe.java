package com.example.photogram.domain.subscribe;

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
@Table(
        name="subscribe",
        uniqueConstraints={ //구독을 중복해서 못화게 하기 위해 작성
                @UniqueConstraint(
                        name = "subscribe_uk", //이거는 유니크 제약조건이름이다
                        columnNames={"fromUserId","toUserId"} //이거는 유니크 제약조건을 여러개 걸때 사용, 실제 데이터베이스에 컬럼며을 겅어야한다.
                )
        }
)
public class Subscribe { //many

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //@Column( unique = true) 이거는 유니크 제약조건을 하나만 걸떄만 이렇게 사용
    @JoinColumn(name = "fromUserId") //카멜표기법으로 변경 , 이렇게 컬럼명을 만들어
    @ManyToOne
    private User fromUser;  // ~~로부터  (1)  구독하는 유저

    @JoinColumn(name = "toUserId")
    @ManyToOne
    private User toUser; // ~~를  (3) 구독 받는 유저


    private LocalDateTime createDate;

    @PrePersist //데이터베이스 인서트되기 직전에 실행 //이거는 jpa save등을 사용할때 적용 네이티브쿼리를 사용하면 적용이 안되단.
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
