package com.crackit.afyadaktari.payload.response.password.forgot;

import com.crackit.afyadaktari.model.auth.User;
import com.crackit.afyadaktari.service.jwt.GenerateToken;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.Callable;

import static com.crackit.afyadaktari.utils.Constants.*;
import static com.crackit.afyadaktari.utils.Constants.KEY_TOAST_PAYLOAD;

public class ForgotPasswordSuccess implements Callable<ResponseEntity<?>> {
    private final User user;

    JSONObject dataPayload = new JSONObject();
    JSONObject toastPayload = new JSONObject();
    JSONObject data = new JSONObject();
    JSONObject toast = new JSONObject();
    JSONObject response = new JSONObject();

    public ForgotPasswordSuccess(User user) {
        this.user = user;
    }

    @Override
    public ResponseEntity<?> call()  {
        final String token = new GenerateToken(user).call();

        data.put(KEY_USER_ID , user.getId());
        data.put(KEY_TOKEN,token);
        data.put(KEY_OTP_VALIDITY, FORGOT_PASS_PHONE_VERIFICATION_TIMEOUT);

        toast.put(KEY_MESSAGE, FORGOT_PASSWORD_REQ_SUCCESS);

        dataPayload.put(KEY_DATA, data);
        toastPayload.put(KEY_TOAST, toast);


        response.put(KEY_DATA_PAYLOAD , dataPayload);
        response.put(KEY_TOAST_PAYLOAD, toastPayload);

        return new ResponseEntity<>(response.toMap(), HttpStatus.ACCEPTED);
    }
}
