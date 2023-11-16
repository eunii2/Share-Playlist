package com.msp.membership.controller;

import com.msp.membership.dto.MemberDTO;
import com.msp.membership.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class MemberController {
    // 생성자 주입
    private final MemberService memberService; //매서드를 사용할 수 있게하는 권한을 줌
    // 회원 가입 페이지 출력 요청
    @GetMapping("/member/join")
    public String joinForm(){
        return "/login/join";
    }

    @PostMapping("/member/join")
    public String join( //파라미터를 스트링 변수로 받아서 서비스클래스 -> 레파지토 -> 데이터베이스 (웹 페이지의 회원가입 중 회원 정보가 데이터 베이스에 저장됨)
                        @ModelAttribute MemberDTO memberDTO)
    {//네임 값을 String userid에 옮겨 담는다.
        System.out.println("MemberController.save");
        System.out.println("memberDTO = " + memberDTO); //전달 받은 값을 db로 보
        memberService.join(memberDTO);
        return "/login/login";
    }

    @GetMapping("/member/login")
    public String loginForm() {
        return "login/login";
    }

    @PostMapping("/member/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session){
        MemberDTO loginResult = memberService.login(memberDTO);
        if(loginResult != null) {
            session.setAttribute("loginId", loginResult.getUserid());
            //로그인한 아이디의 세션 정보를 사용함
            return "main";
        }
        else{
            //로그인 실패
            return "login/login";
        }
    }

    @PostMapping("/member/id-check")
    public @ResponseBody String idCheck(@RequestParam("userid") String userid){
        System.out.println("userid = " + userid);
        String checkResult = memberService.idCheck(userid);
        if(checkResult != null) {
            return "ok";
        }
        else{
            return "no";
        }
    }
}
