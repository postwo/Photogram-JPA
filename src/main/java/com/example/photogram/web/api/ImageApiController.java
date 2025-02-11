package com.example.photogram.web.api;

import com.example.photogram.config.auth.PrincipalDetails;
import com.example.photogram.domain.image.Image;
import com.example.photogram.service.ImageService;
import com.example.photogram.web.dto.CMRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ImageApiController {

    private final ImageService imageService;

    //, sort = "id", direction = Sort.Direction.DESC 네이티브쿼리를 쓰고 있으면 이거는 데잍터베이스 에러가 뜬다
    // 대신 네이티브 쿼리를 안쓰면 그냥 적용된다
    // 네이티브 쿼리로 할시 그냥 네이티브 쿼리에다가 정렬 쿼리를 추가해주면 된다
    // 포스트맨 localhost:8080/api/image?page=0 0번페이지 조회
    @GetMapping("/api/image")
    public ResponseEntity<?> imageStory(@AuthenticationPrincipal PrincipalDetails principalDetails
    , @PageableDefault(size = 3) Pageable pageable) { //direction = Sort.Direction.DESC = 최신 데이터가 항상 앞으로
        Page<Image> images = imageService.이미지스토리(principalDetails.getUser().getId(),pageable);
        return new ResponseEntity<>(new CMRespDto<>(1,"성공",images), HttpStatus.OK);
    }
}
