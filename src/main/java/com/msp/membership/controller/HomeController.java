package com.msp.membership.controller;

import com.msp.membership.entity.Member;
import com.msp.membership.service.MemberService;
import org.springframework.ui.Model;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {
    private final MemberService memberService;

    public HomeController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/index")
    public String index(){
        //간단한 코드 작성만으로도 모두 실행됨
        return "index";
    }

    @GetMapping("/join")
    public String joinForm(){
        return "join";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    /* 유저 정보 확인용 */
    @GetMapping("/myinfo")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Member> getMyUserInfo() {
        return ResponseEntity.ok(memberService.getMyUserWithAuthorities().get());
    }

    @GetMapping("/main/user/{userid}")
    public String main_user(@PathVariable("userid") String userid, Model model) throws Exception {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("member", memberService.findByUserid(userid));

        return "profile";
    }

}
