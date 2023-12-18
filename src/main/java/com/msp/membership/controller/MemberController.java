package com.msp.membership.controller;

import com.msp.membership.dto.MemberDTO;
import com.msp.membership.entity.Member;
import com.msp.membership.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<Member> join(@Valid @RequestBody MemberDTO memberDTO){
        return ResponseEntity.ok(memberService.join(memberDTO));
    }

    /* 유저페이지 */
    @RequestMapping("/main, /main/user/{userid}")
    public String main_user(@PathVariable("userid") String userid, Model model) throws Exception {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("member", memberService.findByUserid(userid));

        return "profile";
    }


    /* 프로필 이미지 업로드 */
    @PostMapping("/main/profileImage")
    public ResponseEntity<String> image_insert(HttpServletRequest request, @RequestParam("filename") MultipartFile mFile, Model model) throws Exception {
        String upload_path = "C:/Users/ASUS/Desktop/test/ODP/src/main/resources/static/img/profile/";   // 서버 환경에 맞게 저장 경로 바꿔야함
        String userid = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberService.findByUserid(userid);
        try {
            if (member.getProfileImage() != null) {
                File file = new File(upload_path + member.getProfileImage());
                file.delete();
            }

            String originalFileName = mFile.getOriginalFilename();
            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String newFileName = UUID.randomUUID().toString() + extension;
            mFile.transferTo(new File(upload_path + newFileName));
            member.setProfileImage(newFileName);
            memberService.img_update(userid, newFileName);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().body("프로필 사진 수정에 성공하였습니다");
    }


    /* 유저 검색 */
    @RequestMapping(value = "/main/friend")
    public String search(@RequestParam("word") String word, Model model) throws Exception {
        if (word == null || word.equals("")) {
            return "redirect:/main/friend";
        }

        Member member = memberService.findByUserid(word);

        if (member != null) {
            return "redirect:/main/user/" + member.getUserid();
        } else {
            return "redirect:/main/friend"; //그 유저의 페이지로 이동하도록 수정해야됨
        }
    }
}