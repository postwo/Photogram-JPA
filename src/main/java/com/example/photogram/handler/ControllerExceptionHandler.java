package com.example.photogram.handler;

import com.example.photogram.handler.ex.CustomValidationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController// 응답은 데이터를 리턴할거기 때문에 rest 사용
@ControllerAdvice // ControllerAdvice 를 사용하면 모든 exception 발생 할떄 다 낚아챈다
public class ControllerExceptionHandler {

//    @ExceptionHandler({RuntimeException.class}) //RuntimeException 이 발생하는 모든 exception들을 validationException 이메서드가 가로챈다.
    @ExceptionHandler(CustomValidationException.class)
    public Map<String, String> validationException(CustomValidationException e) {
//        return e.getMessage(); //메시지를 리턴
    return e.getErrprMap();
    }
}
