package com.msp.friend.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.msp.friend.entity.FriendRequest;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Integer> {
    @Query("SELECT fr.requestId.userid, fr.requestedId.userid FROM FriendRequest fr WHERE fr.requestId.userid=:requestId")
    public abstract List<List<String>> findFriendRequestByRequestId(String requestId);

    @Query("SELECT fr.requestId.userid, fr.requestId.username, fr.id FROM FriendRequest fr WHERE fr.requestedId.userid=:requestedId")
    public abstract List<List<String>> findFriendRequestByRequestedId(String requestedId);

    public abstract List<FriendRequest> findByRequestIdUseridAndRequestedIdUserid(String id1, String id2);


}
