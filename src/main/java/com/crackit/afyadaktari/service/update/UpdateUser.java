package com.crackit.afyadaktari.service.update;

import com.crackit.afyadaktari.model.auth.User;
import com.crackit.afyadaktari.repository.UserRepository;

import java.util.concurrent.Callable;

public class UpdateUser implements Callable<User> {
    private final UserRepository userRepository;
    private final User user;
    private  String password = null;
    private boolean isMobileVerified = false;
    private boolean isProfileUpdated = false;

    public UpdateUser(UserRepository userRepository, User user) {
        this.userRepository = userRepository;
        this.user = user;
    }

    public UpdateUser(UserRepository userRepository, User user, String password){
        this.userRepository = userRepository;
        this.user = user;
        this.password = password;
    }

    public UpdateUser(UserRepository userRepository, User user, boolean isProfileUpdated, boolean isMobileVerified){
        this.userRepository = userRepository;
        this.user = user;
        this.isMobileVerified = isMobileVerified;
        this.isProfileUpdated = isProfileUpdated;
    }

    @Override
    public User call()  {
        if(password != null){
            user.setPassword(this.password);
        }else{
            user.setPassword(user.getPassword());
        }
        user.setProfileUpdated(this.isProfileUpdated);
        user.setMobileVerified(this.isMobileVerified);
        user.setUsername(user.getUsername());
        user.setMobile(user.getMobile());

        return userRepository.save(user);
    }
}
