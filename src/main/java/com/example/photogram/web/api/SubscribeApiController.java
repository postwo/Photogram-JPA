package com.example.photogram.web.api;

import com.example.photogram.config.auth.PrincipalDetails;
import com.example.photogram.service.SubscribeService;
import com.example.photogram.web.dto.CMRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SubscribeApiController { //데이터만 응답해주기 위해 사용

    private final SubscribeService subscribeService;

    @PostMapping("/api/subscribe/{toUserId}") // 누구를 구독하겠다 // 구독하는 사람 (로그인한사람) == AuthenticationPrincipal ,구독당하는 사람 PathVariable
    public ResponseEntity<?> subscribe(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable int toUserId) {
         subscribeService.구독하기(principalDetails.getUser().getId(), toUserId);
        return new ResponseEntity<>(new CMRespDto<>(1,"구독하기 성공",null), HttpStatus.OK);
    }

    @DeleteMapping("/api/subscribe/{toUserId}") // 구독 취소하기
    public ResponseEntity<?> unSubscribe(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable int toUserId) {
         subscribeService.구독취소하기(principalDetails.getUser().getId(), toUserId);
        return new ResponseEntity<>(new CMRespDto<>(1,"구독 취소하기 성공",null), HttpStatus.OK);
    }
}
