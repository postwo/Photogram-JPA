package com.example.photogram.web.dto.user;

import com.example.photogram.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfileDto {

    // int 타입으로 하면 자바스크립트로 처리하기가 뭔가 에매하다
    private boolean pageOwnerState; // 마이페이지에 주인인지 아닌지 여부를 가르킨다 //jsp 에서 변수명 앞에다가 is를 붙이면 파싱이 안되다
    private int imageCount; // 이미지 수
    private boolean subscribeState; // 구독을 한 상태인지
    private int subscribeCount;
    private User user;
}
