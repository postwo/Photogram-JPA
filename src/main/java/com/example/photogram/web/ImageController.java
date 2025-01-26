package com.example.photogram.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ImageController {

    // 이미지 페이지 이동
    @GetMapping({"/","/image/story"}) //로그인 하자마자 바로 이미지 페이지로 이동
    public String story() {
        return "image/story";
    }

    // 인기 이지지 페이지로 이동
    @GetMapping("/image/popular")
    public String popular() {
        return "image/popular";
    }

    // 업로드 페이지로 이동
    @GetMapping("/image/upload")
    public String upload() {
        return "image/upload";
    }
}
