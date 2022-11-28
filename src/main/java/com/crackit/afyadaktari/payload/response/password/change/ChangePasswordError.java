package com.crackit.afyadaktari.payload.response.password.change;


import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.Callable;

import static com.crackit.afyadaktari.utils.Constants.*;
import static com.crackit.afyadaktari.utils.Constants.KEY_TOAST_PAYLOAD;

public class ChangePasswordError implements Callable<ResponseEntity<?>> {
    private final String currentPasswordError;
    private final String newPasswordError;
    private final String confirmPasswordError;
    JSONObject errorPayload = new JSONObject();
    JSONObject toastPayload = new JSONObject();
    JSONObject errors = new JSONObject();
    JSONObject toast = new JSONObject();
    JSONObject response = new JSONObject();

    public ChangePasswordError(String currentPasswordError, String newPasswordError, String confirmPasswordError) {
        this.currentPasswordError = currentPasswordError;
        this.newPasswordError = newPasswordError;
        this.confirmPasswordError = confirmPasswordError;
    }

    @Override
    public ResponseEntity<?> call() {
        if(newPasswordError != null || confirmPasswordError != null || currentPasswordError != null){
            if(currentPasswordError != null){
                errors.put(KEY_CURRENT_PASSWORD, currentPasswordError);
            }
            if(newPasswordError != null){
                errors.put(KEY_NEW_PASSWORD, newPasswordError);
            }

            if(confirmPasswordError != null){
                errors.put(KEY_CONFIRM_PASSWORD, confirmPasswordError);
            }
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
