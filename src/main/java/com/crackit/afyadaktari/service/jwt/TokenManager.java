package com.crackit.afyadaktari.service.jwt;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.crackit.afyadaktari.model.auth.User;
import com.crackit.afyadaktari.utils.StringUtils;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims; import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import static com.crackit.afyadaktari.utils.Constants.*;

@Component
public class TokenManager implements Serializable {
    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 7008375124389347049L;
    public static String generateJwtToken(User user) {
        Map<String, Object> claims = new HashMap<>();

        claims.put(KEY_USERNAME, user.getUsername());
        claims.put(KEY_MOBILE,user.getMobile());
        claims.put(KEY_USER_ID, user.getId());

        return Jwts.builder().setClaims(claims).setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET).compact();
    }
    public static Boolean isTokenValid(String token) {
        token = token.substring(7);
        try{
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public static Claims JwtClaims(String token){
        token = token.substring(7);
        return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
    }

    public static Long getUserIdFromToken(String token){
        return Long.valueOf(JwtClaims(token).get(KEY_USER_ID).toString());
    }

    public static String getMobileFromToken(String token){
        return JwtClaims(token).get(KEY_MOBILE).toString();
    }

    public static Long getExpiryTimeStampFromToken(String token){
        final Date date = JwtClaims(token).getExpiration();
        return new Timestamp(date.getTime()).getTime();
    }

    public static Boolean isTokenExpired(String token){
        return StringUtils.generateCurrentTimeStamp() > getExpiryTimeStampFromToken(token);
    }

}
