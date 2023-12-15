package com.msp.membership.service;

import com.msp.membership.controller.MemberController;
import com.msp.membership.dto.MemberDTO;
import com.msp.membership.entity.Authority;
import com.msp.membership.entity.Member;
import com.msp.membership.exception.DuplicateMemberException;
import com.msp.membership.jwt.util.SecurityUtil;
import com.msp.membership.repository.AuthorityRepository;
import com.msp.membership.repository.FollowRepository;
import com.msp.membership.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private static final Logger log = LoggerFactory.getLogger(MemberController.class);

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    private FollowRepository followRepository;



    @Transactional
    public Member join(MemberDTO memberDTO) {
        if (memberRepository.findOneWithAuthoritiesByUserid(memberDTO.getUserid()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

        Authority authority = authorityRepository.findByAuthorityName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("ROLE_USER 권한이 존재하지 않습니다."));


        Member member = Member.builder()
                .userid(memberDTO.getUserid())
                .userpw(passwordEncoder.encode(memberDTO.getUserpw()))
                .username(memberDTO.getUsername())
                .userphone(memberDTO.getUserphone())
                .useremail(memberDTO.getUseremail())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();
        return memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public Optional<Member> getUserWithAuthorities(String userid){
        return memberRepository.findOneWithAuthoritiesByUserid(userid);
    }

    //현재 인증된 사용자의 회원정보 조회
    @Transactional(readOnly = true)
    public Optional<Member> getMyUserWithAuthorities(){
        log.info(SecurityUtil.getCurrentUserid().toString());
        return SecurityUtil.getCurrentUserid()
                .flatMap(memberRepository::findOneWithAuthoritiesByUserid);
    }


    public Member findOptionalByUserid(String userid) {
        Optional<Member> byUserid = memberRepository.findOptionalByUserid(userid);
        return byUserid.orElse(null);
    }


    public Member findByUserid(String userid) {
        return memberRepository.findByUserid(userid);
    }

    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Member not found with id: " + id));
    }


    public void img_update(String userid, String profileImage) {
        Member member = findByUserid(userid); // 유저아이디로 유저찾음
        member.setProfileImage(profileImage);
        save_user(member);
    }

    public void save_user(Member member) {
        memberRepository.save(member);
    }


    public List<Member> findByUseridContains(String word) {
        return memberRepository.findByUseridContains(word);
    }

    public int countByUseridContains(String word) {
        return memberRepository.countByUseridContains(word);
    }
}