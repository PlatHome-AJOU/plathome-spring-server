package com.example.plathome.wish_list.repository;

import com.example.plathome.wish_list.domain.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {
    List<WishList> findByMemberId(long memberId);

    Optional<WishList> findByMemberIdAndEstateId(long memberId, long estateId);
}
