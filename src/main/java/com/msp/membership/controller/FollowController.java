package com.msp.membership.controller;

import com.msp.membership.config.auth.CustomUserDetails;
import com.msp.membership.service.FollowService;
import com.msp.membership.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/follow")
public class FollowController{

    @Autowired
    FollowService followService;

    @Autowired
    MemberService memberService;

    @PostMapping("/{toUserId}")
    public ResponseEntity<String> follow(@PathVariable int toUserId, @AuthenticationPrincipal CustomUserDetails principal){

        followService.Follow(toUserId, principal.getId());
        return ResponseEntity.ok().body("친구추가 완료");
    }

    @DeleteMapping("/{toUserId}")
    public ResponseEntity<String> unfollow(@PathVariable int toUserId, @AuthenticationPrincipal CustomUserDetails principal){

        followService.unFollow(toUserId, principal.getId());
        return ResponseEntity.ok().body("친구추가 해제");
    }

}
