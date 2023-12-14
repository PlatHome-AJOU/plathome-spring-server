package com.example.plathome.requested_estate.repository;

import com.example.plathome.requested_estate.domain.Requested;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RequestedRepository extends JpaRepository<Requested, Long> {
    Optional<Requested> findByMemberId(long memberId);
    void deleteByMemberId(long memberId);
}
