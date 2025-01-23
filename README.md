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