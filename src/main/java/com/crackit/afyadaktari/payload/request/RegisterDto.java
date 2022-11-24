package com.crackit.afyadaktari.payload;

import lombok.Data;

@Data
public class RegisterDto {
    private String mobile;
    private String username;
    private String password;
    private String confirmPassword;
}
