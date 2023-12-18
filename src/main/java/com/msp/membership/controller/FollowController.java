package com.msp.membership.controller;

import com.msp.membership.entity.Member;
import com.msp.membership.repository.MemberRepository;
import com.msp.membership.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@RestController
public class FollowController{

    @Autowired
    FollowService followService;
    MemberRepository memberService;

    @RequestMapping("/follow")
    public ResponseEntity<String> follow(HttpServletRequest request, Model model) throws Exception {
        String from = request.getParameter("from_id");
        String to = request.getParameter("to_id");

        Long from_id = Long.parseLong(from);
        Long to_id = Long.parseLong(to);

        followService.save(from_id, to_id);

        return ResponseEntity.ok().body("팔로우 완료");
    }

    @RequestMapping("/unfollow")
    public ResponseEntity<String> unfollow(HttpServletRequest request, Model model) throws Exception {
        String from = request.getParameter("from_id");
        String to = request.getParameter("to_id");

        Long from_id = Long.parseLong(from);
        Long to_id = Long.parseLong(to);

        followService.deleteByFromUserIdAndToUserId(to_id, from_id);
        return ResponseEntity.ok().body("팔로우 해제");
    }

    @RequestMapping(value = "/main/following")
    public String following(Model model, Principal principal) {


        Member member = memberService.findByUserid(principal.getName());
        List<Member> followingMembers = followService.getFollowingMembers(member);

        model.addAttribute("followingMembers", followingMembers);
        return "/main/following";
    }

}
