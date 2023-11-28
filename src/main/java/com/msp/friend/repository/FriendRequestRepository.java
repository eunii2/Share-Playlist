package com.msp.friend.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.msp.friend.entity.FriendRequest;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Integer> {
    @Query("SELECT fr.requestId.userid, fr.requestedId.userid FROM FriendRequest fr WHERE fr.requestId.userid=:requestId") // 친구 요청을 보낸 사용자를 기준으로 친구 요청을 조회하는 쿼리
    public abstract List<List<String>> findFriendRequestByRequestId(String requestId);  // 친구 요청을 보낸 사용자를 기준으로 친구 요청을 조회하는 메소드 선언

    @Query("SELECT fr.requestId.userid, fr.requestId.username, fr.id FROM FriendRequest fr WHERE fr.requestedId.userid=:requestedId") // 친구 요청을 받은 사용자를 기준으로 친구 요청을 조회하는 쿼리
    public abstract List<List<String>> findFriendRequestByRequestedId(String requestedId);  // 친구 요청을 받은 사용자를 기준으로 친구 요청을 조회하는 메소드 선언

    public abstract List<FriendRequest> findByRequestIdUseridAndRequestedIdUserid(String id1, String id2);  // 두 사용자를 기준으로 친구 요청을 조회하는 메소드 선언


}
