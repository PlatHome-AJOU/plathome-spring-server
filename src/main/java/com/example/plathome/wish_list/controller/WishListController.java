package com.example.plathome.wish_list.controller;

import com.example.plathome.login.argumentresolver_interceptor.argumentresolver.Login;
import com.example.plathome.member.domain.MemberSession;
import com.example.plathome.wish_list.dto.WishListResponse;
import com.example.plathome.wish_list.service.WishListService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/wish-list/auth")
@RequiredArgsConstructor
@RestController
public class WishListController {

    private final WishListService wishListService;

    @PostMapping("/estate/{estateId}")
    public ResponseEntity<Void> addToWishList(
            @Parameter(hidden = true) @Login MemberSession memberSession,
            @PathVariable Long estateId
    ) {
        wishListService.addToWishList(memberSession.id(), estateId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{wishListId}")
    public ResponseEntity<Void> deleteWishList(
            @Parameter(hidden = true) @Login MemberSession memberSession,
            @PathVariable Long wishListId
    ) {

        wishListService.deleteWishList(wishListId, memberSession.id());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<WishListResponse>> getWishList(
            @Parameter(hidden = true) @Login MemberSession memberSession
    ) {
        List<WishListResponse> wishList = wishListService.getWishListByMemberId(memberSession.id());
        return ResponseEntity.ok(wishList);
    }
}