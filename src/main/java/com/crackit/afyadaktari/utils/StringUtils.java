package com.crackit.afyadaktari.utils;

import com.crackit.afyadaktari.model.auth.OTP;
import com.crackit.afyadaktari.model.auth.User;
import com.crackit.afyadaktari.repository.OTPRepository;
import com.crackit.afyadaktari.repository.UserRepository;
import com.crackit.afyadaktari.service.password.PasswordUtils;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    // Function to validate the username
    private static boolean isValidUsername(String name)
    {
        String regex = "^[A-Za-z]\\w{3,29}$";
        Pattern p = Pattern.compile(regex);

        if (name == null) {
            return false;
        }

        Matcher m = p.matcher(name);
        return m.matches();
    }

    public static String getUsernameError(String username, UserRepository userRepository){
        if(Objects.equals(username, "") || username == null){
            return  "Username cannot be blank.";
        }

        if(!isValidUsername(username)){
            return "Please choose a valid username";
        }

        if(userRepository.existsByUsername(username)){
            return "This username is already taken.";
        }

        return null;
    }

    public static boolean isValidMobile(String mobile)
    {
        Pattern pattern1 = Pattern.compile("^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$");
        Pattern pattern2 = Pattern.compile("^\\d{10}$");

        Matcher matcher1 = pattern1.matcher(mobile);
        Matcher matcher2 = pattern2.matcher(mobile);

        return (matcher1.matches() || matcher2.matches());
    }

    public static String getMobileError(String mobile, UserRepository userRepository) {
        if(Objects.equals(mobile, "") || mobile == null){
            return  "Phone number cannot be blank.";
        }

        if(!isValidMobile(mobile)){
            return "Please enter a valid phone number";
        }

        if(userRepository.existsByMobile(mobile)){
            return "This phone number is already taken.";
        }

        return null;
    }

    public static boolean isStrongPassword(String password){
        int passwordLength=8, upChars=0, lowChars=0;
        int special=0, digits=0;
        char ch;
        int total = password.length();
        if(total<passwordLength)
        {
            return false;
        }
        else
        {
            for(int i=0; i<total; i++)
            {
                ch = password.charAt(i);
                if(Character.isUpperCase(ch))
                    upChars = 1;
                else if(Character.isLowerCase(ch))
                    lowChars = 1;
                else if(Character.isDigit(ch))
                    digits = 1;
                else
                    special = 1;
            }
        }
        return upChars == 1 && lowChars == 1 && digits == 1 && special == 1;
    }

    public static String getPasswordError(String password){
        if(Objects.equals(password, "") || password == null){
            return "Password cannot be blank.";
        }
        if(password.length() < 8){
            return "Your password should be 8 or more characters long.";
        }
        if(!isStrongPassword(password)){
            return "Password must have at least 1 upper_case, lower_case, special, and digit characters";
        }
        return null;
    }

    public static String getConfirmPasswordError(String password, String confirmPassword){
        if(Objects.equals(confirmPassword, "") || confirmPassword == null){
            return "Confirm password cannot be blank.";
        }
        if(password.equals(confirmPassword)){
            return null;
        } else{
            return "Your passwords do not match.";
        }
    }

    public static Long generateOTP() {
        int i  = new Random().nextInt(900000) + 100000;
        String otp = Integer.toString(i);

        return Long.parseLong(otp);
    }

    public static Long generateCurrentTimeStamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp.getTime();
    }

    public static String getOTPError(Long userId,String otp, OTPRepository otpRepository){
        if(Objects.equals(otp, "") || otp == null){
            return "OTP cannot be blank.";
        }
        if(otp.length() != 6){
            return "OTP must be have 6 digits";
        }
        if(otpRepository.findByUserId(userId).isEmpty()){
            return "You are an invalid user";
        }
        OTP userOTP = otpRepository.findByUserId(userId).get();

        final Long expiry = userOTP.getExpiry();

        if(generateCurrentTimeStamp() > expiry){
            System.out.println("CURRENT RIME STAMP : "+generateCurrentTimeStamp() + " OTP EXPIRY : " + expiry);
            return "OTP is expired.";
        }

        final Long code = userOTP.getOtp();
        if(!code.toString().equals(otp)){
            return "OTP is invalid.";
        }

        final boolean isOtpUsed = userOTP.isOtpIsUsed();
        if(isOtpUsed){
            return "This OTP has  already been used.";
        }
        return null;
    }

    public static String getUsernameOrMobileError(String usernameOrMobile){
        if(Objects.equals(usernameOrMobile,"") || usernameOrMobile == null){
            return "Username or mobile cannot be blank.";
        }
        return null;
    }

    public static String getLoginPasswordError(String usernameOrMobile, String password, UserRepository userRepository) throws NoSuchAlgorithmException {
        if(Objects.equals(password, "") || password == null){
            return "Password cannot be blank.";
        }

        final boolean isMobile = usernameOrMobile.matches("[0-9]+");

        boolean usernameOrMobileExists = false;
        Optional<User> optionalUser = Optional.empty();
        User user;

        if(isMobile){
            if(userRepository.existsByMobile(usernameOrMobile)){
                usernameOrMobileExists = true;
                optionalUser = userRepository.findByMobile(usernameOrMobile);
            }
        }else{
            if(userRepository.existsByUsername(usernameOrMobile)){
                usernameOrMobileExists = true;
                optionalUser = userRepository.findByUsername(usernameOrMobile);
            }
        }

        if(!usernameOrMobileExists){
            return "Incorrect username, mobile or username.";
        }else{
            if(optionalUser.isPresent()){
                user = optionalUser.get();

                final String passwordHash = user.getPassword();

                if(!PasswordUtils.passwordMatches(password, passwordHash)){
                    return "Incorrect username, mobile or username.";
                }
            }
        }
        return null;
    }

    public static User getUserFromUsernameOrMobile(String usernameOrMobile, UserRepository userRepository){
        final boolean isMobile = usernameOrMobile.matches("[0-9]+");
        Optional<User> optionalUser;

        if(isMobile){
            optionalUser = userRepository.findByMobile(usernameOrMobile);
        }else{
            optionalUser = userRepository.findByUsername(usernameOrMobile);
        }
        if(optionalUser.isEmpty()) return null;
        return optionalUser.get();
    }
}
