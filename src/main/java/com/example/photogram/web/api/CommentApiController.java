package com.example.photogram.web.api;

import com.example.photogram.config.auth.PrincipalDetails;
import com.example.photogram.domain.comment.Comment;
import com.example.photogram.handler.ex.CustomValidationApiException;
import com.example.photogram.service.CommentService;
import com.example.photogram.web.dto.CMRespDto;
import com.example.photogram.web.dto.comment.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;

    //CommentDto 이렇게 받는거는 x-www-form-data 형식이기 때문에 못받는다 그러므로 RequestBody를 붙여야지 Json데이터를 받을수 있다
    @PostMapping("/api/comment")
    public ResponseEntity<?> commentSave(@Valid @RequestBody CommentDto commentDto, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails principalDetails) { //ajax를 통해 데이터를 받아온다

        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
                System.out.println(error.getDefaultMessage());
            }

            throw new CustomValidationApiException("유효성 검사 실패함", errorMap); //errormap에는 bindingResult에 있는 모든에러가 모여있다.
        }

        Comment comment =  commentService.댓글쓰기(commentDto.getContent(), commentDto.getImageId(), principalDetails.getUser().getId()); // content, imageId, userId
        return new ResponseEntity<>(new CMRespDto<>(1, "댓글쓰기성공", comment), HttpStatus.CREATED);
    }

    @DeleteMapping("/api/comment/{id}")
    public ResponseEntity<?> commentDelete(@PathVariable int id){
        commentService.댓글삭제(id);
        return new ResponseEntity<>(new CMRespDto<>(1, "댓글삭제성공", null), HttpStatus.OK);
    }
}
