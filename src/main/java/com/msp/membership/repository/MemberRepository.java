package com.msp.membership.repository;

import com.msp.membership.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//데이터베이스에 작업을 해주는 마지막 단계 같은 느낌
public interface MemberRepository extends JpaRepository<MemberEntity, Long> { // <Entity클래스, PK가 어떤 타입인지>
    // 아이디로 회원 정보를 조회 (SELECT * FROM member_table WHERE userid=?)
    // 매서드만 정의해도 메서드에 맞는 쿼리가 정의됨
    Optional<MemberEntity> findByUserid(String Userid); // 레파지토리에서 주고받는 객체는 모두 Entitiy 객체

}
