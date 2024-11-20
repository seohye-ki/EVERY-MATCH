package com.everymatch.mvc.util;
import java.util.Random;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    // 비밀번호 암호화
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    // 비밀번호 검증
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
    
    // 랜덤 비밀번호 생성
    public static String generateRandomPassword() {
    	 String charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%";
         StringBuilder password = new StringBuilder();
         Random random = new Random();
         for (int i = 0; i < 13; i++)
        	 password.append(charSet.charAt(random.nextInt(charSet.length())));
         
         return password.toString();
    }
}
