package com.example.photogram.service;

import com.example.photogram.domain.subscribe.SubscribeRepository;
import com.example.photogram.domain.user.User;
import com.example.photogram.domain.user.UserRepository;
import com.example.photogram.handler.ex.CustomApiException;
import com.example.photogram.handler.ex.CustomException;
import com.example.photogram.handler.ex.CustomValidationApiException;
import com.example.photogram.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${file.path}") //yml 에 있는 파일경로를 받아온다.
    private String uploadFolder;
    private final UserRepository userRepository;
    private final SubscribeRepository subscribeRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional(readOnly = true) //읽기전용 = 데이터변경감지를 안한다
    public UserProfileDto 회원프로필 (int pageUserId, int principalid) { //pageUserId == 현재페이지 아이디 //rincipalid== 로그인한 사용자아이디

        UserProfileDto dto = new UserProfileDto();

        //select * from image where userid = :userId; 이거는 네이티브 쿼리 방식
        //findById 가 optional 타입이기 때문에 orElseThrow처리 회원을 못찾을수도 있기 떄문에 이렇게 처리
        User userEntity = userRepository.findById(pageUserId).orElseThrow(() -> { //pageUserId == 현재페이지 아이디
            throw new CustomException("해당 프로필 페이지는 없는 페이지입니다.");
        });

        dto.setUser(userEntity);
//        dto.setPageOwner(pageUserId == principalid ? 1: -1); // 1은 페이지 주인,-1은 주인이 아님 이거는 int여서 삼항연사자사용
        dto.setPageOwnerState(pageUserId == principalid); //boolean 방식
        dto.setImageCount(userEntity.getImages().size()); // 이미지 갯수

        int SubscribeState = subscribeRepository.mSubscribeState(principalid,pageUserId);
        int SubscribeCount = subscribeRepository.mSubscribeCount(pageUserId);

        dto.setSubscribeState(SubscribeState == 1);
        dto.setSubscribeCount(SubscribeCount);

        // 마이페이지 이미지에 좋아요 카운트 추가하기
        // 각각의 이미지마다 들고 있어야 하기 때문에 dto에 따로 넣을 수도없고 해서 dto안에 User에 userentity 내부를 수정해주면 된다
        userEntity.getImages().forEach((image)->{
            image.setLikeCount(image.getLikes().size());
        });

        return dto;
    }

    @Transactional
    public User 회원수정(int id, User user){
        //1. 영속화
        //1.무조건 찾았다. 걱정마 get() 2. 못찾았어 익셉션 발동시킬께 orElseThrow()
        User userEntity = userRepository.findById(id).orElseThrow(()-> {
              return new CustomValidationApiException("찾을 수 없는 id입니다.");
        });

        //2. 영속화된 오브젝트를 수정 - 더티체킹
        userEntity.setName(user.getName());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setBio(user.getBio());
        userEntity.setWebsite(user.getWebsite());
        userEntity.setPhone(user.getPhone());
        userEntity.setGender(user.getGender());


        return userEntity; // 더티체킹이 일어나서 업데이트가 완료됨
    }

    @Transactional
    public User 회원프로필사진변경(int principalId, MultipartFile profileImageFile) {
        UUID uuid = UUID.randomUUID(); // uuid 생성 = 네트워크 상에서 고유성이 보장되는 id를 만들기 위한 표준 규약 //유일성(중복이없다)이 있다(하지만 몇억분의 잃에 확률로 안되는 경우도 있다)
        String imageFileName = uuid+"_"+profileImageFile.getOriginalFilename(); // 실제 파일네임이 들어온다 // 1.jpg 이런형식으로 //이렇게 파일명하고 uuid를 합치면 유일성이 꺠질일은 없다
        System.out.println("이미지 파일이름"+imageFileName);

        Path imageFilePath = Paths.get(uploadFolder+imageFileName); //실제 저장할 파일경로 + 저장할 파일 이름

        // 통신,i/o( 하드디스크에 기록을 하거나 읽을때) -> 예외가 발생할수 있다 그러므로 try catch로 묶는다.
        try{
            Files.write(imageFilePath, profileImageFile.getBytes());//사진을 바이트화 해야한다.
        }catch (Exception e){
            e.printStackTrace();
        }

        User userEntity = userRepository.findById(principalId).orElseThrow(() ->{
            throw new CustomApiException("유저를 찾을 수 없습니다."); //데이터를 리턴해줘서  이 커스텀 익셉션 사용
        });

        userEntity.setProfileImageUrl(imageFileName);

        return userEntity;
    } // 더티체키으로 업데이트 됨
}
