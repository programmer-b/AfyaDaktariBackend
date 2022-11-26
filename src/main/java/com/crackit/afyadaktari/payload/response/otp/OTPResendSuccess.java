package com.crackit.afyadaktari.payload.response.otp;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.Callable;

import static com.crackit.afyadaktari.utils.Constants.*;

public class OTPResendSuccess implements Callable<ResponseEntity<?>> {
    private final String mobile;

    public OTPResendSuccess(String mobile) {
        this.mobile = mobile;
    }

    JSONObject dataPayload = new JSONObject();
    JSONObject toastPayload = new JSONObject();
    JSONObject toast = new JSONObject();
    JSONObject data = new JSONObject();

    JSONObject response = new JSONObject();


    @Override
    public ResponseEntity<?> call() {
        data.put(KEY_MOBILE, this.mobile);
        data.put(KEY_MESSAGE,OTP_RESEND_SUCCESS_MESSAGE);

        toast.put(KEY_TOAST, OTP_RESEND_SUCCESS_MESSAGE);

        dataPayload.put(KEY_DATA, data);
        toastPayload.put(KEY_TOAST, toast);

        response.put(KEY_TOAST_PAYLOAD, toastPayload);
        response.put(KEY_DATA_PAYLOAD, dataPayload);

        return new ResponseEntity<>(response.toMap(), HttpStatus.OK);
    }
}
