package com.msp.membership.controller;

import com.msp.membership.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class FollowController{

    @Autowired
    FollowService followService;

    @RequestMapping("/follow")
    public String follow(HttpServletRequest request, Model model) throws Exception {
        String from = request.getParameter("from_id");
        String to = request.getParameter("to_id");

        int from_id = Integer.parseInt(from);
        int to_id = Integer.parseInt(to);

        followService.save(from_id, to_id);

        String redirect_url = "redirect:/main/user/" + to_id;

        return redirect_url;
    }

    @RequestMapping("/unfollow")
    public String unfollow(HttpServletRequest request, Model model) throws Exception {
        String from = request.getParameter("from_id");
        String to = request.getParameter("to_id");

        int from_id = Integer.parseInt(from);
        int to_id = Integer.parseInt(to);

        followService.deleteByFromUserIdAndToUserId(to_id, from_id);
        String redirect_url = "redirect:/main/user/" + to_id;

        return redirect_url;
    }

    /*
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
    */
}
