package com.example.photogram.service;

import com.example.photogram.domain.user.User;
import com.example.photogram.domain.user.UserRepository;
import com.example.photogram.handler.ex.CustomException;
import com.example.photogram.handler.ex.CustomValidationApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional(readOnly = true) //읽기전용 = 데이터변경감지를 안한다
    public User 회원프로필 (int userId) {
        //select * from image where userid = :userId; 이거는 네이티브 쿼리 방식
        //findById 가 optional 타입이기 때문에 orElseThrow처리 회원을 못찾을수도 있기 떄문에 이렇게 처리
        User userEntity = userRepository.findById(userId).orElseThrow(() -> {
            throw new CustomException("해당 프로필 페이지는 없는 페이지입니다.");
        });

        return userEntity;
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
