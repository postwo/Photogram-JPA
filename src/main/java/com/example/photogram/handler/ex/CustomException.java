package com.example.photogram.handler.ex;

public class CustomException extends RuntimeException {

    //객체를 구분할 때 사용 = 우리 한테는 중요하지 않음 jvm한테 중요
    private static final long serialVersionUID = 1L; //직렬화와 역직렬화 시 클래스 변경에 따른 호환성 문제를 방지하기 위해서

    //생성자
    public CustomException(String message) {
        super(message); // message는 getter를 만들필요없이 부모한테 던지면 된다. = 부모 클래스인 RuntimeException에 이미 message를 처리하는 필드와 getMessage() 메서드가 있기 때문에
    }

}
