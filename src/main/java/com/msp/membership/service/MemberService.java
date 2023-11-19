package com.msp.membership.service;

import com.msp.membership.dto.MemberDTO;
import com.msp.membership.entity.MemberEntity;
import com.msp.membership.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;

    public void join(MemberDTO memberDTO) { //join이라는 매서드 생성
        // 1. dto -> entity 변환
        // 2. repository의 join 매서드 호출
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity);    //save는 jpa가 제공해주는 매서드, 호출을 함으로써 쿼리문을 실행해줌
        // repository의 join매서드 호출(조건. entity객체를 넘겨줘야 함)
    }

    public MemberDTO login(MemberDTO memberDTO) {
        /*
            1. 회원이 입력한 아이디로 DB에서 조회를 함
            2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단
         */
        MemberEntity memberEntity = memberRepository.findByUserId(memberDTO.getUserid())
                .orElseThrow(() -> new IllegalArgumentException("해당 id가 존재하지 않습니다. id: " + memberDTO.getUserid()));

        if (!memberEntity.getUserPw().equals(memberDTO.getUserpw())) {
            log.error(" 유저의 비밀번호가 일치하지 않습니다. userId: {}", memberDTO.getUserpw());
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return MemberDTO.toMemberDTO(memberEntity);
    }

    public String idCheck(String userid){
        Optional<MemberEntity> byUserid = memberRepository.findByUserId(userid);
        if(byUserid.isPresent()){ //이미 존재
            return null;
        }
        else{
            return "true";
        }
    }
}
