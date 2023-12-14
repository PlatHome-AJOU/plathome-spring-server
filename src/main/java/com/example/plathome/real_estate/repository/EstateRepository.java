package com.example.plathome.real_estate.repository;

import com.example.plathome.real_estate.domain.Estate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstateRepository extends JpaRepository<Estate, Long>, EstateRepositoryCustom{
    Optional<Estate> findByMemberId(long memberId);
}
