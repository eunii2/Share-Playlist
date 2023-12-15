package com.msp.membership.controller;

import com.msp.membership.dto.MemberDTO;
import com.msp.membership.entity.Member;
import com.msp.membership.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/main/join")
    public String joinForm(){
        return "/login/join";
    }

    @PostMapping("/main/join")
    public ResponseEntity<Member> join(@Valid @RequestBody MemberDTO memberDTO){
        return ResponseEntity.ok(memberService.join(memberDTO));
    }
    
    @GetMapping("/main/login")
    public String loginForm() {
        return "login";
    }


    @GetMapping("/myinfo")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Member> getMyUserInfo(){
        return ResponseEntity.ok(memberService.getMyUserWithAuthorities().get());
    }


    /* 유저페이지 */
    @RequestMapping("/main/user/{id}")
    public String main_user(@PathVariable("id") Long id, Model model) throws Exception {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("member", memberService.findById(id));

        return "/main/user";
    }
/*
    @RequestMapping("/main/user/{id}")
    public String main_user(@PathVariable("id") Long id, Model model) throws Exception {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("page_id", id); // PathVariable로 넘어온 id - 이 페이지의 id
        model.addAttribute("follow", followService.find(id, userId)); // false or true
    }
    */

    /* 프로필 수정 */
    @RequestMapping(value = "/main/user/update/{id}", method = RequestMethod.GET)
    public String update_user(@PathVariable("id") Long id, Model model) throws Exception {
        String userid = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("member", memberService.findByUserid(userid));
        return "/main/user/update";
    }

    @RequestMapping(value = "/main/user/image_insert")
    public String image_insert(HttpServletRequest request, @RequestParam("filename") MultipartFile mFile, Model model) throws Exception {
        String upload_path = "D:/OutStagram/Instagram/outstagram/src/main/resources/static/images/profile/"; // 프로필 사진들 모아두는 폴더
        String userid = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberService.findByUserid(userid);
        String redirect_url = "redirect:/main/user/update/" + member.getId(); // 사진업로드 이후 redirect될 url

        try {
            if (member.getProfileImage() != null) { // 이미 프로필 사진이 있을경우
                File file = new File(upload_path + member.getProfileImage()); // 경로 + 유저 프로필사진 이름을 가져와서
                file.delete(); // 원래파일 삭제
            }
            mFile.transferTo(new File(upload_path + mFile.getOriginalFilename()));  // 경로에 업로드
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }

        memberService.img_update(userid, mFile.getOriginalFilename()); // 프로필 사진이름 db에 update
        return redirect_url;
    }

    @RequestMapping(value = "main/search")
    public String search(@RequestParam("word") String word, Model model) throws Exception {
        if (word == null || word.equals("")) {
            return "redirect:/main/recommend";
        }

        model.addAttribute("find_member", memberService.findByUseridContains(word));
        model.addAttribute("mcnt", memberService.countByUseridContains(word));
        model.addAttribute("word", word);

        return "main/search";
    }
}
