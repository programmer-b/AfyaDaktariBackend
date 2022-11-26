package com.crackit.afyadaktari.controller.auth;

import com.crackit.afyadaktari.model.auth.OTP;
import com.crackit.afyadaktari.model.auth.User;
import com.crackit.afyadaktari.payload.request.RegisterDto;
import com.crackit.afyadaktari.payload.response.register.RegisterErrors;
import com.crackit.afyadaktari.payload.response.register.RegisterSuccess;
import com.crackit.afyadaktari.repository.OTPRepository;
import com.crackit.afyadaktari.repository.UserRepository;
import com.crackit.afyadaktari.service.password.PasswordUtils;
import com.crackit.afyadaktari.utils.EndPoints;
import com.crackit.afyadaktari.utils.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

import static com.crackit.afyadaktari.utils.Constants.REGISTER_PHONE_VERIFICATION_TIMEOUT;

@RestController
@RequestMapping(value = EndPoints.REGISTER)
public class RegisterController {
    public final UserRepository userRepository;
    public final OTPRepository otpRepository;

    public RegisterController(UserRepository userRepository, OTPRepository otpRepository) {
        this.userRepository = userRepository;
        this.otpRepository = otpRepository;
    }

    @PostMapping()
    public ResponseEntity<?> registerUser(@RequestBody(required = false) RegisterDto registerDto) throws NoSuchAlgorithmException {
        if(registerDto== null){
            String uglyResponse = "REQUEST BODY IS NULL : THIS MUST BE AN ADMIN MESSING WITH MY MIND \uD83D\uDE24 \uD83D\uDE20";
            System.out.println(uglyResponse);
            return new ResponseEntity<>(uglyResponse , HttpStatus.BAD_REQUEST);
        }

        final String username = registerDto.username();
        final String mobile = registerDto.mobile();
        final String password = registerDto.password();
        final String confirmPassword = registerDto.confirm_password();

        String usernameError = StringUtils.getUsernameError(username, userRepository);
        String mobileError = StringUtils.getMobileError(mobile, userRepository);
        String passwordError = StringUtils.getPasswordError(password);
        String confirmPasswordError = StringUtils.getConfirmPasswordError(password , confirmPassword);

        RegisterErrors errors = new RegisterErrors(usernameError,mobileError,passwordError,confirmPasswordError);

        if(errors.call() != null){
            return errors.call();
        }

        User user = new User();
        OTP otp = new OTP();

        user.setUsername(username);
        user.setMobile(mobile);
        user.setPassword(PasswordUtils.encrypt(password));

        userRepository.save(user);

        final Long otp1 = StringUtils.generateOTP();

        ///Write code here that will send an otp to the users phone number

        otp.setOtp(otp1);
        otp.setUserId(user.getId());
        otp.setCreatedAt(StringUtils.generateCurrentTimeStamp());
        otp.setExpiry(StringUtils.generateCurrentTimeStamp() + REGISTER_PHONE_VERIFICATION_TIMEOUT);
        otp.setOtpIsUsed(false);

        otpRepository.save(otp);

        RegisterSuccess success = new RegisterSuccess(user);

        return success.call();
    }
}
