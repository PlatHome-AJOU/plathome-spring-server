package com.example.plathome.service;

import com.example.plathome.ObjectBuilder;
import com.example.plathome.estate_report.dto.request.EstateReportForm;
import com.example.plathome.estate_report.exception.OwnEstateReportException;
import com.example.plathome.member.domain.MemberSession;
import com.example.plathome.member.exception.NotFoundMemberException;
import com.example.plathome.member.repository.MemberRepository;
import com.example.plathome.user_report.domain.UserReport;
import com.example.plathome.user_report.dto.request.UserReportForm;
import com.example.plathome.user_report.dto.response.UserReportResponse;
import com.example.plathome.user_report.exception.NotFoundUserReportException;
import com.example.plathome.user_report.exception.OwnUserReportException;
import com.example.plathome.user_report.repository.UserReportRepository;
import com.example.plathome.user_report.service.UserReportService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("비지니스 로직 - 회원 신고")
@ExtendWith(MockitoExtension.class)
class UserReportServiceTest extends ObjectBuilder {
    @InjectMocks private UserReportService sut;
    @Mock private UserReportRepository userReportRepository;
    @Mock private MemberRepository memberRepository;

    @DisplayName("회원 신고: 타인 신고 - 200")
    @Test
    void givenMemberSessionAndUserReportForm_whenReportingUser_thenReturnsSuccess(){
        //given
        MemberSession memberSession = createMemberSession(ID_2);
        UserReportForm userReportForm = createUserReportForm();
        given(memberRepository.findById(userReportForm.targetMemberId())).willReturn(Optional.of(createMember()));
        given(userReportRepository.save(any(UserReport.class))).willReturn(any(UserReport.class));

        //when
        sut.report(memberSession, userReportForm);

        //then
        then(memberRepository).should().findById(userReportForm.targetMemberId());
        then(userReportRepository).should().save(any(UserReport.class));
    }

    @DisplayName("회원 신고: 존재하지 않는 회원일 경우 - 404")
    @Test
    void givenNonExistentMemberId_whenReportingUser_thenReturnsNotFoundException(){
        //given
        MemberSession memberSession = createMemberSession(ID_1);
        UserReportForm userReportForm = createUserReportForm();
        given(memberRepository.findById(userReportForm.targetMemberId())).willReturn(Optional.empty());

        //when & then
        assertThrows(NotFoundMemberException.class, () -> sut.report(memberSession, userReportForm));
        then(memberRepository).should().findById(userReportForm.targetMemberId());
        then(userReportRepository).shouldHaveNoInteractions();
    }

    @DisplayName("회원 신고: 본인 신고 - 403")
    @Test
    void givenOwnMemberId_whenReportingEstate_thenThrowsOwnEstateReportException(){
        //given
        MemberSession memberSession = createMemberSession(ID_1);
        UserReportForm userReportForm = createUserReportForm();
        given(memberRepository.findById(userReportForm.targetMemberId())).willReturn(Optional.of(createMember()));

        //when & then
        assertThrows(OwnUserReportException.class, () -> sut.report(memberSession, userReportForm));
        then(memberRepository).should().findById(userReportForm.targetMemberId());
        then(userReportRepository).shouldHaveNoInteractions();
    }

    @DisplayName("회원 신고 단일 조회: 주어진 회원 신고 ID로 조회 시, 회원 신고 내역 응답 반환 - 200")
    @Test
    void givenUserReportId_whenGettingById_thenReturnsUserReportResponse(){
        //given
        UserReport userReport = createUserReport();
        given(userReportRepository.findById(ID_1)).willReturn(Optional.of(userReport));

        //when
        UserReportResponse userReportResponse = sut.getById(ID_1);

        //then
        assertThat(userReportResponse)
                .hasFieldOrPropertyWithValue("reportMemberId", userReport.getReportMemberId())
                .hasFieldOrPropertyWithValue("targetMemberId", userReport.getTargetMemberId())
                .hasFieldOrPropertyWithValue("context", userReport.getContext());
        then(userReportRepository).should().findById(ID_1);
    }

    @DisplayName("회원 신고 단일 조회: 존재하지 않는 회원 신고 내역 일 경우 - 404")
    @Test
    void givenNonExistentUserReportId_whenGettingById_thenReturnsNotFoundException(){
        //given
        given(userReportRepository.findById(ID_1)).willReturn(Optional.empty());

        //when & then
        assertThrows(NotFoundUserReportException.class, () -> sut.getById(ID_1));
        then(userReportRepository).should().findById(ID_1);
    }

    @DisplayName("회원 신고 전체 조회: 전체 조회 - 200")
    @Test
    void givenNothing_whenGettingAll_thenReturnsUserReportResponseList(){
        //given
        UserReport userReport = createUserReport();
        given(userReportRepository.findAll()).willReturn(List.of(userReport));

        //when
        List<UserReportResponse> userReportResponseList = sut.getAll();

        //then
        assertThat(userReportResponseList)
                .hasSize(1)
                .extracting(UserReportResponse::reportMemberId).containsExactly(userReport.getReportMemberId());
        then(userReportRepository).should().findAll();
    }

    @DisplayName("회원 신고 삭제: 회원 신고 ID로 삭제 - 204")
    @Test
    void givenUserReportId_whenDeleting_thenReturnsNoContent() {
        //given
        willDoNothing().given(userReportRepository).deleteById(ID_1);

        //when
        sut.delete(ID_1);

        //then
        then(userReportRepository).should().deleteById(ID_1);
    }
}
