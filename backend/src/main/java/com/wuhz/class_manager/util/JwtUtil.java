package com.wuhz.class_manager.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component 
public class JwtUtil {
    // 使用更安全的密钥处理方式
    private static final String SECRET_STRING = "your_very_long_and_secure_secret_key_at_least_256_bits"; // 至少32字符
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET_STRING.getBytes(StandardCharsets.UTF_8));
    
    // 生成Token
    public static String generateToken(String teacherID) {
        return Jwts.builder()
                .subject(teacherID)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000)) // 1天有效
                .signWith(SECRET_KEY)  // 自动选择最佳算法
                .compact();
    }

    // 解析Token获取教师ID
    public static String getTeacherID(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }
}