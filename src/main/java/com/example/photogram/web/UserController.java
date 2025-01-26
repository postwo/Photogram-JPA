package com.example.photogram.web;

import com.example.photogram.config.auth.PrincipalDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {

    //마이페이지 이동
    @GetMapping("/user/{id}") //다른 사람페이지가 나올수도 있기 때문에 그걸 방지하기 위해 id를 붙였다
    public String profile(@PathVariable int id) {
        return "user/profile";
    }

    //회원정보 변경 페이지 이동
    @GetMapping("/user/{id}/update") //유저 1번을 업데이트 하겠다 이런형식으로 가기 위해 id 사용
    public String update(@PathVariable int id, @AuthenticationPrincipal PrincipalDetails principalDetails){ //principalDetails 이게 세션 정보이다,
        //1. 추천
        System.out.println("세션정보: "+ principalDetails.getUser());

        //2, 극혐
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails mPrincipalDetails = (PrincipalDetails) auth.getPrincipal();
        System.out.println("직접 찾은 세션 정보:"+mPrincipalDetails.getUser());

        return "user/update";
    }
}
