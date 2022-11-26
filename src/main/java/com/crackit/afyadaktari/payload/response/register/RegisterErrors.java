package com.crackit.afyadaktari.payload.response.register;

import com.crackit.afyadaktari.utils.Constants;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.Callable;

public class RegisterErrors implements Callable<ResponseEntity<?>> {
    private final String usernameError;
    private final String mobileError;
    private final String passwordError;
    private final String confirmPasswordError;

    public RegisterErrors(String usernameError, String mobileError, String passwordError, String confirmPasswordError) {
        this.usernameError = usernameError;
        this.mobileError = mobileError;
        this.passwordError = passwordError;
        this.confirmPasswordError = confirmPasswordError;
    }


    @Override
    public ResponseEntity<?> call() {
        if(usernameError != null || mobileError != null || passwordError != null || confirmPasswordError != null){
            JSONObject errorMap = new JSONObject();
            JSONObject errorsMap = new JSONObject();

            if(usernameError != null){
                errorsMap.put(Constants.KEY_USERNAME , usernameError);
            }
            if(mobileError != null){
                errorsMap.put(Constants.KEY_MOBILE , mobileError);
            }
            if(passwordError != null){
                errorsMap.put(Constants.KEY_PASSWORD , passwordError);
            }
            if(confirmPasswordError != null){
                errorsMap.put(Constants.KEY_CONFIRM_PASSWORD , confirmPasswordError);
            }
            errorMap.put("ErrorPayload", new JSONObject().put("errors" , errorsMap)).put("ToastPayload", new JSONObject().put("toast", "Bad credentials"));

            return new ResponseEntity<>(errorMap.toMap(), HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
