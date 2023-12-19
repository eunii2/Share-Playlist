package com.msp.membership.repository;

import com.msp.membership.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String> {
    Optional<Authority> findByAuthorityName(String authorityName);
}