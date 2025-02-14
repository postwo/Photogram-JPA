package com.example.photogram.config;

import com.example.photogram.config.oauth.OAuth2DetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration // IOC등록
public class SecurityConfig {

    private final OAuth2DetailsService oAuth2DetailsService;

    @Bean
    BCryptPasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/","/user/**","/image/**","/subscribe/**", "/comment/**","/api/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/auth/signin") //get //인증이 필요한 페이지를 요청하면 이거를 호출 == 인증이 안된 사용자가 페이지를 요청하면 이게 동작
                .loginProcessingUrl("/auth/signin") //post //누군가가 로으인을 요청하면 이게 낚아챈다. == 이거에 대한 요청명을 jsp sginin에 있다 //스프링 시큐리티가 로그인 프로세스 진행
                .defaultSuccessUrl("/")
                .and()
                .oauth2Login() //form 로그인도 하는데 , oauth2 로그인도 할꺼야
                .userInfoEndpoint()// oauth2 로그인을 하면 최종으답을 회원정보를 바로 받을 수 있다.
                .userService(oAuth2DetailsService);
        return http.build();
    }
}
