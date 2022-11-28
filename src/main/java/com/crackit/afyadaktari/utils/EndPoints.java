package com.crackit.afyadaktari.utils;

public class EndPoints {
    public static final String CURRENT_VERSION = "/v1";
    public static final String AUTH_BASE  = CURRENT_VERSION + "/api/auth";
    public static final String REGISTER = AUTH_BASE + "/register";
    public static final String VERIFY_OTP = AUTH_BASE + "/otp/verify";
    public static final String RE_SEND_OTP = AUTH_BASE + "/otp/resend";
    public static final String LOGIN = AUTH_BASE + "/login";
    public static final String PASSWORD_RESET = AUTH_BASE + "/password/reset";
    public static final String PASSWORD_CHANGE = AUTH_BASE + "/password/change";
    public static final String PASSWORD_FORGOT = AUTH_BASE + "/password/forgot";
}
