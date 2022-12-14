package com.apu.user.security_oauth2.repository;


import com.apu.user.security_oauth2.models.security.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByName(String name);
}