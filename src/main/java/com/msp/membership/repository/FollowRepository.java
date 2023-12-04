package com.msp.membership.repository;

import com.msp.membership.entity.Follow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FollowRepository extends JpaRepository<Follow, Integer> {

        @Modifying
        @Query(value= "insert into Follow(toUserId, fromUserId, createDate) VALUES(:toUserId, :fromUserId, now())", nativeQuery = true)
        void saveFollow(@Param("toUserId") int toUserId, @Param("fromUserId") int fromUserId);

        @Modifying
        @Query("delete from Follow where toUserId = :toUserId and fromUserId = :fromUserId")
        void deleteFollow(@Param("toUserId") int toUserId, @Param("fromUserId") int fromUserId);

        int countByToUserId(int toUserId);

        boolean existsByToUserIdAndFromUserId(int toUserId, int fromUserId);
}

