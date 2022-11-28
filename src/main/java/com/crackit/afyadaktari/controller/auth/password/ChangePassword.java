package com.crackit.afyadaktari.controller.auth.password;

import com.crackit.afyadaktari.controller.auth.PrepareTokenAuth;
import com.crackit.afyadaktari.model.auth.User;
import com.crackit.afyadaktari.payload.response.password.change.ChangePasswordError;
import com.crackit.afyadaktari.payload.response.password.change.ChangePasswordSuccess;
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

import static com.crackit.afyadaktari.utils.Constants.*;

@RestController
@RequestMapping(value = EndPoints.PASSWORD_CHANGE)
public class ChangePassword {
    private final UserRepository userRepository;

    public ChangePassword(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<?> changePassword(@RequestHeader(value = "Authorization" , required = false) String token, @RequestBody(required = false) Map<String,String> changePasswordDto) throws NoSuchAlgorithmException {
        PrepareTokenAuth prepareTokenAuth = new PrepareTokenAuth(token);
        if(prepareTokenAuth.call() != null){
            return prepareTokenAuth.call();
        }

        final String currentPassword = changePasswordDto.get(KEY_CURRENT_PASSWORD);
        final String newPassword = changePasswordDto.get(KEY_NEW_PASSWORD);
        final String confirmPassword = changePasswordDto.get(KEY_CONFIRM_PASSWORD);

        final Long userId = TokenManager.getUserIdFromToken(token);

        final String currentPasswordError = StringUtils.getCurrentPasswordError(currentPassword,userId,userRepository);
        final String newPasswordError = StringUtils.getPasswordError(newPassword);
        final String confirmPasswordError = StringUtils.getConfirmPasswordError(newPassword,confirmPassword);

        ChangePasswordError error = new ChangePasswordError(currentPasswordError,newPasswordError,confirmPasswordError);

        if(error.call() != null){
            return error.call();
        }
        final User user = userRepository.findById(userId).get();

        UpdateUser updateUser = new UpdateUser(userRepository, user, PasswordUtils.encrypt(newPassword), user.isProfileUpdated(), user.isMobileVerified());
        updateUser.call();

        ChangePasswordSuccess success = new ChangePasswordSuccess(user);
        return success.call();
    }
}
