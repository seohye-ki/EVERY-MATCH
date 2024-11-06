package com.everymatch.mvc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	//회원가입
	@PostMapping("/regist")
	
	
	//로그인
	@PostMapping("/login")
	
	
	//마이페이지 - 회원정보 상세
	@GetMapping("/detail")
	
	
	//회원정보 수정
	@PostMapping("/update")
	
	
	//비밀번호 수정
	@PostMapping("/change")
	
	
	//회원탈퇴
	@GetMapping("/delete")
	
	
	//이메일 중복체크
	@PostMapping("/checkemail")
	
	
	//아이디 중복체크
	@PostMapping("/checkid")
	
	
	//비밀번호 찾기
	@PostMapping("/find")	

}
