package com.crackit.afyadaktari.repository;

import com.crackit.afyadaktari.model.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User , Long> {
    Optional<User> findByMobile(String mobile);
//    Optional<User> findByUsernameOrEmail(String username, String email);
    Optional<User> findByUsername(String username);
    Optional<User> findById(Long userId);
    Boolean existsByUsername(String username);
    Boolean existsByMobile(String mobile);
}
