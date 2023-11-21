package com.msp.membership.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.msp.membership.entity.FriendRequestEntity;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequestEntity, Integer> {
    @Query("SELECT fr.requestId.userid, fr.requestedId.userid FROM FriendRequestEntity fr WHERE fr.requestId.userid=:requestId")
    public abstract List<List<String>> findFriendRequestByRequestId(String requestId);

    @Query("SELECT fr.requestId.userid, fr.requestId.username, fr.Id FROM FriendRequestEntity fr WHERE fr.requestedId.userid=:requestedId")
    public abstract List<List<String>> findFriendRequestByRequestedId(String requestedId);

    public abstract List<FriendRequestEntity> findByRequestIdUseridAndRequestedIdUserid(String id1, String id2);


}
