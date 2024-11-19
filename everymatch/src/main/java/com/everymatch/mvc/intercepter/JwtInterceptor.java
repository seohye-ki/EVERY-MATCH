package com.everymatch.mvc.intercepter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import com.everymatch.mvc.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements AsyncHandlerInterceptor{
	private final String HEADER_AUTH = "";
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
	
		//클라이언트의 사전 요청 
		if(request.getMethod().equals("OPTIONS"))
				return true;
		
		String token =  request.getHeader(HEADER_AUTH);
		if(token != null) {
			jwtUtil.vaildate(token);
			return true;
		}
		throw new Exception("유효하지 않은 접근입니다.");
	}
}
