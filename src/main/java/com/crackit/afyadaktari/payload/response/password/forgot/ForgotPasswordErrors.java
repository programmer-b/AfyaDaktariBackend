package com.crackit.afyadaktari.payload.response.password.forgot;

import org.json.HTTP;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.Callable;

import static com.crackit.afyadaktari.utils.Constants.*;

public class ForgotPasswordErrors implements Callable<ResponseEntity<?>> {
    private final String error;

    JSONObject errorPayload = new JSONObject();
    JSONObject toastPayload = new JSONObject();
    JSONObject errors = new JSONObject();
    JSONObject toast = new JSONObject();
    JSONObject response = new JSONObject();

    public ForgotPasswordErrors(String error) {
        this.error = error;
    }

    @Override
    public ResponseEntity<?> call() {
        if(error != null){
            errors.put(KEY_MOBILE, error);
            toast.put(KEY_MESSAGE, BAD_CREDENTIALS_MESSAGE);

            errorPayload.put(KEY_ERRORS, errors);
            toastPayload.put(KEY_TOAST, toast);

            response.put(KEY_ERROR_PAYLOAD, errorPayload);
            response.put(KEY_TOAST_PAYLOAD, toastPayload);

            return new ResponseEntity<>(response.toMap(), HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
