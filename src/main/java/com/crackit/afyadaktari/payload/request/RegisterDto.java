package com.crackit.afyadaktari.payload.request;

import lombok.Data;

@Data
public final class RegisterDto {
    private final String mobile;
    private final String username;
    private final String password;
    private final String confirm_password;

    public RegisterDto(String mobile, String username, String password, String confirm_password) {
        this.mobile = mobile;
        this.username = username;
        this.password = password;
        this.confirm_password = confirm_password;
    }

    public String mobile() {
        return mobile;
    }

    public String username() {
        return username;
    }

    public String password() {
        return password;
    }

    public String confirm_password() {
        return confirm_password;
    }

}