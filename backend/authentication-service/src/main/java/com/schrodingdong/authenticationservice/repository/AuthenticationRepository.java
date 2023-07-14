package com.schrodingdong.authenticationservice.repository;

import com.schrodingdong.authenticationservice.models.AuthModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface AuthenticationRepository extends JpaRepository<AuthModel, Long>{
    public boolean existsByEmail(String email);
    public AuthModel findByEmail(String email);
}
