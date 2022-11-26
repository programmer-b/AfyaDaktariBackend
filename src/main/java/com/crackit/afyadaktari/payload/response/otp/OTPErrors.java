package com.crackit.afyadaktari.payload.response.otp;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.Callable;

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
        toast.put(KEY_MESSAGE , error);

        errorPayload.put(KEY_ERRORS , errors);
        toastPayload.put(KEY_TOAST , toast);

        response.put(KEY_ERROR_PAYLOAD, errorPayload);
        response.put(KEY_TOAST_PAYLOAD, toastPayload);

        return new ResponseEntity<>(response.toMap(), HttpStatus.BAD_REQUEST);
    }
}
