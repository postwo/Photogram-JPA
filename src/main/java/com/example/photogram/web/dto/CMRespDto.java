package com.example.photogram.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CMRespDto <T>{ // 공토 응답 DTO
    private int code; //1(성공), -1(실패)
    private String message;
    private T data; //어떤 타입이든 받는다

}
