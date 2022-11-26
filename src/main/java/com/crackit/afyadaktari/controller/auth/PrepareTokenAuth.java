package com.crackit.afyadaktari.controller.auth;

import com.crackit.afyadaktari.service.jwt.AuthEntryPoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.concurrent.Callable;

public class PrepareTokenAuth implements Callable<ResponseEntity<?>> {
    private final String token;
    private boolean otpNullable = false;
    private  Map<String,String> otp = null;

    public PrepareTokenAuth(String token){
        this.token = token;
        this.otpNullable = true;
    }
    public PrepareTokenAuth(String token, Map<String, String> otp) {
        this.token = token;
        this.otp = otp;
    }

    @Override
    public ResponseEntity<?> call(){
        if(this.otp == null && !otpNullable){
            String uglyResponse = "REQUEST BODY IS NULL : THIS MUST BE AN ADMIN MESSING WITH MY MIND \uD83D\uDE24 \uD83D\uDE20";
            System.out.println(uglyResponse);
            return new ResponseEntity<>(uglyResponse , HttpStatus.BAD_REQUEST);
        }

        AuthEntryPoint authEntryPoint = new AuthEntryPoint(token);
        if(authEntryPoint.call() != null){
            return authEntryPoint.call();
        }
        return null;
    }
}
