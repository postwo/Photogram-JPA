package com.example.photogram.service;

import com.example.photogram.domain.subscribe.SubscribeRepository;
import com.example.photogram.domain.user.User;
import com.example.photogram.domain.user.UserRepository;
import com.example.photogram.handler.ex.CustomException;
import com.example.photogram.handler.ex.CustomValidationApiException;
import com.example.photogram.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

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
}
