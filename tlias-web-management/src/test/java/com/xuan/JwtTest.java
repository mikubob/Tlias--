package com.xuan;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {
    /**
     * 生成jwt令牌，
     */
    @Test
    public void testGenerate(){
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("id", 1);
        dataMap.put("username", "tom");
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, "eHVhbg==")//指定加密算法，密钥
                .addClaims(dataMap)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))//设置过期时间
                .compact();//生成jwt令牌
        System.out.println(jwt);
    }
    /**
     * 解析jwt令牌
     */
    @Test
    public void testParse(){
        String token="eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJ0b20iLCJleHAiOjE3NjI2OTI4MDB9.8aylPo-21a7-GC6NNWkjc31YWeyRCxgxmFzn3AFf-C0";
        Claims claims = Jwts.parser()//创建jwt解析器
                .setSigningKey("eHVhbg==")//指定密钥
                .parseClaimsJws(token)//解析jwt令牌
                .getBody();//获取数据
        System.out.println(claims);
    }
}
