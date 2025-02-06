package com.example.photogram.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration //ioc 등록
public class WebMvcConfig implements WebMvcConfigurer { // web 설정파일


    @Value("${file.path}")
    private String uploadFolder; //파일 경로

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);

        // 이 코드를 재사용할떄는 다른거는 건들필요 없고 addResourceHandler,addResourceLocations 이렇게 두가지만 수정해주면 된다.
        //file:///C:/workspace/springbootwork/upload/ 이주소처럼 변경 해줌
        registry
                .addResourceHandler("/upload/**")//jsp 페이지에서 /upload/** 이런 주소패턴이 나오면
                .addResourceLocations("file:///"+uploadFolder)// 이녀석이 발동한다
                .setCachePeriod(60*10*6) //1시간
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }
}
