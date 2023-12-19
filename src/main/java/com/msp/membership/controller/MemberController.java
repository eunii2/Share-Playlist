package com.msp.membership.controller;

import com.msp.membership.dto.MemberDTO;
import com.msp.membership.entity.Follow;
import com.msp.membership.entity.Member;
import com.msp.membership.repository.FollowRepository;
import com.msp.membership.repository.MemberRepository;
import com.msp.membership.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private MemberRepository memberRepository;

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

    /* 유저페이지 */
    @RequestMapping("/main/user/{userid}")
    public ResponseEntity<Member> main_user(@PathVariable("userid") String userid, Model model) throws Exception {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("member", memberService.findByUserid(userid));
        return ResponseEntity.ok(memberService.getMyUserWithAuthorities().get());
    }

    /* 다른 유저 페이지 */
    @GetMapping("/main/user/{userid}")
    public ResponseEntity<Member> getUserById(@PathVariable String userid) {
        Member member = memberService.findByUserid(userid);
        if (member == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(memberService. getUserWithAuthorities(userid).get());
    }

    /* 프로필 이미지 업로드 */
    @PostMapping("/main/profileImage")
    public ResponseEntity<String> image_insert(HttpServletRequest request, @RequestParam("filename") MultipartFile mFile, Model model) throws Exception {
        String upload_path = "C:/Users/ASUS/Pictures";   // 서버 환경에 맞게 저장 경로 바꿔야함
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

    /* 다른 유저 프로필 사진 불러오기 */
    @GetMapping("/main/UserProfileImage")
    public ResponseEntity<Resource> getUserProfileImage() {

        // 현재 로그인한 사용자의 userid 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        Member member = memberService.findByUserid(currentPrincipalName);
        String profileImage = member.getProfileImage();

        if (profileImage != null) {
            Path filePath = Paths.get("C:/Users/ASUS/Pictures" + profileImage);
            Resource resource = null;
            try {
                resource = new UrlResource(filePath.toUri());

                if(resource.exists()) {
                    return ResponseEntity.ok()
                            .contentType(MediaType.parseMediaType("image/jpeg"))
                            .body(resource);
                } else {
                    resource = new ByteArrayResource(new byte[0]);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .contentType(MediaType.parseMediaType("text/plain"))
                            .body(resource);
                }

            } catch (MalformedURLException e) {
                resource = new ByteArrayResource(new byte[0]);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .contentType(MediaType.parseMediaType("text/plain"))
                        .body(resource);
            }
        }

        Resource resource = new ByteArrayResource(new byte[0]);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.parseMediaType("text/plain"))
                .body(resource);
    }

    /* 로그인한 유저 프로필 사진 불러오기 */
    @GetMapping("/main/presentProfileImage")
    public ResponseEntity<Resource> getpresentProfileImage() {

        // 현재 로그인한 사용자의 userid 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        Member member = memberService.findByUserid(currentPrincipalName);
        String profileImage = member.getProfileImage();

        if (profileImage != null) {
            Path filePath = Paths.get("C:/Users/ASUS/Pictures" + profileImage);
            Resource resource = null;
            try {
                resource = new UrlResource(filePath.toUri());

                if(resource.exists()) {
                    return ResponseEntity.ok()
                            .contentType(MediaType.parseMediaType("image/jpeg"))
                            .body(resource);
                } else {
                    resource = new ByteArrayResource(new byte[0]);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .contentType(MediaType.parseMediaType("text/plain"))
                            .body(resource);
                }

            } catch (MalformedURLException e) {
                resource = new ByteArrayResource(new byte[0]);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .contentType(MediaType.parseMediaType("text/plain"))
                        .body(resource);
            }
        }

        Resource resource = new ByteArrayResource(new byte[0]);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.parseMediaType("text/plain"))
                .body(resource);
    }

    /* 유저 검색 */
    @RequestMapping(value = "/main/userSearch")
    public String search(@RequestParam("word") String word, Model model) throws Exception {
        if (word == null || word.equals("")) {
            return "redirect:/main/recommend";
        }

        model.addAttribute("find_member", memberService.findByUseridContains(word));
        model.addAttribute("mcnt", memberService.countByUseridContains(word));
        model.addAttribute("word", word);

        return "main/search";   //프론트에서 연결해야함;
    }

    @GetMapping("/friendList")
    public ResponseEntity<List<Map<String, Object>>> getFriendList(Principal principal) {
        String userid = principal.getName();
        Member member = memberRepository.findByUserid(userid);
        List<Follow> follows = followRepository.findByFromUser(member);
        List<Map<String, Object>> result = follows.stream()
                .map(follow -> {
                    Member friend = follow.getFollowed();
                    Map<String, Object> map = new HashMap<>();
                    map.put("userid", friend.getUserid());
                    map.put("profileImage", friend.getProfileImage());
                    return map;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }


}