package com.msp.membership.service;

import com.msp.membership.dto.MemberDTO;
import com.msp.membership.entity.Member;
import com.msp.membership.repository.MemberRepository;
import com.msp.membership.dto.UserProfileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void join(MemberDTO memberDTO) {
        Member member = Member.toMemberEntity(memberDTO);
        memberRepository.save(member);

    }

    public MemberDTO login(MemberDTO memberDTO) {

        Optional<Member> byUserid = memberRepository.findByUserid(memberDTO.getUserid()); //Optional 객체로 감쌈
        if (byUserid.isPresent()) {
            Member member = byUserid.get();
            if (member.getUserpw().equals(memberDTO.getUserpw())) {
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

    public Member findByUserid(String userid) {
        Optional<Member> byUserid = memberRepository.findByUserid(userid);
        return byUserid.orElse(null);
    }

    @Transactional
    public boolean findUser(String userid) {
        return memberRepository.existsByUserid(userid);
    }
    @Transactional
    public static UserProfileDTO findById(Long id) {
        Member member = MemberRepository.findById(id);
        UserProfileDTO profileDTO = new UserProfileDTO().EntityToDto(member);
        return profileDTO;
    }

}
