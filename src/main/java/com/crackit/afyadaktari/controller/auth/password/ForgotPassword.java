package com.crackit.afyadaktari.controller.auth.password;

import com.crackit.afyadaktari.model.auth.OTP;
import com.crackit.afyadaktari.model.auth.User;
import com.crackit.afyadaktari.payload.response.password.forgot.ForgotPasswordErrors;
import com.crackit.afyadaktari.payload.response.password.forgot.ForgotPasswordSuccess;
import com.crackit.afyadaktari.repository.OTPRepository;
import com.crackit.afyadaktari.repository.UserRepository;
import com.crackit.afyadaktari.utils.EndPoints;
import com.crackit.afyadaktari.utils.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

import static com.crackit.afyadaktari.utils.Constants.KEY_MOBILE;
import static com.crackit.afyadaktari.utils.Constants.REGISTER_PHONE_VERIFICATION_TIMEOUT;

@RestController
@RequestMapping(value = EndPoints.PASSWORD_FORGOT)
public class ForgotPassword {

    private final UserRepository userRepository;
    private final OTPRepository otpRepository;

    public ForgotPassword(UserRepository userRepository, OTPRepository otpRepository) {
        this.userRepository = userRepository;
        this.otpRepository = otpRepository;
    }

    @PostMapping
    public ResponseEntity<?> forgotPassword(@RequestBody(required = false) Map<String,String> forgotPasswordDto){

        final String mobile = forgotPasswordDto.get(KEY_MOBILE);
        final String mobileError = StringUtils.getForgotPasswordError(mobile, userRepository);

        ForgotPasswordErrors errors = new ForgotPasswordErrors(mobileError);
        if(errors.call() != null){
            return errors.call();
        }

        final Optional<OTP> optionalOTP = otpRepository.findByMobile(StringUtils.getPhoneCorrFormat(mobile));
        OTP otp = optionalOTP.get();

        final Long otp1 = StringUtils.generateOTP();

        ///Write code here that will send an otp to the users phone number

        otp.setOtp(otp1);
        otp.setUserId(otp.getUserId());
        otp.setMobile(otp.getMobile());
        otp.setCreatedAt(StringUtils.generateCurrentTimeStamp());
        otp.setExpiry(StringUtils.generateCurrentTimeStamp() + REGISTER_PHONE_VERIFICATION_TIMEOUT);
        otp.setOtpIsUsed(false);

        otpRepository.save(otp);

        final User user = userRepository.findById(otp.getUserId()).get();

        ForgotPasswordSuccess success = new ForgotPasswordSuccess(user);
        return success.call();
    }
}
