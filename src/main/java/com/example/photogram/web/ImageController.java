package com.example.photogram.web;

import com.example.photogram.config.auth.PrincipalDetails;
import com.example.photogram.service.ImageService;
import com.example.photogram.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

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

    //
    @PostMapping("/image")
    public String imageUpload(ImageUploadDto imageUploadDto, @AuthenticationPrincipal PrincipalDetails principalDetails){ //이미지를 업로드 하기위해서 로그인한 유저정보가 필요하다
        // 서비스 호출
        imageService.사진업로드(imageUploadDto,principalDetails);

        return "redirect:/user/"+principalDetails.getUser().getId(); //사진업로드 후 다시 마이페이지로 이동 //principalDetails.getUser().getId() 로그인한 사용자 정보를 가지고 온다.
    }
}
