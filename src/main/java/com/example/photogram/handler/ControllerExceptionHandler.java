package com.example.photogram.handler;

import com.example.photogram.handler.ex.CustomValidationException;
import com.example.photogram.web.dto.CMRespDto;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController// 응답은 데이터를 리턴할거기 때문에 rest 사용
@ControllerAdvice // ControllerAdvice 를 사용하면 모든 exception 발생 할떄 다 낚아챈다
public class ControllerExceptionHandler {

//    @ExceptionHandler({RuntimeException.class}) //RuntimeException 이 발생하는 모든 exception들을 validationException 이메서드가 가로챈다.
    @ExceptionHandler(CustomValidationException.class)
    //CMRespDto<Map<String,String>> 이거 대신 뭘 넣을지 모르면 그냥 ?를 넣으면 된다 그럼 리턴에 있는 타입을 보고 알아서 타입을 지정해준다
    public CMRespDto<?> validationException(CustomValidationException e) {
//        return e.getMessage(); //메시지를 리턴
    return new CMRespDto<Map<String,String>>(-1,e.getMessage(),e.getErrprMap());
    //e.getErrprMap() 대신 "문자열을 넣고 싶어요" 이렇게 하면 CMRespDto<String> 이렇게 제네릭만 변겨해주면 된다.
    }
}
