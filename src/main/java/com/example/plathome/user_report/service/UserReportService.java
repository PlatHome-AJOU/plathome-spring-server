package com.example.plathome.user_report.service;

import com.example.plathome.member.domain.Member;
import com.example.plathome.member.domain.MemberSession;
import com.example.plathome.member.exception.NotFoundMemberException;
import com.example.plathome.member.repository.MemberRepository;
import com.example.plathome.user_report.dto.request.UserReportForm;
import com.example.plathome.user_report.dto.response.UserReportResponse;
import com.example.plathome.user_report.exception.NotFoundUserReportException;
import com.example.plathome.user_report.exception.OwnUserReportException;
import com.example.plathome.user_report.repository.UserReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserReportService {
    private final UserReportRepository userReportRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void report(MemberSession memberSession, UserReportForm userReportForm) {
        Member member = memberRepository.findById(userReportForm.targetMemberId()).orElseThrow(NotFoundMemberException::new);
        getValidateUser(memberSession.id(), member.getId());
        userReportRepository.save(userReportForm.toEntity(memberSession.id()));
    }
    private static void getValidateUser(long memberId, long expectedId) {
        if (memberId == expectedId) {
            throw new OwnUserReportException();
        }
    }


    public UserReportResponse getById(long userReportId) {
        return userReportRepository.findById(userReportId)
                .map(UserReportResponse::from)
                .orElseThrow(NotFoundUserReportException::new);
    }

    public List<UserReportResponse> getAll() {
        return userReportRepository.findAll().stream()
                .map(UserReportResponse::from)
                .toList();
    }

    @Transactional
    public void delete(long userReportId) {
        userReportRepository.deleteById(userReportId);
    }
}
