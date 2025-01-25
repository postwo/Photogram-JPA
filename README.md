# 포토그램 - 인스타그램 클론

### 의존성

- Sring Boot DevTools
- Lombok
- Spring Data JPA
- MariaDB Driver
- Spring Security
- Spring Web
- oauth2-client

```xml
<!-- 시큐리티 태그 라이브러리 -->
<dependency>
	<groupId>org.springframework.security</groupId>
	<artifactId>spring-security-taglibs</artifactId>
</dependency>

<!-- JSP 템플릿 엔진 -->
<dependency>
	<groupId>org.apache.tomcat</groupId>
	<artifactId>tomcat-jasper</artifactId>
	<version>9.0.43</version>
</dependency>

<!-- JSTL -->
<dependency>
	<groupId>javax.servlet</groupId>
	<artifactId>jstl</artifactId>
</dependency>
```

### 데이터베이스

```sql
create user 'cos'@'%' identified by 'cos1234';
GRANT ALL PRIVILEGES ON *.* TO 'cos'@'%';
create database photogram;
```

### 태그라이브러리

```jsp
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
```

### 인텔리제이 devtools 적용방법

2개 블로그 참고 
https://mmee2.tistory.com/60

https://velog.io/@dayoung_sarah/IntelliJ-%EC%97%90-Devtools-%EC%A0%81%EC%9A%A9%ED%95%98%EA%B8%B0

devtools:
    livereload:
        enabled: true
    restart:
        enabled: true
thymeleaf:
    cache: false  //Thymeleaf 템플릿 파일을 수정했을 때, 서버를 재시작하지 않아도 변경 사항이 바로 반영

devtools 이용시 템플릿 엔진의 캐싱 기능으로 인해 별도의 설정을 해주지 않으면, 그대로 캐싱 기능을 사용한다. 
개발환경에서는 변경사항을 바로 반영해줘야 하기때문에 아래와 같이 캐싱 기능을 끈다.
하지만 운영환경에서는 캐싱기능을 통해 성능 향상을 위해 다시 true로 설정해줘야 한다.


### web패키지를 테스트할때는 springweb 과  spring bootdevtools만 있으면 된다.

## CSRF 토큰
어떤 클라이언트가 요청을 한다 form에다가 데이터를 담아서 서버로 전송하면
서버에 있는 시큐리티가 먼저 검사를 한다. 검사하는거는 csrf 토큰을 검사한다.
CSRF는 응답해주는(jsp,html 등) 파일에다가 토큰(CSRF토큰)을 심는다.
그니까 한마디로 input 창에 담기 값들에 다 csrf="KFC" 이런형식으로 달린다고 생각하면된다.


### Transactional을 거는 이유 
@Transactional을 사용하는 이유는 트랜잭션 관리를 통해 데이터의 일관성과 안정성을 보장하기 위해서입니다. 이 코드에서 @Transactional이 필요한 이유를 구체적으로 설명하자면:

1. 트랜잭션의 원자성 보장
   트랜잭션은 **"전체가 성공하거나 전체가 실패해야 한다"**는 원자성을 보장합니다.
   회원가입 로직에서는 아래와 같은 단계들이 연속적으로 실행됩니다:

비밀번호를 암호화.
암호화된 비밀번호와 사용자 정보를 설정.
데이터베이스에 저장 (userRepository.save(user)).
이 과정에서 만약 예외가 발생한다면, 데이터가 중간 상태로 저장되지 않고 롤백됩니다.
예를 들어:

userRepository.save(user) 실행 도중 데이터베이스 오류가 발생하면, 이미 암호화된 비밀번호만 처리된 user 객체가 데이터베이스에 일부만 저장되는 상황을 막습니다.
2. 트랜잭션의 일관성 보장
   @Transactional은 데이터의 일관성을 유지합니다.
   회원가입은 데이터베이스의 쓰기 작업(insert)이 이루어지기 때문에, 하나의 트랜잭션으로 묶여야 데이터베이스가 일관성을 가질 수 있습니다. 만약 트랜잭션이 없으면, 일부 데이터만 변경되거나 불완전한 데이터가 저장될 수 있습니다.

3. 자동 롤백 기능
   @Transactional을 사용하면, 메서드 실행 중 예외가 발생할 경우 데이터베이스에 수행된 모든 작업이 자동으로 롤백됩니다.
   예를 들어:

비밀번호 암호화 후, save 메서드에서 데이터베이스 예외가 발생하면 이미 암호화된 비밀번호를 가진 사용자가 저장되지 않도록 모든 작업이 취소됩니다.
4. 트랜잭션 커밋 관리
   @Transactional을 통해 스프링이 해당 메서드가 성공적으로 완료되었는지 여부를 확인한 후, 작업을 자동으로 커밋합니다.
   즉:

메서드가 성공적으로 끝나면 데이터가 저장됩니다.
실패하면 롤백됩니다.
핵심 요약
@Transactional을 사용하는 이유: 회원가입 처리의 전 과정을 원자성, 일관성, 자동 롤백으로 안전하게 관리하기 위해서입니다.
트랜잭션을 사용하는 상황: 데이터베이스의 쓰기 작업(INSERT, UPDATE, DELETE)이 포함된 경우 트랜잭션으로 묶어야 데이터가 깨지거나 불완전하게 저장되는 일을 방지할 수 있습니다.
회원가입은 중요한 작업이므로 트랜잭션으로 묶어 데이터의 안정성을 보장해야 합니다.


### 콘솔 로그 크기 설정 방법
https://observerlife.tistory.com/60