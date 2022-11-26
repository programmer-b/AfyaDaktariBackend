package com.crackit.afyadaktari.payload.response.otp;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.Callable;

import static com.crackit.afyadaktari.payload.response.login.LoginErrors.getResponseEntity;
import static com.crackit.afyadaktari.utils.Constants.*;

public class OTPErrors implements Callable<ResponseEntity<?>> {
    private final String error;

    public OTPErrors(String error) {
        this.error = error;
    }

    JSONObject errorPayload = new JSONObject();
    JSONObject toastPayload = new JSONObject();
    JSONObject toast = new JSONObject();
    JSONObject errors = new JSONObject();

    JSONObject response = new JSONObject();

    @Override
    public ResponseEntity<?> call() {
        errors.put(KEY_OTP , error);
        return getResponseEntity(toast, error, errorPayload, errors, toastPayload, response);
    }
}
