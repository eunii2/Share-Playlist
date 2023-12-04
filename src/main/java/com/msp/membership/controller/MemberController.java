package com.msp.membership.controller;

import com.msp.membership.dto.MemberDTO;
import com.msp.membership.entity.Member;
import com.msp.membership.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String joinForm(){
        return "/login/join";
    }

    @PostMapping("/join")
    public ResponseEntity<Member> join(@Valid @RequestBody MemberDTO memberDTO){
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

    @GetMapping("/profile/{userid}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Member> getUserInfo(@PathVariable String userid){
        return ResponseEntity.ok(memberService.getUserWithAuthorities(userid).get());
    }
    */

    @GetMapping("/myinfo")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Member> getMyUserInfo(){
        return ResponseEntity.ok(memberService.getMyUserWithAuthorities().get());
    }
/*
    @GetMapping("user/profile")
    public void profileDefault(@AuthenticationPrincipal CustomUserDetails customUser, Model model) {

        profile(customUser.getId(),model);
    }

    @GetMapping("/user/profile/{id}")
    public String profile(@PathVariable int id, Model model) {

        UserProfileDTO userprofileDTO = MemberService.findById(id);
        model.addAttribute("profileDto", userprofileDTO);
        return "user/profile";
    }
    */

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
