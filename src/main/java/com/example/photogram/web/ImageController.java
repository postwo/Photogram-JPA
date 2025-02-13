package com.example.photogram.web;

import com.example.photogram.config.auth.PrincipalDetails;
import com.example.photogram.domain.image.Image;
import com.example.photogram.handler.ex.CustomValidationException;
import com.example.photogram.service.ImageService;
import com.example.photogram.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    // 이미지 페이지 이동
    @GetMapping({"/","/image/story"}) //로그인 하자마자 바로 이미지 페이지로 이동
    public String story() {
        return "image/story";
    }

    //api로 구현한단면 = 이유 = (브라우저에서 ㅣ요청하는게 아니라 ,안드로이드,ios 요청)
    // 인기 이지지 페이지로 이동
    @GetMapping("/image/popular")
    public String popular(Model model) {

        //web/api에 이컨트롤러를 안만드는 이유는 api 쪽은 데이터만 응답해줄려고 만든곳이기 떄문이다
        List<Image> images = imageService.인기사진();

        model.addAttribute("images",images);

        return "image/popular";
    }

    // 업로드 페이지로 이동
    @GetMapping("/image/upload")
    public String upload() {
        return "image/upload";
    }

    //이미지 업로드
    @PostMapping("/image")
    public String imageUpload(ImageUploadDto imageUploadDto, @AuthenticationPrincipal PrincipalDetails principalDetails){ //이미지를 업로드 하기위해서 로그인한 유저정보가 필요하다

        if (imageUploadDto.getFile().isEmpty()){
            throw new CustomValidationException("이미지가 첨부 되지 않았습니다.",null);
        }

        // 서비스 호출
        imageService.사진업로드(imageUploadDto,principalDetails);

        return "redirect:/user/"+principalDetails.getUser().getId(); //사진업로드 후 다시 마이페이지로 이동 //principalDetails.getUser().getId() 로그인한 사용자 정보를 가지고 온다.
    }
}
