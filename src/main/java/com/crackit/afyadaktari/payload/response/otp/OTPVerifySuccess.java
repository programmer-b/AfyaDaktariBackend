package com.crackit.afyadaktari.payload.response.otp;

import com.crackit.afyadaktari.model.auth.OTP;
import com.crackit.afyadaktari.model.auth.User;
import com.crackit.afyadaktari.repository.OTPRepository;
import com.crackit.afyadaktari.repository.UserRepository;
import com.crackit.afyadaktari.service.jwt.GenerateToken;
import com.crackit.afyadaktari.service.update.UpdateOTP;
import com.crackit.afyadaktari.service.update.UpdateUser;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.Callable;

import static com.crackit.afyadaktari.utils.Constants.*;

public class OTPVerifySuccess implements Callable<ResponseEntity<?>> {

    private User user;
    private final UserRepository userRepository;
    private final OTPRepository otpRepository;

    JSONObject dataPayload = new JSONObject();
    JSONObject toastPayload = new JSONObject();
    JSONObject toast = new JSONObject();
    JSONObject data = new JSONObject();

    JSONObject response = new JSONObject();

    public OTPVerifySuccess(User user, UserRepository userRepository, OTPRepository otpRepository) {
        this.user = user;
        this.userRepository = userRepository;
        this.otpRepository = otpRepository;
    }

    @Override
    public ResponseEntity<?> call() {
        UpdateUser updateUser = new UpdateUser(userRepository, user, user.isProfileUpdated(), true);
        user = updateUser.call();

        final OTP otp = otpRepository.findByUserId(user.getId()).get();

        UpdateOTP updateOTP = new UpdateOTP(otpRepository, otp, true);

        updateOTP.call();

        final String token = new GenerateToken(user).call();

        toast.put(KEY_MESSAGE, OTP_VERIFICATION_SUCCESS_MESSAGE);
        data.put(KEY_TOKEN, token);

        dataPayload.put(KEY_DATA, data);
        toastPayload.put(KEY_TOAST, toast);

        response.put(KEY_DATA_PAYLOAD, dataPayload);
        response.put(KEY_TOAST_PAYLOAD, toastPayload);

        return new ResponseEntity<>(response.toMap() , HttpStatus.ACCEPTED);
    }
}
