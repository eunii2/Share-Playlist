package com.msp.membership.repository;

import com.msp.membership.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
    Optional<Authority> findByAuthorityName(String authorityName);
}