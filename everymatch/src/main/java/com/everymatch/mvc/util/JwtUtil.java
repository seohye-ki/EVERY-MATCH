package com.everymatch.mvc.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	private static final String SALT = "SSAFY_FINAL_PROJECT_EVERY_MATCH_LJH_KSH";
	private SecretKey secretKey = Keys.hmacShaKeyFor(SALT.getBytes(StandardCharsets.UTF_8));
	
	public String createToken(String claimId, String data) {
		
		Date exp = new Date(System.currentTimeMillis() + 1000*60*60);
		
		return Jwts.builder() 
				.expiration(exp)
				.claim("data", data)
				.signWith(secretKey)
				.compact();
	}
	
	public Jws<Claims> vaildate(String token) {
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
	}
}
