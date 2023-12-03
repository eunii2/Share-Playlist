package com.msp.membership.service;

import com.msp.membership.controller.MemberController;
import com.msp.membership.dto.MemberDTO;
import com.msp.membership.dto.UserProfileDTO;
import com.msp.membership.entity.Authority;
import com.msp.membership.entity.Member;
import com.msp.membership.jwt.util.SecurityUtil;
import com.msp.membership.repository.FollowRepository;
import com.msp.membership.repository.MemberRepository;
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

@Service
public class MemberService {

    private static final Logger log = LoggerFactory.getLogger(MemberController.class);

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private FollowRepository followRepository;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public MemberDTO join(MemberDTO memberDTO) {

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        Member member = Member.builder()
                .userid(memberDTO.getUserid())
                .userpw(passwordEncoder.encode(memberDTO.getUserpw()))
                .username(memberDTO.getUsername())
                .userphone(memberDTO.getUserphone())
                .useremail(memberDTO.getUseremail())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return MemberDTO.from(memberRepository.save(member));
    }

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
                            // 로깅을 통해 경고 메시지 출력
                            log.warn("사용자를 찾을 수 없음: {}", SecurityUtil.getCurrentUserid().orElse("알 수 없음"));
                            return new Member();
                        })
        );
    }

    public MemberDTO login(MemberDTO memberDTO) {

        Optional<Member> byUserid = memberRepository.findByUserid(memberDTO.getUserid());
        if (byUserid.isPresent()) {
            Member member = byUserid.get();
            if (member.getUserpw().equals(memberDTO.getUserpw())) {
                MemberDTO dto = MemberDTO.toMemberDTO(member);
                return dto;
            } else {
                return null;
            }
        } else {
            return null;    //!. 로그인 실패 만들기
        }
    }

    public String idCheck(String userid) {
        Optional<Member> byUserid = memberRepository.findByUserid(userid);
        if (byUserid.isPresent()) { //이미 존재
            return null;
        } else {
            return "true";
        }
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
    public UserProfileDTO findById(int profileUserId, int principalId) {
        Member member = memberRepository.findById(profileUserId);
        UserProfileDTO userProfileDTO = new UserProfileDTO().EntityToDto(member);
        userProfileDTO.setSubscribeCount(followRepository.countByToUserId(profileUserId));
        userProfileDTO.setSubscribeState(followRepository.existsByToUserIdAndFromUserId(profileUserId, principalId));
        return userProfileDTO;
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