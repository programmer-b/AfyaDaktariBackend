package com.crackit.afyadaktari.controller.auth;

import com.crackit.afyadaktari.model.auth.User;
import com.crackit.afyadaktari.payload.response.login.LoginErrors;
import com.crackit.afyadaktari.payload.response.login.LoginSuccess;
import com.crackit.afyadaktari.repository.UserRepository;
import com.crackit.afyadaktari.utils.EndPoints;
import com.crackit.afyadaktari.utils.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

import static com.crackit.afyadaktari.utils.Constants.KEY_PASSWORD;
import static com.crackit.afyadaktari.utils.Constants.KEY_USERNAME_OR_MOBILE;

@RestController
@RequestMapping(value = EndPoints.LOGIN)
public class LoginController {
    private final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping()
    public ResponseEntity<?> loginUser(@RequestBody(required = false)Map<String,String> loginDto) throws NoSuchAlgorithmException {
        if(loginDto == null){
            String uglyResponse = "REQUEST BODY IS NULL : THIS MUST BE AN ADMIN MESSING WITH MY MIND \uD83D\uDE24 \uD83D\uDE20";
            System.out.println(uglyResponse);
            return new ResponseEntity<>(uglyResponse , HttpStatus.BAD_REQUEST);
        }

        final String usernameOrMobile = loginDto.get(KEY_USERNAME_OR_MOBILE);
        final String password = loginDto.get(KEY_PASSWORD);

        final String usernameOrMobileError = StringUtils.getUsernameOrMobileError(usernameOrMobile);
        final String passwordError = StringUtils.getLoginPasswordError(usernameOrMobile,password,userRepository, false);

        LoginErrors loginErrors = new LoginErrors(usernameOrMobileError, passwordError);
        if(loginErrors.call() != null){
            return loginErrors.call();
        }

        final User user = StringUtils.getUserFromUsernameOrMobile(usernameOrMobile,userRepository);

        LoginSuccess loginSuccess = new LoginSuccess(user);

        return loginSuccess.call();
    }
}
