package com.example.plathome.requested_estate.repository;

import com.example.plathome.requested_estate.domain.ThumbNail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThumbNailRepository extends JpaRepository<ThumbNail, Long> {
    void deleteByMemberId(Long memberId);
    List<ThumbNail> findByMemberId(Long memberId);
    List<ThumbNail> findByMemberIdIn(List<Long> memberId);
}
