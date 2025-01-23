package com.example.photogram.service;

import com.example.photogram.domain.user.User;
import com.example.photogram.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service //1. IOC 등록 2. 트랜잭션 관리
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptpasswordEncoder;

    @Transactional //회원가입 메서드 실행되고 종료 될때까지 진행됨 //Transactional 붙일때는 write(insert,update,delete) 일때 붙여준다
    public User 회원가입(User user) { //여기서 user는 통신을 통해 받아온 user objcet이다
        // 회원가입 진행
        String rawPassword = user.getPassword();
        String encPassword = bCryptpasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword); // 암호화
        user.setRole("ROLE_USER"); // 회원가입한 유저는 다 user 권한 준다.0
        User userEntity = userRepository.save(user); //db 에들어간 뒤 응답 받은거다 //데이터베이스 있는 데이터를 user object에 담은거다
        return userEntity;
    }
}
