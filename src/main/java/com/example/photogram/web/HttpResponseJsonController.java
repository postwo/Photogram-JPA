package com.example.photogram.web;

import com.example.photogram.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HttpResponseJsonController {

    @GetMapping("/resp/json")
    public String respJson() {
        return "{\"username\":\"cos\"}"; // 이거는 복사붙여넣기 하면 이렇게 편하게 알아서 역슬러시 까지 작성된다
    }


    /* 1. "{\"username\":"+user.getUsername()+"\"}"; 원래는 반환할때 이렇게 되는데 이거를
         MessageConverter 가 자동으로 JavaObject 를 Json으로 변경해서 통신을 통해 응답을 해준다.*/

    /* 2. @RestController 일때만 MessageConverter가 작동한다.*/

    @GetMapping("/resp/json/javaobject")
    public User respJsonJavaObject() { //user(java) object 타입으로 변환
        User user = new User(); //객체를 생성 = user(java) object 생성
        user.setUsername("홍길동");
        return user; //user object 반환

    }
}


