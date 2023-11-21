package com.msp.membership.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.msp.membership.entity.MemberEntity;
import com.msp.membership.entity.FriendListEntity;

@Repository
public interface FriendListRepository extends JpaRepository<FriendListEntity, Integer>{
        @Query("SELECT fl.id, fl.id2.userid, fl.id2.username FROM FriendListEntity fl WHERE fl.id1.userid=:id1")
        public abstract List<List<String>> findId2ById1Userid(String id1);

        @Query("SELECT fl FROM FriendListEntity fl WHERE fl.id1.userid=:id1 AND fl.id2.userid=:id2")
        public abstract Optional<FriendListEntity> findMyFunction(String id1, String id2);
}