package com.msp.membership.service;
import javax.transaction.Transactional;

import com.msp.membership.repository.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowService {

    @Autowired
    FollowRepository followRepository;

    @Transactional
    public void saveFollow(int toUserId, int fromUserId) {
        followRepository.saveFollow(toUserId, fromUserId);
    }

    @Transactional
    public void deleteFollow(int toUserId, int fromUserId) {
        followRepository.deleteByToUserIdAndFromUserId(toUserId, fromUserId);
    }
}
