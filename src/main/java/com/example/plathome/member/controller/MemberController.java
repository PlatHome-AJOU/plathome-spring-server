package com.example.plathome.member.controller;

import com.example.plathome.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/member")
@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/no-auth/{memberId}")
    public String getNickname(
            @PathVariable long memberId
    ) {
        return memberService.getNickname(memberId);
    }
}
