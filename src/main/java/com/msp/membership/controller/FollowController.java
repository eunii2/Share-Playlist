package com.msp.membership.controller;

import com.msp.membership.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class FollowController{

    @Autowired
    FollowService followService;

    @RequestMapping("/follow")
    public ResponseEntity<String> follow(HttpServletRequest request, Model model) throws Exception {
        String to = request.getParameter("to_id");

        Long to_id = Long.parseLong(to);

        followService.save(to_id);

        return ResponseEntity.ok().body("팔로우");
    }

    @RequestMapping("/unfollow")
    public ResponseEntity<String> unfollow(HttpServletRequest request, Model model) throws Exception {
        String from = request.getParameter("from_id");
        String to = request.getParameter("to_id");

        Long from_id = Long.parseLong(from);
        Long to_id = Long.parseLong(to);

        followService.deleteByFromUserIdAndToUserId(to_id, from_id);

        return ResponseEntity.ok().body("언팔로우");
    }
}
