package com.example.photogram.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//.loginProcessingUrl("/auth/signin") 로그인 요청이 들어오면 여기서 처리하는거다
@Service //IOC
public class PrincipalDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("나 실행돼"+username);
        return null;
    }
}
