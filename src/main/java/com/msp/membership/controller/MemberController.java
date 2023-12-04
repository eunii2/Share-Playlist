package com.msp.membership.controller;

import com.msp.membership.dto.MemberDTO;
import com.msp.membership.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    private static final Logger log = LoggerFactory.getLogger(MemberController.class);

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/join")
    public String joinForm(){
        return "/login/join";
    }

    @PostMapping("/join")
    public ResponseEntity<MemberDTO> join(
            @Valid @RequestBody MemberDTO memberDTO
    ) {
        return ResponseEntity.ok(memberService.join(memberDTO));
    }
    
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }


    /*
    @GetMapping("/update/{id}")    // 프로필 수정 페이지(로그인 한 사람만 수정 가능)
    public String updateForm(@PathVariable int id, HttpSession session, Model model) {

        MemberDTO user = (MemberDTO) session.getAttribute("user"); // 세션에서 사용자 정보를 조회

        if(user == null || id != user.getId()) {
            log.info("잘못된 접근입니다");
            return "redirect:/user/profile"; // 사용자 프로필 페이지로 리다이렉트
        }
        Member member = memberService.findByUserid(user.getUserid());
        model.addAttribute("user", member);

        return "update";
    }
    */

    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<MemberDTO> getMyUserInfo(HttpServletRequest request) {
        return ResponseEntity.ok(memberService.getMyUserWithAuthorities());
    }

    @GetMapping("/profile/{userid}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<MemberDTO> getUserInfo(@PathVariable String userid) {
        return ResponseEntity.ok(memberService.getUserWithAuthorities(userid));
    }

    @PutMapping("/profile/Image/{id}")
    public ResponseEntity<String> updateProfileImage(@PathVariable int id, MultipartFile profileImageFile){

        if(memberService.updateProfileImage(id, profileImageFile)) {
            return ResponseEntity.ok().body("프로필 사진 수정에 성공하였습니다");
        }

        else {
            return ResponseEntity.badRequest().body("프로필 사진 수정에 실패하였습니다");
        }

    }
}
