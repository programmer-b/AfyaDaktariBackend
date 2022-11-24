package com.crackit.afyadaktari.payload.response;

import com.crackit.afyadaktari.utils.Constants;
import org.json.JSONObject;

import java.util.concurrent.Callable;

public class RegisterErrors implements Callable<JSONObject> {
    public String usernameError;
    public String mobileError;
    public String passwordError;
    public String confirmPasswordError;

    public RegisterErrors(String usernameError, String mobileError, String passwordError, String confirmPasswordError) {
        this.usernameError = usernameError;
        this.mobileError = mobileError;
        this.passwordError = passwordError;
        this.confirmPasswordError = confirmPasswordError;
    }

    private JSONObject errorMap = null;


    @Override
    public JSONObject call() {
        if(usernameError != null || mobileError != null || passwordError != null || confirmPasswordError != null){
            errorMap = new JSONObject();
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
            errorMap.put("ErrorPayload", new JSONObject().put("errors" , errorsMap)).put("message", "bad credentials");
        }
        return errorMap;
    }
}
