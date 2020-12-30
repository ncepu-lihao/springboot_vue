package com.li.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.li.config.TpConfig;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.impl.crypto.MacProvider;

/**
 * Created by Chilly Cui on 2020/9/9.
 */
@Slf4j
public class JwtUtil {
    /**
     * token 过期时间, 单位: 分钟
     */
//    private static final long TOKEN_EXPIRED_TIME = 30 * 24 * 60 ;
    private static long TOKEN_EXPIRED_TIME = ((TpConfig) ApplicationContextUtils.getApplicationContext("tpConfig")).getJwtExpired();
//    private static final long TOKEN_EXPIRED_TIME = 2 ;

    public static final String JWT_ID_STR = "userid";
    public static final String JWT_NAME_STR = "username";

    public static final String JWT_ROLE_STR = "userrole";
    public static final String JWT_ORGAN_STR = "organid";

    public static final Key KEY = MacProvider.generateKey();


    /**
     * jwt 加密解密密钥
     */
    private static final String JWT_SECRET = "MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY=";

    public static final String JWT_ID = "tokenId";

    /**
     * 创建JWT
     *
     * @param claims
     * @param time   单位：秒
     * @return
     */
    public static String createJWT(Map<String, Object> claims, Long time) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; //指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        Date now = new Date(System.currentTimeMillis());

        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();//生成JWT的时间
        //下面就是在为payload添加各种标准声明和私有声明了
        JwtBuilder builder = Jwts.builder() //这里其实就是new一个JwtBuilder，设置jwt的body
                .setClaims(claims)          //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setId(JWT_ID)                  //设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setIssuedAt(now)           //iat: jwt的签发时间
                .signWith(signatureAlgorithm, secretKey);//设置签名使用的签名算法和签名使用的秘钥
        if (time >= 0) {
            long expMillis = nowMillis + time * 60 * 1000;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);     //设置过期时间
        }
      // builder.compact() 报错 压缩token
         return builder.compact();
    }

    /**
     * 验证jwt
     */
    public static Claims verifyJwt(String token) {
        //签名秘钥，和生成的签名的秘钥一模一样
        SecretKey key = generalKey();
        Claims claims;
        try {
            claims = Jwts.parser()  //得到DefaultJwtParser
                    .setSigningKey(key)         //设置签名的秘钥
                    .parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException expiredJwtException) {
            log.info("token过期");
            return null;
        } catch (Exception e) {
            log.info("token失效", e);
            return null;
        }//设置需要解析的jwt
        return claims;

    }


    /**
     * 由字符串生成加密key
     *
     * @return
     */
    public static SecretKey generalKey() {
        String stringKey = JWT_SECRET;
        byte[] encodedKey = Base64.decodeBase64(stringKey);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 根据userId和openid生成token
     */
    public static String generateToken(String role, Integer userId, String username, Integer organId) {
        Map<String, Object> map = new HashMap<>(4);
        map.put(JWT_ID_STR, userId);
        map.put(JWT_NAME_STR, username);
        map.put(JWT_ROLE_STR, role);
        map.put(JWT_ORGAN_STR, organId);
        return createJWT(map, TOKEN_EXPIRED_TIME);
    }

    /**
     * 获取jwt的payload部分
     */
    public Claims getClaimFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 获取jwt失效时间
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token).getExpiration();
    }

    /**
     * 判断是否过期
     *
     * @param token
     * @return
     */
    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * 验新证token
     */
    public static DecodedJWT verify(String token) {
        //如果有任何验证异常，此处都会抛出异常
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(JWT_SECRET)).build().verify(token);
        return decodedJWT;
    }



}
