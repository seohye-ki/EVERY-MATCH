package com.everymatch.mvc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.everymatch.mvc.intercepter.JwtInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer{

	private final JwtInterceptor jwtInterceptor;
	
	public WebConfig(JwtInterceptor jwtInterceptor) {
		this.jwtInterceptor = jwtInterceptor;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtInterceptor).addPathPatterns("/**")
			.excludePathPatterns("/api-user/**", "/swagger-ui/**", "/v3/api-docs.**");
	}
	
	
}