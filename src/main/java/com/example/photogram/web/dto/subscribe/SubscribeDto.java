package com.example.photogram.web.dto.subscribe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubscribeDto { //구독 모달창

    private int userId; //누군가의 정보 구독할 취소 할 id값
    private String username;
    private String profileImageUrl; //모달창 구독한 유저 사진
    private Integer subscribeState; //구독상태
    private Integer equalUserState; //로그인한 사용자의 동일인 여부
}
