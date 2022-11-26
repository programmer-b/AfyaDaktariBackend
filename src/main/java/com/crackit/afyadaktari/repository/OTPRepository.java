package com.crackit.afyadaktari.repository;

import com.crackit.afyadaktari.model.auth.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface OTPRepository extends JpaRepository<OTP, Long>, JpaSpecificationExecutor<OTP> {
    Optional<OTP> findByUserId(Long userId);

    Boolean existsByUserId(Long userId);
}