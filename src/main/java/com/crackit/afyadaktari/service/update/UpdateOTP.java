package com.crackit.afyadaktari.service.update;

import com.crackit.afyadaktari.model.auth.OTP;
import com.crackit.afyadaktari.repository.OTPRepository;

import java.util.concurrent.Callable;

public class UpdateOTP implements Callable<OTP> {
    private final OTPRepository otpRepository;
    private final OTP otp;
    private final boolean isOtpUsed;
    private Long createdAt = null;
    private Long expiry = null;
    private Long otpCode = null;

    public UpdateOTP(OTPRepository otpRepository1, OTP otp1, boolean isOtpUsed1){

        this.otpRepository = otpRepository1;
        this.otp = otp1;
        this.isOtpUsed = isOtpUsed1;
    }
    public UpdateOTP(OTPRepository otpRepository1, OTP otp1, boolean isOtpUsed1, Long createdAt1, Long expiry1, Long otpCode1){

        this.otpRepository = otpRepository1;
        this.otp = otp1;
        this.isOtpUsed = isOtpUsed1;

        this.createdAt = createdAt1;
        this.expiry = expiry1;
        this.otpCode = otpCode1;
    }
    @Override
    public OTP call() {
        if(this.createdAt != null){
            otp.setCreatedAt(this.createdAt);
        }else{
            otp.setCreatedAt(otp.getCreatedAt());
        }

        if(this.expiry != null){
            otp.setExpiry(this.expiry);
        }else{
            otp.setExpiry(otp.getExpiry());
        }

        if(this.otpCode != null){
            otp.setOtp(otpCode);
        }
        else {
            otp.setOtp(otp.getOtp());
        }

        otp.setOtpIsUsed(this.isOtpUsed);
        otp.setUserId(otp.getUserId());

        return otpRepository.save(otp);
    }
}
