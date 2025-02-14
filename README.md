
# 포토그램 - 인스타그램 클론 코딩

### 배포 프로세스
- AWS 회원가입
- github 소스코드 다운받기
- profile 세팅
- RDS 만들기 (default 보안그룹설정 - 3306 내IP, 3306 default 설정)
- 테이블 생성하기
- Elastic Beanstalk 만들기
- Elastic Beanstalk jar와 webapp 폴더 배포하기

### 빌드하기
- 빌드는 dev 모드로 하고 추후 엘라스틱빈스톡에서 실행은 prod 모드로 실행
- ./mvnw clean package -Dspring.profiles.active=dev

### 실행테스트
- java -jar -Dspring.profiles.active=dev

### 배포하기
- photogram-start-0.0.1-SNAPSHOT.jar
- src/main/webapp 폴더 가져오기
- 두개 압축해서 deploy-1.0.zip 으로 만들기

### 배포하기
- deploy-1.0.zip 을 엘라스틱 빈스톡에 배포하기


### 테이블 생성
```sql
create database photogram;

USE photogram;

create table comment_tb (
  id integer not null auto_increment,
  content varchar(100) not null,
  createDate datetime(6),
  imageId integer,
  userId integer,
  primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;


create table image_tb (
  id integer not null auto_increment,
  caption varchar(255),
  createDate datetime(6),
  postImageUrl varchar(255),
  userId integer,
  primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;


create table likes_tb (
  id integer not null auto_increment,
  createDate datetime(6),
  imageId integer,
  userId integer,
  primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;


create table subscribe_tb (
  id integer not null auto_increment,
  createDate datetime(6),
  fromUserId integer,
  toUserId integer,
  primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;


create table user_tb (
  id integer not null auto_increment,
  bio varchar(255),
  createDate datetime(6),
  email varchar(255) not null,
  gender varchar(255),
  name varchar(255) not null,
  password varchar(255) not null,
  phone varchar(255),
  profileImageUrl varchar(255),
  role varchar(255),
  username varchar(100),
  website varchar(255),
  primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;


alter table likes_tb 
 add constraint likes_uk unique (imageId, userId);

alter table subscribe_tb 
 add constraint subscribe_uk unique (fromUserId, toUserId);

alter table user_tb 
 add constraint UK_jreodf78a7pl5qidfh43axdfb unique (username);

alter table comment_tb 
 add constraint FKm1kgtoxiwl6jebkoqxesmh20k 
 foreign key (imageId) 
 references image_tb (id);

alter table comment_tb 
 add constraint FKr4r2wh1b3rucuaxui9lkjwjlu 
 foreign key (userId) 
 references user_tb (id);

alter table image_tb 
 add constraint FKgrmt25snbia9s3sxls7gn3tvl 
 foreign key (userId) 
 references user_tb (id);

alter table likes_tb 
 add constraint FKdrmcrl980hncyhnurju8nm5dy 
 foreign key (imageId) 
 references image_tb (id);

alter table likes_tb 
 add constraint FK1f0ppyupbg6s2v5i9h74rglto 
 foreign key (userId) 
 references user_tb (id);

alter table subscribe_tb 
 add constraint FK9dl9afu79ab4cxwbbgif6t6ie 
 foreign key (fromUserId) 
 references user_tb (id);

alter table subscribe_tb 
 add constraint FKl3r4mww8oeu08s3mqyq138qp3 
 foreign key (toUserId) 
 references user_tb (id);
```

### 인메모리 DB로 테스트 하는 yml, maven

```xml
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```

```yml
server:
  port: 8080
  servlet:
    context-path: /
    encoding:
      charset: utf-8
      enabled: true
    
spring:
  mvc:
    view:
      prefix: /WEB-INF/views/
      suffix: .jsp
      
  datasource:
    url: jdbc:h2:mem:test;MODE=MySQL
    driver-class-name: org.h2.Driver
    username: sa
    password: 
  h2:
    console:
      enabled: true
    
  jpa:
    open-in-view: true
    hibernate:
      ddl-auto: create # update <- 서버 데이터 유지 / create <- 사라짐 / none <- 아무것도 변경 못하게... / create-drop도 있는데 몰라도 된다고...
      naming:
        physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
    show-sql: true
      
  servlet:
    multipart: # multipart 스타일로 사진을 받겠다.
      enabled: true # true <- 사진을 받겠다.
      max-file-size: 2MB # 사진 최대 용량은 2MB가 넘지 않도록 제한

  security:
    user:
      name: test
      password: 1234   

file: # 내가 만든 키값
  path: C:/workspace/upload/ # 업로드된 사진 저장할 공간(폴더)
```

