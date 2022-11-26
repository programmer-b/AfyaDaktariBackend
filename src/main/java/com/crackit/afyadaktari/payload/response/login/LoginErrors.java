package com.crackit.afyadaktari.payload.response.login;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.Callable;

import static com.crackit.afyadaktari.utils.Constants.*;

public class LoginErrors implements Callable<ResponseEntity<?>> {
    private final String usernameOrPasswordError;
    private final String passwordError;

    public LoginErrors(String usernameOrPasswordError, String passwordError) {
        this.usernameOrPasswordError = usernameOrPasswordError;
        this.passwordError = passwordError;
    }

    JSONObject errorPayload = new JSONObject();
    JSONObject toastPayload = new JSONObject();
    JSONObject errors = new JSONObject();
    JSONObject toast = new JSONObject();
    JSONObject response = new JSONObject();

    @Override
    public ResponseEntity<?> call() {
        if(passwordError != null || usernameOrPasswordError != null){
            if(usernameOrPasswordError != null){
                errors.put(KEY_USERNAME_OR_MOBILE, usernameOrPasswordError);
            }
            if(passwordError != null){
                errors.put(KEY_PASSWORD, passwordError);
            }
            return getResponseEntity(toast, BAD_CREDENTIALS_MESSAGE, errorPayload, errors, toastPayload, response);
        }
        return null;
    }

    public static ResponseEntity<?> getResponseEntity(JSONObject toast, String badCredentialsMessage, JSONObject errorPayload, JSONObject errors, JSONObject toastPayload, JSONObject response) {
        toast.put(KEY_MESSAGE, badCredentialsMessage);

        errorPayload.put(KEY_ERRORS, errors);
        toastPayload.put(KEY_TOAST, toast);

        response.put(KEY_ERROR_PAYLOAD, errorPayload);
        response.put(KEY_TOAST_PAYLOAD, toastPayload);

        return new ResponseEntity<>(response.toMap(), HttpStatus.BAD_REQUEST);
    }
}
