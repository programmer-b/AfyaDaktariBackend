package com.crackit.afyadaktari.controller.auth;

import com.crackit.afyadaktari.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/register")
public class RegisterController {
    @Autowired
    private UserRepository userRepository;
}
