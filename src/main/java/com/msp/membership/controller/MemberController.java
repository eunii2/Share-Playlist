package com.msp.membership.controller;

import com.msp.membership.entity.Member;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

import com.msp.membership.dto.MemberDTO;
import com.msp.membership.service.MemberService;
import com.msp.membership.dto.UserProfileDTO;

@RestController
@RequiredArgsConstructor
public class MemberController {
    // 생성자 주입
    private final MemberService memberService;
    // 회원 가입 페이지 출력 요청
    private static final Logger log = LoggerFactory.getLogger(MemberController.class);
    @GetMapping("/member/join")
    public String joinForm(){
        return "/login/join";
    }

    @PostMapping("/member/join")
    public String join(@RequestBody MemberDTO memberDTO)
    {
        log.info("MemberController.save");
        log.info("memberDTO = " + memberDTO);
        memberService.join(memberDTO);
        return "/login/login";
    }

    @GetMapping("/user/login")
    public String loginForm() {
        return "login/login";
    }

    @PostMapping("/user/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session){
        MemberDTO loginResult = memberService.login(memberDTO);
        if(loginResult != null) {
            session.setAttribute("loginId", loginResult.getUserid());
            return "main";
        }
        else{
            //로그인 실패
            return "login/login";   //html 파일
        }
    }

    @PostMapping("/user/id-check")    // 아이디 중복 처리
    public @ResponseBody String idCheck(@RequestParam("userid") String userid){
        log.info("userid = " + userid);
        String checkResult = memberService.idCheck(userid);
        if(checkResult != null) {
            return "ok";
        }
        else{
            return "no";
        }
    }

    @GetMapping("/user/update/{id}")
    public String updateForm(@PathVariable int id, HttpSession session, Model model) {

        MemberDTO user = (MemberDTO) session.getAttribute("user"); // 세션에서 사용자 정보를 조회

        if(user == null || id != user.getId()) {
            log.info("잘못된 접근입니다");
            return "redirect:/user/profile"; // 사용자 프로필 페이지로 리다이렉트
        }
        Member member = memberService.findByUserid(user.getUserid());
        model.addAttribute("user", member);

        return "user/update";
    }

    @GetMapping("user/profile")
    public String profileDefault(HttpSession session, Model model) {
        MemberDTO user = (MemberDTO) session.getAttribute("user"); // 세션에서 사용자 정보를 조회
        if (user != null) {
            profile(user.getId(), model);
        }
        // TODO: 사용자가 로그인하지 않은 경우의 처리
        return "user/profile";
    }

    @GetMapping("user/profile/{id}")
    public String profile(@PathVariable Long id, Model model) {
        UserProfileDTO profileDTO = MemberService.findById(id);
        model.addAttribute("profileDto",profileDTO);
        return "user/profile";
    }
}
