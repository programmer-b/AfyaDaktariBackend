package com.crackit.afyadaktari.service.jwt;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.Callable;

import static com.crackit.afyadaktari.service.jwt.TokenManager.isTokenExpired;
import static com.crackit.afyadaktari.service.jwt.TokenManager.isTokenValid;
import static com.crackit.afyadaktari.utils.Constants.*;

public class AuthEntryPoint implements Callable<ResponseEntity<?>> {
    private final String token;
    public AuthEntryPoint(String token) {
        this.token = token;
    }
    JSONObject errorPayload = new JSONObject();
    JSONObject toastPayload = new JSONObject();
    JSONObject toast = new JSONObject();

    @Override
    public ResponseEntity<?> call() {
        if(token == null){
            return getResponseEntity(EMPTY_TOKEN_MESSAGE);
        }
        if(isTokenValid(token)){
            if(isTokenExpired(token)){
                return getResponseEntity(EXPIRED_TOKEN_MESSAGE);
            }
            return null;
        }
        return getResponseEntity(INVALID_TOKEN_MESSAGE);
    }

    private ResponseEntity<?> getResponseEntity(String toastMessage) {
        toast.put(KEY_MESSAGE, toastMessage);

        toastPayload.put(KEY_TOAST, toast);

        JSONObject response = new JSONObject();

        response.put(KEY_ERROR_PAYLOAD , errorPayload);
        response.put(KEY_TOAST_PAYLOAD, toastPayload);

        return new ResponseEntity<>(response.toMap(), HttpStatus.UNAUTHORIZED);
    }
}
