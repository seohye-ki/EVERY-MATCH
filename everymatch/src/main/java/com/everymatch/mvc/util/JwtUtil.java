package com.everymatch.mvc.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private static final String SALT = "SSAFY_FINAL_PROJECT_EVERY_MATCH_LJH_KSH";
	private final SecretKey secretKey = Keys.hmacShaKeyFor(SALT.getBytes(StandardCharsets.UTF_8));

	public String createToken(String claimKey, String data) {
		Date now = new Date();
		Date exp = new Date(now.getTime() + 1000 * 60 * 60); // 1시간

		return Jwts.builder()
				.expiration(exp) // 만료 시간
				.claim(claimKey, data) // 사용자 정의 클레임 추가
				.signWith(secretKey) // 서명
				.compact();
	}

	public Claims validateClaims(String token) {
		try {
			return Jwts.parser()
					.verifyWith(secretKey)
					.build()
					.parseSignedClaims(token).getPayload();
		} catch (Exception e) {
			throw new IllegalArgumentException("유효하지 않은 JWT 토큰입니다.", e);
		}
	}

	public String extractClaim(String token, String claimKey) {
		Claims claims = validateClaims(token);
		return claims.get(claimKey, String.class);
	}
}
