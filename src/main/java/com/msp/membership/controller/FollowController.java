package com.msp.membership.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msp.config.auth.CustomUserDetails;
import com.msp.membership.service.FollowService;

@RestController
public class FollowController{

    @Autowired
    FollowService followService;

    @PostMapping("/api/subscribe/{toUserId}")
    public ResponseEntity<String> follow(@PathVariable int toUserId, @AuthenticationPrincipal CustomUserDetails principal){

        followService.saveFollow(toUserId, principal.getId());
        return ResponseEntity.ok().body("친구추가 완료");
    }

    @DeleteMapping("/api/subscribe/{toUserId}")
    public ResponseEntity<String> unfollow(@PathVariable int toUserId, @AuthenticationPrincipal CustomUserDetails principal){

        followService.deleteFollow(toUserId, principal.getId());
        return ResponseEntity.ok().body("친구추가 해제");
    }

}
