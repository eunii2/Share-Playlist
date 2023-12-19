package com.msp.membership.service;

import com.msp.membership.entity.Follow;
import com.msp.membership.entity.Member;
import com.msp.membership.repository.FollowRepository;
import com.msp.membership.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;

@RequiredArgsConstructor
@Service
public class FollowService {
    private final MemberRepository memberRepository;

    @Autowired
    FollowRepository followRepository;
    @Autowired
    MemberService memberService;

    public void save(Long page_id) { // 팔로우
        Follow follow = new Follow();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        Member member = memberRepository.findByUserid(userId);

        if (member == null) {
            throw new EntityNotFoundException("Member not found with user id : " + userId);
        }
        follow.setFollowing(member);
        follow.setFollowed(memberService.findById(page_id));
        follow.setFollow_date(timestamp);

        followRepository.save(follow);
    }
    public void deleteByFromUserIdAndToUserId(Long id1, Long id2) { // 언팔로우
        followRepository.deleteByFromUserIdAndToUserId(id2, id1);
    }

    public boolean find(Long id, String userid) { // 팔로우가 되어있는지를 확인하기위해
        if(followRepository.countByFromUserIdAndToUserId(id, userid) == 0)
            return false; // 팔로우 안되어있음
        return true; // 되어있음
    }
}
