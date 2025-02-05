package com.example.photogram.web.dto.image;

import com.example.photogram.domain.image.Image;
import com.example.photogram.domain.user.User;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data //getter setter를 위해 사용
public class ImageUploadDto { //프론트단에서 받아올거

    //@Notblank 이렇게 validation을 사용못하는이유는 MultipartFile에는 지원을 안해주기 때문이다 그러므로 image
    private MultipartFile file;
    private String caption;

    public Image toEntity (String postImageUrl, User user) { // postImageUrl==imageFileName
        return Image.builder()
                .caption(caption)
                .postImageUrl(postImageUrl)
                .user(user) // 어떤 유저가 insert(저장)를 했는지 알기위해 유저정보가 필요하다
                .build();
    }
}
