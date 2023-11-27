package com.example.plathome.wish_list.service;
import com.example.plathome.estate.real.domain.Estate;
import com.example.plathome.estate.real.dto.response.EstateResponse;
import com.example.plathome.estate.real.exception.NotFoundEstateException;
import com.example.plathome.estate.real.repository.EstateRepository;
import com.example.plathome.global.exception.NotFoundException;
import com.example.plathome.wish_list.domain.WishList;
import com.example.plathome.wish_list.dto.WishListResponse;
import com.example.plathome.wish_list.exception.DuplicateWishListException;
import com.example.plathome.wish_list.exception.UnAuthorizedWishListException;
import com.example.plathome.wish_list.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class WishListService {

    private final WishListRepository wishListRepository;
    private final EstateRepository estateRepository;

    public List<WishListResponse> getWishListByMemberId(Long memberId) {
        List<WishList> wishList = wishListRepository.findByMemberId(memberId);
        List<WishListResponse> responseList = new ArrayList<>();

        for (WishList wishListItem : wishList) {
            Estate estate = estateRepository.findById(wishListItem.getEstateId())
                    .orElseThrow(() -> new NotFoundEstateException());

            EstateResponse estateResponse = EstateResponse.from(estate);
            WishListResponse wishListResponse = WishListResponse.customfrom(estateResponse);

            responseList.add(wishListResponse);
        }

        return responseList;
    }



    public void addToWishList(Long memberId, Long estateId) {

        Optional<Estate> estate = estateRepository.findById(estateId);

        Estate foundEstate = estate.orElseThrow(() ->  new NotFoundEstateException());

        Optional<WishList> existingWishList = wishListRepository.findByMemberIdAndEstateId(memberId, estateId);

        existingWishList.ifPresent(wishList -> {
            throw new DuplicateWishListException("이미 찜 목록에 등록한 매물입니다.");
        });

        WishList wishList = new WishList(memberId, estateId);
        wishListRepository.save(wishList);
    }

    public void deleteWishList(Long id, Long memberId){
        Optional<WishList> existingWishList = wishListRepository.findById(id);

        if (existingWishList.isPresent() && existingWishList.get().getMemberId() == memberId) {
            throw new  UnAuthorizedWishListException("해당 유저의 찜목록이 아니므로 삭제할 수 없습니다.");
        }

        wishListRepository.deleteById(id);
    }
}