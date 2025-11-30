package com.xuan.utils;

import io.jsonwebtoken.*;
import java.util.Date;
import java.util.Map;

/**
 * JWT令牌操作工具类
 */
public class JwtUtils {
    
    // 设置密钥，与测试类中一致
    private static final String SECRET_KEY = "eHVhbg==";
    
    // 设置过期时间为12小时
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 12;
    
    /**
     * 生成JWT令牌
     * 
     * @param claims 要包含在令牌中的数据
     * @return 生成的JWT令牌字符串
     */
    public static String generateJwt(Map<String, Object> claims) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .addClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .compact();
    }
    
    /**
     * 解析JWT令牌
     * 
     * @param token 要解析的JWT令牌
     * @return 解析后的Claims对象
     */
    public static Claims parseJwt(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
    
    /**
     * 验证JWT令牌是否有效（未过期且未被篡改）
     * 
     * @param token 要验证的JWT令牌
     * @return 如果令牌有效返回true，否则返回false
     */
    public static boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException |
                 UnsupportedJwtException | IllegalArgumentException e) {
            // 令牌被篡改、格式错误、已过期、不支持或非法参数都会导致验证失败
            return false;
        }
    }
    
    /**
     * 刷新JWT令牌（只有在原令牌有效时才刷新）
     * 
     * @param token 原JWT令牌
     * @return 刷新后的JWT令牌，如果原令牌无效则返回null
     */
    public static String refreshToken(String token) {
        try {
            // 解析原令牌
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);
            
            // 获取原令牌中的数据
            Claims claims = claimsJws.getBody();
            
            // 检查原令牌是否即将过期（例如还剩1小时内过期）
            Date expiration = claims.getExpiration();
            Date now = new Date();
            long timeToExpire = expiration.getTime() - now.getTime();
            
            // 如果原令牌还有效且未接近过期时间，则不需要刷新
            // 这里设定为1小时内过期才需要刷新
            if (timeToExpire > 60 * 60 * 1000) {
                return token; // 原令牌还有效且不需要刷新，直接返回原令牌
            }
            
            // 生成新令牌
            return Jwts.builder()
                    .setClaims(claims) // 复用原令牌中的数据
                    .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .compact();
        } catch (Exception e) {
            // 原令牌无效（过期或被篡改），返回null表示需要重新登录
            return null;
        }
    }
    
    /**
     * 强制刷新JWT令牌（无论原令牌是否即将过期都刷新）
     * 
     * @param token 原JWT令牌
     * @return 刷新后的JWT令牌，如果原令牌无效则返回null
     */
    public static String forceRefreshToken(String token) {
        try {
            // 解析原令牌
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);
            
            // 获取原令牌中的数据
            Claims claims = claimsJws.getBody();
            
            // 生成新令牌
            return Jwts.builder()
                    .setClaims(claims) // 复用原令牌中的数据
                    .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .compact();
        } catch (Exception e) {
            // 原令牌无效（过期或被篡改），返回null表示需要重新登录
            return null;
        }
    }
}