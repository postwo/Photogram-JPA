package com.example.photogram.handler;

import com.example.photogram.handler.ex.CustomApiException;
import com.example.photogram.handler.ex.CustomValidationApiException;
import com.example.photogram.handler.ex.CustomValidationException;
import com.example.photogram.util.Script;
import com.example.photogram.web.dto.CMRespDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController// 응답은 데이터를 리턴할거기 때문에 rest 사용
@ControllerAdvice // ControllerAdvice 를 사용하면 모든 exception 발생 할떄 다 낚아챈다
public class ControllerExceptionHandler {

    //1. 첫번째 방법
//    @ExceptionHandler({RuntimeException.class}) //RuntimeException 이 발생하는 모든 exception들을 validationException 이메서드가 가로챈다.
//    @ExceptionHandler(CustomValidationException.class)
//    //CMRespDto<Map<String,String>> 이거 대신 뭘 넣을지 모르면 그냥 ?를 넣으면 된다 그럼 리턴에 있는 타입을 보고 알아서 타입을 지정해준다
//    public CMRespDto<?> validationException(CustomValidationException e) {
//        return e.getMessage(); //메시지를 리턴
//    return new CMRespDto<Map<String,String>>(-1,e.getMessage(),e.getErrprMap());
//    //e.getErrprMap() 대신 "문자열을 넣고 싶어요" 이렇게 하면 CMRespDto<String> 이렇게 제네릭만 변겨해주면 된다.
//    }

    // 2. 두번째 방법 이거는 얼럿창을 반환하기 위해 사용
    //    @ExceptionHandler({RuntimeException.class}) //RuntimeException 이 발생하는 모든 exception들을 validationException 이메서드가 가로챈다.
    @ExceptionHandler(CustomValidationException.class)
    public String validationException(CustomValidationException e) {
        // CMRespDTO, Script 비교
        //1. 클라이언트에게 응답할때는 Script 좋음 = 이거는 클라이언트가 받음(브라우저)
        //2. Ajax 통신 - CMRespDto 좋음 = 개발자가 받음
        //3. Android 통신 - CMRespDto 좋음 = 개발자가 받음
       if (e.getErrprMap() == null){
           return Script.back(e.getMessage());
       }else{
           return Script.back(e.getErrprMap().toString());
       }
    }



    //이거는 ajax통신이기 때문에 데이터로 응답
    @ExceptionHandler(CustomValidationApiException.class)
    public ResponseEntity<?> validationApiException(CustomValidationApiException e) {
        System.out.println("나실행중");
        return new ResponseEntity<CMRespDto<?>>(new CMRespDto<>(-1,e.getMessage(),e.getErrprMap()),HttpStatus.BAD_REQUEST);
    }

    //이거는 ajax통신이기 때문에 데이터로 응답
    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<?> apiException(CustomApiException e) {
        System.out.println("나실행중");
        return new ResponseEntity<CMRespDto<?>>(new CMRespDto<>(-1,e.getMessage(),null),HttpStatus.BAD_REQUEST);
    }
}
