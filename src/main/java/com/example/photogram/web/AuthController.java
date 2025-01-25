package com.example.photogram.web;

import com.example.photogram.domain.user.User;
import com.example.photogram.service.AuthService;
import com.example.photogram.web.dto.auth.SignupDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller // 1.IOC 2.파일을 리턴하는 컨트롤러
@Slf4j
@RequiredArgsConstructor// final 필드를 di 할떄 사용
public class AuthController {

    private final AuthService authService; //DI 해서 불러온다

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
    //signupDto 에서 에러가 발생하면 BindingResult에 다모아준다. 그 모은거는 getFieldErrors에 모여있다
    public String signup(@Valid SignupDto signupDto, BindingResult bindingResult) {//전처리 //key = value (x-www-form-urlencoded)

        if (bindingResult.hasErrors()){
            Map<String,String> errorMap = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()){
                errorMap.put(error.getField(),error.getDefaultMessage());
                System.out.println(error.getDefaultMessage());
            }
        }

        //User <- SignupDto
        User user = signupDto.toEntity();
        User userENtity = authService.회원가입(user);
        System.out.println(userENtity);
        return "auth/signin"; // 회원가입 성공시 로그인 페이지로 이동
    }

}
