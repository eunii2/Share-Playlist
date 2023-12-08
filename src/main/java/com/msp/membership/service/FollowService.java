package com.msp.membership.service;

import com.msp.membership.entity.Follow;
import com.msp.membership.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@RequiredArgsConstructor
@Service
public class FollowService {

    @Autowired
    FollowRepository followRepository;
    @Autowired
    MemberService memberService;

    public void save(int login_id, int page_id) { // 팔로우
        Follow follow = new Follow();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        follow.setFollowing(memberService.findById(login_id));
        follow.setFollower(memberService.findById(page_id));
        follow.setFollow_date(timestamp);

        followRepository.save(follow);
    }
    public void deleteByFromUserIdAndToUserId(int id1, int id2) { // 언팔로우
        followRepository.deleteByFromUserIdAndToUserId(id2, id1);
    }

    public boolean find(int id, String userid) { // 팔로우가 되어있는지를 확인하기위해
        if(followRepository.countByFromUserIdAndToUserId(id, userid) == 0)
            return false; // 팔로우 안되어있음
        return true; // 되어있음
    }
/*
    public List<Follow> findByFollowingId(int id) {
        return followRepository.findByFollowingId(id);
    }
    */
}
