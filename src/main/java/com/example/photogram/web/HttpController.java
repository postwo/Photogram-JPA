package com.example.photogram.web;

import org.springframework.web.bind.annotation.*;

//@Controller // File을 응답하는 컨트롤러 (클라이언트가 브라우저면 .html 파일을 )
@RestController // Data를 응답하는 컨트롤러 (클라이언트가 핸드폰이면 data)
public class HttpController {

    // get = 데이터 요청
    @GetMapping("/get")
    public String Get() {
        return "<h1> get 요청됨 </h1>";
    }

    // post = 데이터 전송 = http body(데이터가 담김) 가 있다
    @PostMapping("/post")
    public String Post() {
        return "post 요청됨";
    }

    // put = 데이터 갱신 = http body(데이터가 담김) 가 있다
    @PutMapping("/put")
    public String Put() {
        return "put 요청됨";
    }

    // delete = 데이터 삭제
    @DeleteMapping("delete")
    public String Delete() {
        return "Delete 요청됨";
    }

}
