package com.crackit.afyadaktari.controller.auth.otp;

import com.crackit.afyadaktari.controller.auth.PrepareTokenAuth;
import com.crackit.afyadaktari.model.auth.OTP;
import com.crackit.afyadaktari.payload.response.otp.OTPResendSuccess;
import com.crackit.afyadaktari.repository.OTPRepository;
import com.crackit.afyadaktari.service.jwt.TokenManager;
import com.crackit.afyadaktari.service.update.UpdateOTP;
import com.crackit.afyadaktari.utils.EndPoints;
import com.crackit.afyadaktari.utils.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.crackit.afyadaktari.utils.Constants.REGISTER_PHONE_VERIFICATION_TIMEOUT;

@RestController
@RequestMapping(value = EndPoints.RE_SEND_OTP)
public class OTPResend {
    private final OTPRepository otpRepository;

    public OTPResend(OTPRepository otpRepository) {
        this.otpRepository = otpRepository;
    }

    @PostMapping()
    public ResponseEntity<?> resendOTP(@RequestHeader(value = "Authorization" , required = false) String token){
        PrepareTokenAuth prepareTokenAuth = new PrepareTokenAuth(token);
        if(prepareTokenAuth.call() != null){
            return prepareTokenAuth.call();
        }

        final String mobile = TokenManager.getMobileFromToken(token);

        final Long userId = TokenManager.getUserIdFromToken(token);

        final OTP otp1 = otpRepository.findByUserId(userId).get();

        final Long otpCode = StringUtils.generateOTP();
        ///code to send an otp to the users mobile number

        UpdateOTP updateOTP = new UpdateOTP(otpRepository, otp1, false, StringUtils.generateCurrentTimeStamp(), StringUtils.generateCurrentTimeStamp() + REGISTER_PHONE_VERIFICATION_TIMEOUT, otpCode);
        updateOTP.call();

        OTPResendSuccess otpResendSuccess = new OTPResendSuccess(mobile);
        return otpResendSuccess.call();
    }

}
