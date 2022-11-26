package com.crackit.afyadaktari.model.auth;

import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class OTP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column
    private Long userId;
    @Column
    private Long otp;
    @Column
    private Long createdAt;
    @Column
    private Long expiry;

    @Column
    private boolean otpIsUsed;

    public boolean isOtpIsUsed() {
        return otpIsUsed;
    }

    public void setOtpIsUsed(boolean otpIsUsed) {
        this.otpIsUsed = otpIsUsed;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOtp() {
        return otp;
    }

    public void setOtp(Long otp) {
        this.otp = otp;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getExpiry() {
        return expiry;
    }

    public void setExpiry(Long expiry) {
        this.expiry = expiry;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
       OTP otp = (OTP) o;
        return id != null && Objects.equals(id, otp.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
