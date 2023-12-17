package com.msp.membership.controller;

import com.msp.membership.entity.Member;
import com.msp.membership.repository.MemberRepository;
import com.msp.membership.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String follow(HttpServletRequest request, Model model) throws Exception {
        String from = request.getParameter("from_id");
        String to = request.getParameter("to_id");

        Long from_id = Long.parseLong(from);
        Long to_id = Long.parseLong(to);

        followService.save(from_id, to_id);

        String redirect_url = "redirect:/main/user/" + to_id;

        return redirect_url;
    }

    @RequestMapping("/unfollow")
    public String unfollow(HttpServletRequest request, Model model) throws Exception {
        String from = request.getParameter("from_id");
        String to = request.getParameter("to_id");

        Long from_id = Long.parseLong(from);
        Long to_id = Long.parseLong(to);

        followService.deleteByFromUserIdAndToUserId(to_id, from_id);
        String redirect_url = "redirect:/main/user/" + to_id;

        return redirect_url;
    }

    @RequestMapping(value = "/main/following")
    public String following(Model model, Principal principal) {


        Member member = memberService.findByUserid(principal.getName());
        List<Member> followingMembers = followService.getFollowingMembers(member);

        model.addAttribute("followingMembers", followingMembers);
        return "/main/following";
    }

}