### STS 툴에 세팅하기 - 플러그인 설정
- https://blog.naver.com/getinthere/222322821611

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

<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-aop</artifactId>
</dependency>

<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

### 데이터베이스

```sql
create user 'cos'@'%' identified by 'cos1234';
GRANT ALL PRIVILEGES ON *.* TO 'cos'@'%';
create database photogram;
```

### yml 설정

```yml
server:
  port: 8080
  servlet:
    context-path: /
    encoding:
      charset: utf-8
      enabled: true
    
spring:
  mvc:
    view:
      prefix: /WEB-INF/views/
      suffix: .jsp
      
  datasource:
    driver-class-name: org.mariadb.jdbc.Driver
    url: jdbc:mariadb://localhost:3306/costa?serverTimezone=Asia/Seoul
    username: costa
    password: costa1234
    
  jpa:
    open-in-view: true
    hibernate:
      ddl-auto: update
      naming:
        physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
    show-sql: true
      
  servlet:
    multipart:
      enabled: true
      max-file-size: 2MB

  security:
    user:
      name: test
      password: 1234   

file:
  path: C:/src/springbootwork-sts/upload/
```

### 태그라이브러리

```jsp
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
```

<!-- JSTL -->
<dependency>
	<groupId>javax.servlet</groupId>
	<artifactId>jstl</artifactId>
