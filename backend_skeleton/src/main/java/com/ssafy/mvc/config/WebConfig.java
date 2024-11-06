package com.ssafy.mvc.config;

import com.ssafy.mvc.intercepter.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

public class WebConfig {
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/api/**") // 보호할 경로 설정 (예: /api/** 경로)
                .excludePathPatterns("/api/users/login", "/api/users/register"); // 인증 없이 접근 가능한 경로
    }
}
