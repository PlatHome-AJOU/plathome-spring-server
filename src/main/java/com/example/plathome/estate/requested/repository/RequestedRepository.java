package com.example.plathome.estate.requested.repository;

import com.example.plathome.estate.requested.domain.Requested;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RequestedRepository extends JpaRepository<Requested, Long> {
    Optional<Requested> findByUserId(String userId);
}
