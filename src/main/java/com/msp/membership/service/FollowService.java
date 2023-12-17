package com.msp.membership.service;

import com.msp.membership.entity.Follow;
import com.msp.membership.entity.Member;
import com.msp.membership.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FollowService {

    @Autowired
    FollowRepository followRepository;
    @Autowired
    MemberService memberService;

    public void save(Long login_id, Long page_id) { // 팔로우
        Follow follow = new Follow();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        follow.setFollowing(memberService.findById(login_id));
        follow.setFollower(memberService.findById(page_id));
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
    public List<Member> getFollowingMembers(Member member) {
        return followRepository.findByFromUser(member).stream()
                .map(Follow::getToUser)
                .collect(Collectors.toList());
    }
}
