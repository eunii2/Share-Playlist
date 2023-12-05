package com.msp.membership.repository;

import com.msp.membership.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Integer> {

    Optional<Member> findOptionalByUserid(String userid);

    Member findById(int id);

    Member findByUserid(String userid);

    @EntityGraph(attributePaths = "authorities")
    Optional<Member> findOneWithAuthoritiesByUserid(String userid);

}
