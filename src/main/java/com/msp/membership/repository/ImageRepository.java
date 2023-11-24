package com.msp.membership.repository;

import com.msp.membership.entity.ImageEntity;
import com.msp.membership.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
    ImageEntity findByMemberEntity(MemberEntity memberEntity);
}