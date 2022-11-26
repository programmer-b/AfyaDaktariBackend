package com.crackit.afyadaktari.service.jwt;

import com.crackit.afyadaktari.model.auth.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import static com.crackit.afyadaktari.utils.Constants.*;

public class GenerateToken implements Callable<String> {
    private final User user;

    public GenerateToken(User user){
        this.user = user;

    }


    @Override
    public String call() {
        Map<String, Object> claims = new HashMap<>();

        claims.put(KEY_USERNAME, user.getUsername());
        claims.put(KEY_MOBILE,user.getMobile());
        claims.put(KEY_USER_ID, user.getId());
        claims.put(KEY_PROFILE_UPDATED, user.isProfileUpdated());
        claims.put(KEY_MOBILE_VERIFIED, user.isMobileVerified());

        return Jwts.builder().setClaims(claims).setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET).compact();
    }
}