package com.example.photogram.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

//어노테이션이 JpaRepository를 상속하면 없어도 ioc 등록이 자동으로 된다.
public interface UserRepository extends JpaRepository<User,Integer> {
}
