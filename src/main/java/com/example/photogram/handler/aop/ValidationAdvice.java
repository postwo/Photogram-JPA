package com.example.photogram.handler.aop;

import com.example.photogram.handler.ex.CustomValidationApiException;
import com.example.photogram.handler.ex.CustomValidationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

// 이거 적용 되는거는 그냥 페이지 이동하면 알아서 적용된다
@Component //RestController, service 모든 것들이 component를 상속해서 만들어져 있음 = 설정파일이 아니다 그냥 잘모르겠다 싶으면 component 사용
@Aspect // aop 처리를 위해 달아야한다
public class ValidationAdvice { //공통 기능을 넣을거다

    // before 는 어떤 함수가 실행되기 직전에 먼저 실행
    // after  는 어떤 함수가 끝난후에 실행 하고 싶을 때사용
    // Around 는 함수가 실행되기 전과 후 모두에 적용
    // api.*Controller = api 밑의 controller 롤 끝나는 모든 얘들 , .*(..)) controller 안의 있는 모든 메서드를 선택 ,*(..)) 여기서 ..은 어떤 파라미터든 상관없다는 뜻이다

    //전처리
    @Around("execution(* com.example.photogram.web.api.*Controller.*(..))")
    public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable { //ProceedingJoinPoint는 컨트롤러에 있는 메서드 내부에 정보에 모두 접근할수 있게 하는 파라미터이다

        // profile 함수보다 먼저 실행 = 컨트롤러에 있는 함수보다 이게 먼저 실행된다
        // proceedingJoinPoint => profile 함수의 모든 곳에 접근할 수 있는 변수
        // return proceedingJoinPoint.proceed() 이부분을 실행하고 나서 profile 함수가 실행된다

        //System.out.println("web api 컨트롤러 =====================");
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) { //  for (Object arg : args)==컨트롤러에 있는 매개변수를 가지고 오는거다
//            System.out.println(arg);
            if (arg instanceof BindingResult) { // arg가 BindingResult 타입의 객체인지 확인하는 조건문
                BindingResult bindingResult = (BindingResult) arg;

                if (bindingResult.hasErrors()) {
                    Map<String, String> errorMap = new HashMap<>();

                    for (FieldError error : bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage());
                    }
                    throw new CustomValidationApiException("유효성 검사 실패함", errorMap);
                }

            }
        }

        return proceedingJoinPoint.proceed(); // profile 함수가 실행됨. //proceed()컨트롤러 에 있는 그함수로 다시 돌아가라는 뜻이다
    }

    @Around("execution(* com.example.photogram.web.*Controller.*(..))")
    public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        //System.out.println("web 컨트롤러 ==========================");
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof BindingResult) {

                BindingResult bindingResult = (BindingResult) arg;

                if (bindingResult.hasErrors()) {
                    Map<String, String> errorMap = new HashMap<>();

                    for (FieldError error : bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage());
                    }
                    throw new CustomValidationException("유효성 검사 실패함", errorMap);
                }
            }
        }
        return proceedingJoinPoint.proceed(); //proceed()그함수로 다시 돌아가라는 뜻이다
    }
}

