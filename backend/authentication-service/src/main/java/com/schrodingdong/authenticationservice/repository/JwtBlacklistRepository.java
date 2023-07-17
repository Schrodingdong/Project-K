package com.schrodingdong.authenticationservice.repository;

import com.schrodingdong.authenticationservice.models.JwtBlacklistModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JwtBlacklistRepository extends JpaRepository<JwtBlacklistModel, Long> {
    boolean existsByJwt(String jwt);
}
