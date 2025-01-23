package com.example.photogram.web;

import com.example.photogram.web.dto.auth.SignupDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller // 1.IOC 2.파일을 리턴하는 컨트롤러
@Slf4j
public class AuthController {

    //로그인 페이지
    @GetMapping("/auth/signin")
    public String signinForm() {
        return "auth/signin";
    }

    //회원가입 페이지
    @GetMapping("/auth/signup")
    public String signupForm() {
        return "auth/signup";
    }

    // 회원가입
    // 회원가입 버튼 -> /auth/signup -> /auth/signin
    @PostMapping("/auth/signup")
    public String signup(SignupDto signupDto) {//key = value (x-www-form-urlencoded)
        log.info(signupDto.toString());
        return "auth/signin"; // 회원가입 성공시 로그인 페이지로 이동
    }
}
