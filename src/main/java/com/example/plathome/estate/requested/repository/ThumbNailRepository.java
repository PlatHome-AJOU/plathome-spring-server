package com.example.plathome.estate.requested.repository;

import com.example.plathome.estate.requested.domain.ThumbNail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThumbNailRepository extends JpaRepository<ThumbNail, Long> {
    void deleteByMemberId(Long memberId);
    List<ThumbNail> findByMemberId(Long memberId);
    List<ThumbNail> findByMemberIdIn(List<Long> memberId);
}
