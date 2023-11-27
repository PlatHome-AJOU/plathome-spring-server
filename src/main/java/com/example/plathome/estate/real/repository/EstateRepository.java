package com.example.plathome.estate.real.repository;

import com.example.plathome.estate.real.domain.Estate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstateRepository extends JpaRepository<Estate, Long>, EstateRepositoryCustom{
    Optional<Estate> findByMemberId(long memberId);
}
