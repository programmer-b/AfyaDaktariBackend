package com.crackit.afyadaktari.controller.auth.password;

import com.crackit.afyadaktari.controller.auth.PrepareTokenAuth;
import com.crackit.afyadaktari.model.auth.User;
import com.crackit.afyadaktari.payload.response.password.reset.ResetPasswordErrors;
import com.crackit.afyadaktari.payload.response.password.reset.ResetPasswordSuccess;
import com.crackit.afyadaktari.repository.UserRepository;
import com.crackit.afyadaktari.service.jwt.TokenManager;
import com.crackit.afyadaktari.service.password.PasswordUtils;
import com.crackit.afyadaktari.service.update.UpdateUser;
import com.crackit.afyadaktari.utils.EndPoints;
import com.crackit.afyadaktari.utils.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

import static com.crackit.afyadaktari.utils.Constants.KEY_CONFIRM_PASSWORD;
import static com.crackit.afyadaktari.utils.Constants.KEY_NEW_PASSWORD;

@RestController
@RequestMapping(value = EndPoints.PASSWORD_RESET)
public class ResetPassword {
    private final UserRepository userRepository;

    public ResetPassword(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<?> resetPassword(@RequestHeader(value = "Authorization" , required = false) String token, @RequestBody(required = false) Map<String,String> resetPasswordDto) throws NoSuchAlgorithmException {
        PrepareTokenAuth prepareTokenAuth = new PrepareTokenAuth(token);
        if(prepareTokenAuth.call() != null){
            return prepareTokenAuth.call();
        }

        final String newPassword = resetPasswordDto.get(KEY_NEW_PASSWORD);
        final String confirmPassword = resetPasswordDto.get(KEY_CONFIRM_PASSWORD);

        final String newPasswordError = StringUtils.getResetNewPasswordError(newPassword);
        final String confirmPasswordError = StringUtils.getConfirmPasswordError(newPassword, confirmPassword);

        ResetPasswordErrors errors = new ResetPasswordErrors(newPasswordError, confirmPasswordError);

        if(errors.call() != null){
            return errors.call();
        }

        Long userId = TokenManager.getUserIdFromToken(token);

        final User user = userRepository.findById(userId).get();

        UpdateUser updateUser = new UpdateUser(userRepository,user, PasswordUtils.encrypt(newPassword), user.isProfileUpdated(), user.isMobileVerified());
        updateUser.call();

        ResetPasswordSuccess success =  new ResetPasswordSuccess(user);
        return success.call();
    }
}
