package com.example.photogram.config.auth;

import com.example.photogram.domain.user.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data //getter,setter
public class PrincipalDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

    private User user;

    // 생성자
    public PrincipalDetails(User user){
        this.user = user;
    }

    //권한을 가지고 오는 메서드
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 권한이 여러개 일때
        Collection<GrantedAuthority> collector = new ArrayList<>();
        collector.add(() -> user.getRole());
        return collector;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 밑의 4가지가 true가 아니면 로그인을 못한다.

    // 니 계정이 만료가 되었니 true면 만료가 안된거다
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //니 계정이 잠겼니
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 니 비밀번호가 1년 동안 한번도 안바뀐거 아니니
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 니 계정이 활성화 되어있니
    @Override
    public boolean isEnabled() {
        return true;
    }
}
