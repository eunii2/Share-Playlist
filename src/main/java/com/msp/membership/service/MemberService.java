package com.msp.membership.service;

import com.msp.membership.controller.MemberController;
import com.msp.membership.dto.MemberDTO;
import com.msp.membership.dto.UserProfileDTO;
import com.msp.membership.entity.Authority;
import com.msp.membership.entity.Member;
import com.msp.membership.exception.DuplicateMemberException;
import com.msp.membership.jwt.util.SecurityUtil;
import com.msp.membership.repository.FollowRepository;
import com.msp.membership.repository.MemberRepository;
import com.msp.membership.repository.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

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
/*
    @Transactional(readOnly = true) // 특정 사용자 조회 로직
    public MemberDTO getUserWithAuthorities(String userid) {
        return MemberDTO.from(memberRepository.findOneWithAuthoritiesByUserid(userid).orElse(null));
    }

    @Transactional(readOnly = true) // 현재 사용자 조회 로직
    public MemberDTO getMyUserWithAuthorities() {
        return MemberDTO.from(
                SecurityUtil.getCurrentUserid()
                        .flatMap(memberRepository::findOneWithAuthoritiesByUserid)
                        .orElseGet(() -> {
                            log.warn("사용자를 찾을 수 없음: {}", SecurityUtil.getCurrentUserid().orElse("알 수 없음"));
                            return new Member();
                        })
        );
    }

*/
    @Transactional(readOnly = true)
    public Optional<Member> getUserWithAuthorities(String userid){
        return memberRepository.findOneWithAuthoritiesByUserid(userid);
    }

    //현재 인증된 사용자의 회원정보 조회
    @Transactional(readOnly = true)
    public Optional<Member> getMyUserWithAuthorities(){
        log.info(SecurityUtil.getCurrentUsername().toString());
        return SecurityUtil.getCurrentUsername()
                .flatMap(userid -> memberRepository.findOneWithAuthoritiesByUserid(userid));
    }


    public Member findByUserid(String userid) {
        Optional<Member> byUserid = memberRepository.findByUserid(userid);
        return byUserid.orElse(null);
    }

    @Transactional
    public UserProfileDTO findById(int profileUserId, int principalId) {
        Member member = memberRepository.findById(profileUserId);
        UserProfileDTO userProfileDTO = new UserProfileDTO().EntityToDto(member);
        userProfileDTO.setSubscribeCount(followRepository.countByToUserId(profileUserId));
        userProfileDTO.setSubscribeState(followRepository.existsByToUserIdAndFromUserId(profileUserId, principalId));
        return userProfileDTO;
    }

    //@Override
    @Transactional
    public UserProfileDTO findById(int id) {
        Member member = memberRepository.findById(id);
        return new UserProfileDTO().EntityToDto(member);
    }

    /* 프로필 업로드 */
    @Transactional
    public boolean updateProfileImage(int id, MultipartFile profileImage) {

        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
        String uploadForder= Paths.get("C:", "insta", "upload").toString();
        String profileUploadForder = Paths.get("profileImage", today).toString();
        String uploadPath = Paths.get(uploadForder, profileUploadForder).toString();

        File dir = new File(uploadPath);
        if (dir.exists() == false) {
            dir.mkdirs();
        }

        UUID uuid = UUID.randomUUID();
        String profileImageName = uuid+"_"+profileImage.getOriginalFilename();

        try {
            File target = new File(uploadPath, profileImageName);
            profileImage.transferTo(target);

        } catch (Exception e) {
            return false;
        }

        Member member = memberRepository.findById(id);

        member.updateProfileImage(profileUploadForder+"\\"+profileImageName);


        return true;
    }
}