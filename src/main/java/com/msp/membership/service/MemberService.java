package com.msp.membership.service;

import com.msp.membership.dto.MemberDTO;
import com.msp.membership.entity.Member;
import com.msp.membership.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void join(MemberDTO memberDTO) { // join이라는 매서드 생성
        // 1. dto -> entity 변환
        // 2. repository의 join 매서드 호출
        Member member = Member.toMemberEntity(memberDTO);
        memberRepository.save(member);    // save는 jpa가 제공해주는 매서드, 호출을 함으로써 쿼리문을 실행해줌
        // repository의 join매서드 호출(조건. entity객체를 넘겨줘야 함)
    }

    public MemberDTO login(MemberDTO memberDTO) {
        /*
            1. 회원이 입력한 아이디로 DB에서 조회를 함
            2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단
        */
        Optional<Member> byUserid = memberRepository.findByUserid(memberDTO.getUserid()); //Optional 객체로 감쌈
        if (byUserid.isPresent()) {
            // 조회 결과가 있다.(해당 아이디를 가진 회원 정보가 있다.)
            Member member = byUserid.get();
            if (member.getUserpw().equals(memberDTO.getUserpw())) { //!. entity랑 dto관계 살펴보기
                //entity -> dto
                MemberDTO dto = MemberDTO.toMemberDTO(member);
                return dto;
            } 
            else {
                return null;
            }
        }
        else {
            return null;    //!. 로그인 실패 만들기
        }
    }
    public String idCheck(String userid){
        Optional<Member> byUserid = memberRepository.findByUserid(userid);
        if(byUserid.isPresent()){ //이미 존재
            return null;
        }
        else{
            return "true";
        }
    }
    public List<Member> findByUsernameContainingAndUseridNot(String searching, String id) {
        return memberRepository.findByUsernameContainingAndUseridNot(searching, id);
    }

}
