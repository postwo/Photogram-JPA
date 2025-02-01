package com.example.photogram.handler.ex;

public class CustomApiException extends RuntimeException { //구독 에러

    //객체를 구분할 때 사용 = 우리 한테는 중요하지 않음 jvm한테 중요
    private static final long serialVersionUID = 1L; //직렬화와 역직렬화 시 클래스 변경에 따른 호환성 문제를 방지하기 위해서

    //메시지만 받는 생성자
    public CustomApiException(String message) {
        super(message);
    }

}
