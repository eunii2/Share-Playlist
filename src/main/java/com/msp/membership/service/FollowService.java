package com.msp.membership.service;

import com.msp.membership.config.handler.CustomApiException;
import com.msp.membership.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class FollowService {

    private final FollowRepository followRepository;

    @Transactional
    public void Follow(int toUserId, int fromUserId) {
        try{
        followRepository.saveFollow(toUserId, fromUserId);
        }catch (Exception e) {
            throw new CustomApiException("이미 팔로우 하고 있는 유저입니다.");
        }
    }

    @Transactional
    public void unFollow(int toUserId, int fromUserId) {
        followRepository.deleteFollow(toUserId, fromUserId);
    }
}
