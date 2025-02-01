package com.example.photogram.service;

import com.example.photogram.domain.subscribe.SubscribeRepository;
import com.example.photogram.handler.ex.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;


    @Transactional
    public void 구독하기 (int fromUserid,int toUserid){ //int 여서 Subscribe객체를 못만든다 그이유는 타입이 User오브젝트이기 떄문이다 그러므로 네이티브 쿼리 사용하기
        try {
            subscribeRepository.mSubscribe(fromUserid,toUserid);
        }catch (Exception e){
            throw new CustomApiException("이미 구독을 하였습니다.");
        }

    }

    @Transactional
    public void 구독취소하기 (int fromUserid,int toUserid){
        subscribeRepository.mUnSubscribe(fromUserid, toUserid);
    }
}
