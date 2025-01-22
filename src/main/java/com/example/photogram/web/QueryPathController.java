package com.example.photogram.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueryPathController {

    //http://localhost:8080/chiken?type=양념  = 이게 쿼리 스트링이다 = 이거는 거의 안쓴다.
    @GetMapping("/chiken")
    public String chickenQuery(String type) {
        return type+" 배달갑니다.(쿼리스트링)";
    }

    //http://localhost:8080/chiken/양념 = 이게 주소 변수 매핑이다
    @GetMapping("/chiken/{type}")
    public String chikenPath(@PathVariable String type){
        return type+"배달갑니다.(주소변수매핑)";
    }
}
