package com.example.photogram.service;

import com.example.photogram.config.auth.PrincipalDetails;
import com.example.photogram.domain.image.Image;
import com.example.photogram.domain.image.ImageRepository;
import com.example.photogram.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${file.path}") //yml 에 있는 파일경로를 받아온다.
    private String uploadFolder;
    private final ImageRepository imageRepository;

    //사진업로드
    @Transactional // 서비스에서 변형(update,insert,delete )을 줄때는 Transactional을 꼭 걸어줘야 한다. 그이유는 둘중에 하나 실패를 하는 경우가 발생하기 때문에 이부분을 걸어서 rollback시켜줌으로 불상사를 방지할수 있다
    public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {
        UUID uuid = UUID.randomUUID(); // uuid 생성 = 네트워크 상에서 고유성이 보장되는 id를 만들기 위한 표준 규약 //유일성(중복이없다)이 있다(하지만 몇억분의 잃에 확률로 안되는 경우도 있다)
        String imageFileName = uuid+"_"+imageUploadDto.getFile().getOriginalFilename(); // 실제 파일네임이 들어온다 // 1.jpg 이런형식으로 //이렇게 파일명하고 uuid를 합치면 유일성이 꺠질일은 없다
        System.out.println("이미지 파일이름"+imageFileName);

        Path imageFilePath = Paths.get(uploadFolder+imageFileName); //실제 저장할 파일경로 + 저장할 파일 이름

        // 통신,i/o( 하드디스크에 기록을 하거나 읽을때) -> 예외가 발생할수 있다 그러므로 try catch로 묶는다.
        try{
            Files.write(imageFilePath, imageUploadDto.getFile().getBytes());//사진을 바이트화 해야한다.
        }catch (Exception e){
            e.printStackTrace();
        }

        // image 테이블에 저장
        Image image = imageUploadDto.toEntity(imageFileName,principalDetails.getUser()); // db에는 이미지 파일네임을 넣을거다
        imageRepository.save(image);

//        System.out.println(imageEntity); //이게 있으면 무한 참조 에러가 발생한다
    }

    @Transactional(readOnly = true) // 영속성 컨텍스트 변경 가지를 해서 , 더티체킹, flush(반영) =readonly일때는 flush를 적용 안한다
    public List<Image> 이미지스토리(int principalid) {
        List<Image> images = imageRepository.mStory(principalid);
        return images;
    }
}
