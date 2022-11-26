package com.crackit.afyadaktari.controller.auth.otp;

import com.crackit.afyadaktari.controller.auth.PrepareTokenAuth;
import com.crackit.afyadaktari.model.auth.User;
import com.crackit.afyadaktari.payload.response.otp.OTPErrors;
import com.crackit.afyadaktari.payload.response.otp.OTPVerifySuccess;
import com.crackit.afyadaktari.repository.OTPRepository;
import com.crackit.afyadaktari.repository.UserRepository;
import com.crackit.afyadaktari.utils.EndPoints;
import com.crackit.afyadaktari.utils.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

import static com.crackit.afyadaktari.service.jwt.TokenManager.getUserIdFromToken;
import static com.crackit.afyadaktari.utils.Constants.KEY_OTP;

@RestController
@RequestMapping(value = EndPoints.VERIFY_OTP)
public class OTPVerify {
    private final OTPRepository otpRepository;
    private final UserRepository userRepository;

    public OTPVerify(OTPRepository otpRepository, UserRepository userRepository) {
        this.otpRepository = otpRepository;
        this.userRepository = userRepository;
    }

    @PostMapping()
    public ResponseEntity<?> verifyOTP(@RequestHeader(value = "Authorization" , required = false) String token, @RequestBody(required = false) Map<String,String> otp){
        PrepareTokenAuth prepareTokenAuth = new PrepareTokenAuth(token,otp);
        if(prepareTokenAuth.call() != null){
            return prepareTokenAuth.call();
        }

        final String otpString = otp.get(KEY_OTP);
        System.out.println("OTP : "+otpString);

        final String otpError = StringUtils.getOTPError(getUserIdFromToken(token), otpString, otpRepository);
        if(otpError != null){
            OTPErrors errors = new OTPErrors(otpError);
            return errors.call();
        }
        final Long userId = getUserIdFromToken(token);
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            System.out.println("THIS USER DOES NOT EXIST. I DONT KNOW HOW IT EVEN GOT HERE!!!");
            return null;
        }
        return new OTPVerifySuccess(user.get(), userRepository, otpRepository).call();
    }
}
