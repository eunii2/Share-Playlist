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
import java.util.UUID;

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

    /* 유저 정보 확인용 */
    @GetMapping("/myinfo")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Member> getMyUserInfo(){
        return ResponseEntity.ok(memberService.getMyUserWithAuthorities().get());
    }

    /* 유저페이지 */
    @RequestMapping("/main/user/{userid}")
    public String main_user(@PathVariable("userid") String userid, Model model) throws Exception {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("member", memberService.findByUserid(userid));

        return "/main/user";
    }

    /* 프로필 수정 페이지 */
    @RequestMapping(value = "/main/user/update/{userid}", method = RequestMethod.GET)
    public String update_user(@PathVariable("userid") String userid, Model model) throws Exception {
        String loggedInUserId = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!userid.equals(loggedInUserId)) {
            throw new Exception("Unauthorized access");
        }

        model.addAttribute("member", memberService.findByUserid(userid));
        return "/main/user/update";
    }


    /* 프로필 이미지 업로드 */
    @PostMapping("/main/user/update/profileImage")
    public String image_insert(HttpServletRequest request, @RequestParam("filename") MultipartFile mFile, Model model) throws Exception {
        String upload_path = "C:/Users/ASUS/Desktop/test/ODP/src/main/resources/static/img/profile/";   // 서버 환경에 맞게 저장 경로 바꿔야함
        String userid = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberService.findByUserid(userid);
        String redirect_url = "redirect:/main/user/update/" + member.getId();

        try {
            if (member.getProfileImage() != null) { // 이미 프로필 사진이 있을경우
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
        return redirect_url;
    }


    /* 유저 검색 */
    @RequestMapping(value = "main/userSearch")
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
