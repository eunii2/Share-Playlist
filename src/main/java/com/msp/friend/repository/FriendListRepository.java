package com.msp.friend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.msp.friend.entity.FriendList;

@Repository
public interface FriendListRepository extends JpaRepository<FriendList, Integer>{
        @Query("SELECT fl.id, fl.id2.userid, fl.id2.username FROM FriendList fl WHERE fl.id1.userid=:id1") // 주어진 사용자의 ID를 가진 사용자의 친구 목록을 조회하는 쿼리
        public abstract List<List<String>> findId2ById1Userid(String id1);  // 메소드 선언

        @Query("SELECT fl FROM FriendList fl WHERE fl.id1.userid=:id1 AND fl.id2.userid=:id2")  // 두 사용자의 친구 관계를 조회하는 쿼리
        public abstract Optional<FriendList> findMyFunction(String id1, String id2);  // 메소드 선언
}