package com.crackit.afyadaktari.service.jwt;

import com.crackit.afyadaktari.utils.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

public class JwtUtils {
//
//    //Sample method to construct a JWT
//    private final String secret = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";
//    private final Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret), SignatureAlgorithm.HS256.getJcaName());
//
//    public String generateJWT(Long userId, String username, String mobile) {
//        System.out.println( "SECRET   "+secret);
//
//        return Jwts.builder().setIssuer("CrackIT").setSubject("JWT Secret Token").claim(Constants.KEY_USERNAME, username).claim(Constants.KEY_MOBILE, mobile).claim(Constants.KEY_USER_ID, userId).setId(userId.toString()).setExpiration(Date.from(Instant.ofEpochSecond(860000000L))).signWith(hmacKey).compact();
//    }
//
//    public  Jws<Claims> parseJwt(String jwtString) {
//        try{
//            return Jwts.parserBuilder().setSigningKey(hmacKey).build().parseClaimsJws(jwtString);
//        } catch (Exception e){
//
//        }
//    }
}
