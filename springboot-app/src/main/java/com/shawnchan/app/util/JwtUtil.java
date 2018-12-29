package com.shawnchan.app.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    /**
     * token过期时间，单位：秒，当前有效期1天
     */
    private static final long TOKEN_EXPIRED_TIME = 24 * 60 * 60;
    /**
     * jwt的唯一标识，这个可以设置为一个不重复的值，主要用来作为一次性token，回避重放攻击。
     */
    public static final String JWT_ID = "tokenId";
    /**
     * jwt加密解密密钥
     */
    private static final String JWT_SECRET = "YWxsYSIsImV4cCI6MT";
    /**
     * 创建jwt
     */
    public static String createJWT(Map<String, Object> claims, Long time) {
        //指定签名的时候使用的签名算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Date now = new Date(System.currentTimeMillis());
        SecretKey secretKey = generalKey();
        //生成JWT的时间
        long nowMillis = System.currentTimeMillis();
        //为payload添加各种标准声明和私有声明
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setId(JWT_ID)
                //jwt的签发时间
                .setIssuedAt(now)
                //设置签名使用的签名算法和签名使用的秘钥
                .signWith(signatureAlgorithm,secretKey);
        if (time >= 0) {
            long expMillis = nowMillis + time;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    /**
     * 验证jwt
     */
    public static Claims verifyJwt(String token) {
        //签名秘钥，和生成的签名的秘钥必须一样
        SecretKey key = generalKey();
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * 由字符串生成加密key
     */
    public static SecretKey generalKey() {
        String stringKey = JWT_SECRET;
        byte[] encodedKey = Base64.decodeBase64(stringKey);
        SecretKey key = new SecretKeySpec(encodedKey, 0,encodedKey.length, "AES");
        return key;
    }

    /**
     * 根据username和password生成token
     */
    public static String generateToken(String username, String password) {
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        return createJWT(map, TOKEN_EXPIRED_TIME);
    }

}
