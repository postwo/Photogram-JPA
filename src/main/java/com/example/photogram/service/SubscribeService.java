package com.example.photogram.service;

import com.example.photogram.domain.subscribe.SubscribeRepository;
import com.example.photogram.handler.ex.CustomApiException;
import com.example.photogram.web.dto.subscribe.SubscribeDto;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final EntityManager em; //Repository는 entitymanager를 구현해서 만들어져 있는 구현체이다

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


    @Transactional(readOnly = true) // 조회전용
    public List<SubscribeDto> 구독리스트(int principalId, int pageUserId) {

        // 쿼리 준비
        //네이티브쿼리를 작성하지 않고 이렇게 서비스에서 작성하는 이유 = 네이티브쿼리로 작성을 할수 있는데 작동이 안된다 리턴되는 컬럼 값들이 subscribe타입이 아니여서 이렇게 서비스로 작성한는거다
        // ex) u.profileImageUrl , "  이렇게 다 작성하고 마지막에 한칸을 띄워줘야 한다 안그러면 그다음꺼 하고 붙어서 동작하기 때문에 동작이 안된다 하지만 맨 마지막 쿼리는 굳이 띄울필요 없다 왜냐하면 마지막이기 때문이다
        // Subscribe에 있는 컬럼명이 없을경우 repository로 해결 불가능 이렇게 없는 컬럼만 가지고 올려면 이렇게 작성해서 가지고와야 한다
        // 결론(중요) : JPA Repository에서는 엔티티와 매핑된 필드만을 직접 다룰 수 있기 때문에, 복잡한 계산이나 다중 테이블 조인과 같은 작업은 서비스 계층에서 네이티브 쿼리로 해결하는 것이 필요
        StringBuffer sb = new StringBuffer();
        sb.append("select  u.id, u.username, u.profileImageUrl, ");
        sb.append("if((select 1 from subscribe where fromuserid = ? and touserid =u.id),1,0) subscribestate, "); // 여기 ? 는 principalId 로그인한 유저 아이디
        sb.append("if((? = u.id),1,0) equalUserState "); //여기 ?는 equalUserState  여기는 로그인한 아이디
        sb.append("from user u Inner join subscribe s ");
        sb.append("on u.id = s.touserid ");
        sb.append("where s.fromuserid = ?"); // 세미콜론 첨부하면 안됨 //fromuserid 현재 페이지 주인 아이디 이다

        // ? 매핑
        // 쿼리 완성
        Query query =em.createNativeQuery(sb.toString())
                .setParameter(1,principalId)
                .setParameter(2,principalId)
                .setParameter(3,pageUserId);

        //쿼리 실행(qlrm 라이브러리 필요 = dto에 db결과를 매핑하기 위해서 )
        // 한건을 응답받고 싶으면 uniqueResult를 사용
        // 여러건을 응답받고 싶으면 list를 사용
        JpaResultMapper result = new JpaResultMapper(); //qlrm = 이거는 스프링에 내장 되어 있는게 아니다  이거는 라이브러리 다운받아야 한다  == 데이터베이스에서 리턴된결과를 자바클래스에 매핑해주는 라이브러리
        List<SubscribeDto> subscribeDtos = result.list(query,SubscribeDto.class); //SubscribeDto.class 이걸 넣은 이유는 뭐로 리턴받을건지 명시해준는거다

        return subscribeDtos;
    }


}
