package com.example.photogram.service;

import com.example.photogram.domain.user.User;
import com.example.photogram.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public User 회원수정(int id, User user){
        //1. 영속화
        User userEntity = userRepository.findById(id).get(); //1.무조건 찾았다. 걱정마 get() 2. 못찾았어 익셉션 발동시킬께 orElseThrow()

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
