package com.msp.playlist.repository;

import com.msp.playlist.entity.TagMood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagMoodRepository extends JpaRepository<TagMood, Long> {
}
