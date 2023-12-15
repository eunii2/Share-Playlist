package com.msp.membership.repository;

import com.msp.membership.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findOptionalByUserid(String userid);

    Optional<Member> findById(Long id);

    Member findByUserid(String userid);

    @EntityGraph(attributePaths = "authorities")
    Optional<Member> findOneWithAuthoritiesByUserid(String userid);

    List<Member> findByUseridContains(String word); // word가 포함되는 유저id List찾기
    int countByUseridContains(String word); // word가 포함되는 유저id 개수찾기

}
