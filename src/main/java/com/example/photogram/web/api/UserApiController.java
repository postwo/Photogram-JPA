package com.example.photogram.web.api;

import com.example.photogram.config.auth.PrincipalDetails;
import com.example.photogram.domain.user.User;
import com.example.photogram.handler.ex.CustomValidationApiException;
import com.example.photogram.service.SubscribeService;
import com.example.photogram.service.UserService;
import com.example.photogram.web.dto.CMRespDto;
import com.example.photogram.web.dto.subscribe.SubscribeDto;
import com.example.photogram.web.dto.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final SubscribeService subscribeService;

    // MultipartFile  profileImageFile 이거하고 profile.jsp 에 name명하고 정확히 같아야 매핑 돼서 받아온다(중요)
    @PutMapping("/api/user/{principalId}/profileImageUrl")
    public ResponseEntity<?> profileImageUrlUpdate(@PathVariable int principalId, MultipartFile profileImageFile,
                                                   @AuthenticationPrincipal PrincipalDetails principalDetails){ //principalDetails 회원이미지가 변경 되는거기 때무에 추가
        User userEntity = userService.회원프로필사진변경(principalId,profileImageFile);
        principalDetails.setUser(userEntity); //세션 변경
        return new ResponseEntity<>(new CMRespDto<>(1,"프로필사진 변경 성공",null),HttpStatus.OK);
    }


    @GetMapping("/api/user/{pageUserId}/subscribe") //pageUserId 그페이지주인 아이디
    public ResponseEntity<?> subscribeList(@PathVariable int pageUserId,@AuthenticationPrincipal PrincipalDetails principalDetails){ //principalDetails로그인한 사용자정보

        List<SubscribeDto> subscribeDto = subscribeService.구독리스트(principalDetails.getUser().getId(),pageUserId) ;

        return new ResponseEntity<>(new CMRespDto<>(1,"구독자 정보 리스트 불러오기 성공",subscribeDto), HttpStatus.OK);
    }

    @PutMapping("/api/user/{id}")
    public CMRespDto<?> update(@PathVariable int id,
                               @Valid UserUpdateDto userUpdateDto,
                               BindingResult bindingResult, // 꼭 @Valid 가 적형있는 다음 파라메터 적어야됨
                               @AuthenticationPrincipal PrincipalDetails principalDetails) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
                System.out.println(error.getDefaultMessage());
            }

            throw new CustomValidationApiException("유효성 검사 실패함", errorMap); //errormap에는 bindingResult에 있는 모든에러가 모여있다.
        }else {
            User userEntity = userService.회원수정(id, userUpdateDto.toEntity());
            principalDetails.setUser(userEntity); //세션 정보 변경
            return new CMRespDto<>(1, "회원 수정 완료", userEntity); // 응답시에  userEntity의 모든 getter 함수가 호출되고 Json으로 파싱하여 응답한다 //ajax 호출한쪽으로 응답함
        }
    }
}
