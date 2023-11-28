package com.msp.profileImage.repository;

import com.msp.profileImage.entity.Image;
import com.msp.membership.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findByMember(Member member);
}