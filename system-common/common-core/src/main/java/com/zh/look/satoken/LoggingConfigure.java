package com.zh.look.satoken;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoggingConfigure implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new CustomLoggingInterceptor())
                .addPathPatterns("/doctor/**")
                .addPathPatterns("/patients/**")
                .addPathPatterns("/login/logout");
}
}
