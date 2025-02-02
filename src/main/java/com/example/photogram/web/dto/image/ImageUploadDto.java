package com.example.photogram.web.dto.image;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data //getter setter를 위해 사용
public class ImageUploadDto { //프론트단에서 받아올거

    private MultipartFile file;
    private String caption;
}
