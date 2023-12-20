package com.msp.membership.repository;

import com.msp.membership.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findOptionalByUserid(String userid);

    Optional<Member> findById(Long id);

    Optional<Member> findById(String userid);

    Member findByUserid(String userid);

    Optional<Member> findOneWithAuthoritiesByUserid(String userid);

    List<Member> findByUseridContains(String word);
    int countByUseridContains(String word);

}
