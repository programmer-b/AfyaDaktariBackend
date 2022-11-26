package com.crackit.afyadaktari.payload.response.register;

import com.crackit.afyadaktari.model.auth.User;
import com.crackit.afyadaktari.service.jwt.GenerateToken;
import com.crackit.afyadaktari.service.jwt.JwtUtils;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.Callable;

import static com.crackit.afyadaktari.utils.Constants.*;

public class RegisterSuccess implements Callable<ResponseEntity<?>> {
    public User user;
    JSONObject dataPayload = new JSONObject();
    JSONObject toastPayload = new JSONObject();
    JSONObject data = new JSONObject();
    JSONObject toast = new JSONObject();
    JSONObject response = new JSONObject();
    public RegisterSuccess(User user) {
       this.user = user;
    }

    @Override
    public ResponseEntity<?> call() {

        final String token = new GenerateToken(user).call();

        data.put(KEY_USER_ID , user.getId());
        data.put(KEY_TOKEN,token);
        data.put(KEY_OTP_VALIDITY, REGISTER_PHONE_VERIFICATION_TIMEOUT);

        toast.put(KEY_MESSAGE, REGISTER_SUCCESS_MESSAGE);

        dataPayload.put(KEY_DATA, data);
        toastPayload.put(KEY_TOAST, toast);


        response.put(KEY_DATA_PAYLOAD , dataPayload);
        response.put(KEY_TOAST_PAYLOAD, toastPayload);

        return new ResponseEntity<>(response.toMap(), HttpStatus.CREATED);
    }
}