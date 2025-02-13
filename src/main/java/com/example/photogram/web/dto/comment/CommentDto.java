package com.example.photogram.web.dto.comment;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

// Notnull = null 값체크
// NotEmpty = 빈값이거나 null 체크
// NotBlank = 빈값이거나 null 체크 그리고 빈 공백(스페이스)까지

@Data
public class CommentDto {

    @NotBlank//빈값이거나 null 체크 그리고 빈공백까지
    private String content;

    // NotBlank,NotEmpty 는 int나 integer에서는 사용 못한다
    @NotNull // null 체크
    private Integer imageId;

    //toEntity가 필요 없다
}
