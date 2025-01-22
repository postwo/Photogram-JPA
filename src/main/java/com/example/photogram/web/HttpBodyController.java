package com.example.photogram.web;

import com.example.photogram.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HttpBodyController {

    //### http header의 content-type 이해 ==  content-type은 post, put 요청할때만 존재
    //예)
    //header{
    //무게 : 1kg,
    //종류 : 쌀,
    //원산지 : 진주
    //}
    //- 스프링부트는 기본적으로 x-www-form-urlencoded 타입을 파싱해준다.
    //- x-www-form-urlencoded => 예) key =value
    //- text/plain => 예) 안녕
    //- application/json => 예) {"username" :"cos"}


    //브라우저 주소창에서는 기본적으로 GET 요청만 가능하기 때문에, POST 요청을 테스트하려면 Postman 같은 API 테스트 도구를 사용하는 것이 적합

    @PostMapping("/body1")
    public String xwwwformurlencoded(String username){ // 포스트맨에서 body -> x - www-from-urlenoded 를 선택후 key 값에 username value에 cos 넣어서 테스트
        //headers -> content-type을 보면 내가 어떤형식으로 보낸지 확인가능
        log.info(username);
        return "key=value 전송옴";
    }

    // @RequestBody == 클라이언트가 전송한 JSON, XML, 혹은 기타 데이터 형식을 컨트롤러 메서드의 파라미터로 매핑해주는 역할

    @PostMapping("/body2")
    public String testplain(@RequestBody String data){ //평문:안녕 //포스트맨에서 body -> raw 를 선택후 text로 변경  안녕을 send한다
        //headers -> content-type을 보면 내가 어떤형식으로 보낸지 확인가능
        log.info(data);
        return "plain/test 전송옴";
    }

    @PostMapping("/body3")
    public String applicationjson(@RequestBody String data){ //포스트맨에서 body -> raw 를 선택후 json으로 변경 { "username" : "cos" }
        //headers -> content-type을 보면 내가 어떤형식으로 보낸지 확인가능 확인하면 applciation/son으로 되어있다
        log.info(data);
        return "json 전송옴";
    }

    @PostMapping("/body4")
    public String applicationjsoToObject(@RequestBody User user){ //포스트맨에서 body -> raw 를 선택후 json으로 변경{ "username" : "cos" }
        //user = 이게 자바 오브젝트를 뜻한다
        //headers -> content-type을 보면 내가 어떤형식으로 보낸지 확인가능 확인하면 applciation/son으로 되어있다
        log.info(user.getUsername());
        return "json 전송옴";
    }
}
