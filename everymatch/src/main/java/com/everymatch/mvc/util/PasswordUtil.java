package com.everymatch.mvc.util;
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
}
