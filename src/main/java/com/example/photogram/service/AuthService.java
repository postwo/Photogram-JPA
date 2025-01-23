package com.example.photogram.service;

import com.example.photogram.domain.user.User;
import com.example.photogram.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service //1. IOC 등록 2. 트랜잭션 관리
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public User 회원가입(User user) { //여기서 user는 통신을 통해 받아온 user objcet이다
        // 회원가입 진행
        User userEntity = userRepository.save(user); //db 에들어간 뒤 응답 받은거다 //데이터베이스 있는 데이터를 user object에 담은거다
        return userEntity;
    }
}