</dependency>
```

### yml

servlet:
multipart: //multipart 타입으로 사진을 받는다는 의미이다
enabled: true //사진을 받겠다는 의미다 true
max-file-size: 2MB //너무 크사진은 안받기위해 2mb로 제한

file:
path: C:/workspace/springbootwork/upload/
upload뒤에 /가 안붙으면 파일명이 upload에 붙어서 나온다 그러므로 /를 붙여둔다.

### upload jsp
일반 application/x-www-form-urlencoded와 차이점
타입	                                 데이터 포맷	                파일 업로드 가능 여부
application/x-www-form-urlencoded	 key=value&key2=value2	   ❌ (텍스트 데이터만 가능)
multipart/form-data 	             바이너리 데이터 포함	       ✅ (파일 업로드 가능)****

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


### 로그인하고 회원가입할때는 귀중한 정보이기 때문에 주소창에 노출시키지 말아야한다. post로 하면 body 데이터들을 담아서 사용하기 때문에 안전하다 로그인만 예외적으로 post로 한다.


###
	<!-- 시큐리티 태그 라이브러리 (jsp 있을때 사용) -->
		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-taglibs</artifactId>
		</dependency>

<sec:authorize access = "isAuthenticated()">
    <sec:authentication property="principal" var="principal"/>
</sec:authorize>

이거를 headers 에 작성하면 이제 어디서든 principal(이거는 model에 담아서 보낸거다)에 접근이 가능하다  property="principal"이거는 공정값이다 변경x 
이렇게 하면 이제 컨트롤러에서 model을 통해 principal을 보낼 필요없다
principal == principalDetails이다
principal.user.username 그러므로 호출할려면 이렇게 user에 있는 정보를 가져오듯이 이렇게 가지고 와야 한다.

### 포토그램 github
https://github.com/codingspecialist/Eazyup-Photogram-Deploy

### 영속화 뜻
영속화 = 데이터를 DB에 저장하고 관리하는 것

### 연관관계 15강 12분32초
ex)
한명의 유저(User)=1 는 게시글(Board)=n 을 여러가 작성가능 1:n
하나의 게시글은 한명의 유저만 작성가능 1:1
이렇게 관계가 있으면 큰쪽으로본다 == 1:n의 관계를 기준으로 잡는다.

1:n
1:1
이렇게 있으면 세로 기준으로 1하고 n을 비교한다 그럼 n이 더크다

Board 입장에서는 many To one 이고 = board가 n이기 때문에 many가 앞으로 온다.
User 입장에서는 one to many 이다. = user는 1이기 때문에 one이 앞으로 온다.

### 공식
*1. FK 는 꼭 many가 가져가야 한다(중요)*
*2. N:N 의 관계는 중간 테이블(중간테이블은 항상 n이된다.)이 생긴다. == 나머지 테이블은 1이 된다.*

    (Many)
   --Board--
    ID    title  user(FK) 
    1     내용1   1 // 이렇게 하나의 컬럼에는 하나의 데이터가 들어가야한다.
    2     내용2   3
    3     내용3   1

     (one)
   --User--
    ID  username   게시글 // 한명의 유저가 여러개의 게시글을 작성한다는 기준으로 이렇게 하면 원자성이 꺠진다
    1   ssar        1,2 //이렇게 컴마를 넣는거는 안된다. 그러므로 board 테이블에 user컬럼을 추가 해주면 된다. //이렇게 하면 원자성이 깨진다
    2   cos
    3   love

### N:N의 관계 ==  항상 중간 테이블이 필요하다.

한명의 유저는 여러개의 영화를 볼 수 있다 1:n
하나의 영화는 여러명의 유저가 볼 수 있다 n:1
== 이러한 관꼐를 n:n의 관계라고 한다.

// 중간테이블 추가후
한명의 유저(1)는 예매(n)를 여러변 할 수 있다 1:n 
하나의 예매(1)는 한명의 유저(1)가 할 수 있다 1:1
== 이렇게 되면 1:n 의 관계이다

하나의 영화(1)는 예매(n)를 여러개 할 수 있다 1:n
하나의 예매(1)는 영화(1)를 한개만 예매 할 수 있다 1:1
== 이렇게 되면 1:n 의 관계이다

1:n
n:1 
이렇게 있으면 세로 기준으로 1하고 n을 비교한다 그럼 n이 더크다


--User-- //한명의 유저는 예매를 여러번할수 있다 
id   username movieId // 한명의 유저가 여러개의 영화를 본다는 기준으로 이렇게 하면 원자성이 꺠진다
1    cos      1,2 //이렇게 컴마를 넣는거는 안된다. 그러므로 board 테이블에 user컬럼을 추가 해주면 된다. //이렇게 하면 원자성이 깨진다
2    ssar


--예매-- // 이렇게 중간테이블을 추가하므로 원자성 깨지는걸 방지할수 있다.
id   User   Movie 
1    1      1
2    1      2 
3    2      1

--Movie--
id   name     userId //하나의 영화에 여러명의 유저가 볼수있다 이것도 원자성이 깨진다
1    어벤져스   1,2 1,2 //이렇게 컴마를 넣는거는 안된다. 그러므로 board 테이블에 user컬럼을 추가 해주면 된다. //이렇게 하면 원자성이 깨진다
2    배트맨


### 구독 == 같은테이블 

한명의 유저는 구독을 여러번 할 수 있다. 1:n
한명의 유저는 구독을 여러번 할 수 있다. n:1
== n:n 의 관계이다

--user-- 


--subscribe-- // 중간테이블은 항상 n이다 //fk도 중간테이블이 가지고 있느다.
id   구독하는유저   구독받는유저
1     1           2
2     1           3  


--user--

### lazy,eager 전략
//Lazy = User를 Select할 때 해당 User id로 등록된 image들을 가져오지마 -> ex) 대신 getImages() 메서드가 호출될때 가져와
//Eager = User를 Select할 때 해당 User id로 등록된 image들을 전부 Join해서 가져와


### yml파일에 open-in-view: true
open-in-view: true 를 하면 뷰단까지 세션을 오픈한다는 의미 이다 이말은 lazy전략을 사용해도 service에서 controller로 넘어올때 세션이 종료 되지 안하고 controller 지나서 세션이 종료된다
false로 두고 사용하면 service에서 controller 넘어갈때 세션이 종료되기 떄문에 lazy 전략을 사용못한다 이럴떄는 eager전략을 사용해야한다.

### sout 찍을때 StackOverflowError 이나 무한참조가 발생하면
dto 나 entity에서 tosring 을 오버라이딩해서 무한참조 되는부부은 지우고 찍으면 된다 .

### shift + tab 은 전체 앞으로 이동

### ajax 
    $.ajax({

    }).done(res=>{

    }).fail(error=>{

    })

### //manytoone은 기본전략이 eager전략이다 ,onetomany 기본전략은 lazy전략이다 (매우 중요 )
eager = 도서관에 가서 회원 정보를 조회하면, 해당 회원이 빌린 책 목록도 즉시 같이 불러옵니다.
lazy = 도서관에서 회원 정보를 조회할 때는 회원 정보만 가져오고, 책 목록은 나중에 필요할 때 가져오도록 설정합니다.

EAGER → 연관된 데이터를 자주 함께 조회할 때
예: 사용자 프로필 정보 + 게시물 (항상 함께 조회될 경우)
예: 회원 정보 조회 시, 회원이 빌린 책 목록까지 한 번에 조회

LAZY → 연관된 데이터를 따로 조회하는 경우가 많을 때
예: 회원 정보는 자주 보지만, 빌린 책 정보는 가끔 필요할 때
예: 회원 정보는 먼저 조회하고, "내가 빌린 책 보기" 버튼을 클릭하면 그때 책 목록을 조회


### yml 에서 create를 했는데 테이블이 안지워지면 연관관계 때문에 그런거다 
ex) user테이블을 삭제할려면 user랑 연관관계(자식테이블)를 맺고 있는 테이블들이 선 삭제가 되어야 한다
ex) like 테이블을 예로 들어보면 user,image 테이블을 연관관계를 맺고 있다 user테이블을 참고해서 like 테이블을 만들고 image테이블을 참고해서 like테이블을 생성
-> like 테이블을 삭제하고 user를 삭제 -> like 테이블을 삭제하고 image를 삭제 

삭제가 안되면 결론적으로 데이터베이스에 들어가서 자식 테이블들을 먼저 삭제하고 그 이후 부모 테이블을 삭제한다 
ex) 밑 순서대로 자식 테이블 삭제  
1. drop table like;
2. drop table subscribe;
3. drop table images;
4. drop table user;

### 양방향 매핑 JsonIgnoreProperties 적용 예

맞습니다! 결론적으로, @JsonIgnoreProperties({"image"}) 어노테이션이
Likes 엔티티의 image 속성에 적용되었을 때, 양방향 매핑에서도 Image에서 Likes를 조회할 때 Likes 엔티티의 image 필드는 JSON 직렬화에서 제외됩니다.
하지만 Likes에서 Image를 조회할 때는 image 필드가 포함됩니다. 즉, @JsonIgnoreProperties({"image"})가
적용된 곳은 Likes 객체가 JSON으로 변환될 때만 영향을 미치고, 데이터베이스 상의 관계나 실제 객체 내에서는 Likes와 Image 간의 참조는 그대로 유지

### api 에 있는 controller는 데이터로 응답해주기 위해 사용 그리고 ajax를 활용하기 위해 사용 

### http://localhost:8080/upload/null 404 (Not Found) 이게 뜨는 이유는 프로필 이미지가 없어서 뜨는거다 그러므로 프로필 이미지를 넣어주면 해결 

### ✔ 단방향 매핑 (Comment → Image)

Comment에서 Image를 참조할 수 있지만, Image에서는 Comment 목록을 모름.
부모(예: Image)에서 자식을 조회할 일이 없으면 단방향이 좋음.
✔ 양방향 매핑 (Comment ↔ Image)

Comment에서도 Image를 참조하고, Image에서도 Comment 목록을 알 수 있음.
부모(예: Image)에서 자식을 자주 조회해야 하면 양방향이 좋음.
즉, 양방향 매핑을 하면 부모도 자식을 알 수 있고, 단방향이면 자식만 부모를 알 수 있습니다

### 1:N 관계에서 부모-자식 개념
📌 부모(1) → 독립적으로 존재할 수 있는 엔터티
📌 자식(N) → 부모 없이는 의미가 없는 엔터티

예제: Image(1) ↔ Comment(N)

하나의 Image에는 여러 개의 Comment가 달릴 수 있음 (1:N 관계)
Comment는 반드시 Image를 참조해야 하지만, Image는 Comment 없이도 존재 가능
따라서 Image가 부모, Comment가 자식

### CustomException은 html 파일 반환 해주는 컨트롤러 , CustomApiException 데이터를 리턴해주는 컨트롤러, validation은 처음 값을 받을떄 그럴때 사용

### AOP (핵심 비즈니스 로직과 공통적인 부가 기능을 분리하여 관리하는 프로그래밍 기법)
