package com.msp.membership.controller;

import com.msp.membership.config.auth.CustomUserDetails;
import com.msp.membership.dto.UserProfileDTO;
import com.msp.membership.service.FollowService;
import com.msp.membership.service.MemberService;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class FollowController{

    @Autowired
    FollowService followService;

    @Autowired
    MemberService memberService;

    @PostMapping("/follow/{toUserId}")
    public ResponseEntity<String> follow(@PathVariable int toUserId, @AuthenticationPrincipal CustomUserDetails principal){

        followService.saveFollow(toUserId, principal.getId());
        return ResponseEntity.ok().body("친구추가 완료");
    }

    @DeleteMapping("/follow/{toUserId}")
    public ResponseEntity<String> unfollow(@PathVariable int toUserId, @AuthenticationPrincipal CustomUserDetails principal){

        followService.deleteFollow(toUserId, principal.getId());
        return ResponseEntity.ok().body("친구추가 해제");
    }

    @GetMapping("/user/profile/{id}")
    public String profile(@PathVariable int id, Model model, Authentication authentication) {

        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        UserProfileDTO userProfileDTO = memberService.findById(id, principal.getId());

        model.addAttribute("profileDto", userProfileDTO);

        return "user/profile";
    }

}
