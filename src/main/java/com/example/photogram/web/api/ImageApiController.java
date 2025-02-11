package com.example.photogram.web.api;

import com.example.photogram.config.auth.PrincipalDetails;
import com.example.photogram.domain.image.Image;
import com.example.photogram.service.ImageService;
import com.example.photogram.web.dto.CMRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ImageApiController {

    private final ImageService imageService;

    @GetMapping("/api/image")
    public ResponseEntity<?> imageStory(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<Image> images = imageService.이미지스토리(principalDetails.getUser().getId());
        return new ResponseEntity<>(new CMRespDto<>(1,"성공",images), HttpStatus.OK);
    }
}
