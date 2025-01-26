package com.example.photogram.config.auth;

import com.example.photogram.domain.user.User;
import com.example.photogram.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//.loginProcessingUrl("/auth/signin") 로그인 요청이 들어오면 여기서 처리하는거다
@Service //IOC
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    //password는 알아서 시큐리티가 처리해준다. 신경 안써된다.
    //1. 패스워드는 알아서 체킹하니까 신경쓸 필요 없다.
    //2. 리턴이 잘되면 자동으로 UserDetails 타입을 세션으로 만든다
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepository.findByUsername(username);

        if (userEntity==null){
            return null;
        }else{
            return new PrincipalDetails(userEntity); //이렇게 하면 세션에 user오브젝트를 저장할수 있다 .
        }


    }
}
