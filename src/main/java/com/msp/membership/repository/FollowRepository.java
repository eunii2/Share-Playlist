package com.msp.membership.repository;

import com.msp.membership.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface FollowRepository extends JpaRepository<Follow, Integer> {


        @Modifying
        @Query(value= "insert into Follow(toUserId, fromUserId, createDate) VALUES(:toUserId, :fromUserId, createDate)", nativeQuery = true)
        void saveFollow(@Param("toUserId") int toUserId, @Param("fromUserId") int fromUserId);

        @Modifying
        @Query("delete from Follow where toUserId = :toUserId and fromUserId = :fromUserId")
        void deleteFollow(@Param("toUserId") int toUserId, @Param("fromUserId") int fromUserId);

        int countByFromUserIdAndToUserId(int id, String userid);

        @Modifying
        @Transactional
        void deleteByFromUserIdAndToUserId(int id1, int id2);

        /* List<Follow> findByFollowingId(int id); */

}

