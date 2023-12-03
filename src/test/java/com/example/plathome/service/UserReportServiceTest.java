package com.example.plathome.service;

import com.example.plathome.ObjectBuilder;
import com.example.plathome.member.domain.MemberSession;
import com.example.plathome.member.exception.NotFoundMemberException;
import com.example.plathome.member.repository.MemberRepository;
import com.example.plathome.user_report.domain.UserReport;
import com.example.plathome.user_report.dto.request.UserReportForm;
import com.example.plathome.user_report.dto.response.UserReportResponse;
import com.example.plathome.user_report.exception.NotFoundUserReportException;
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

    @DisplayName("매물신고: input - MemberSession & UserReportForm | output - void")
    @Test
    void givenMemberSessionAndUserReportForm_whenReportingUser_thenReturnsSuccess(){
        //given
        MemberSession memberSession = createMemberSession(ID);
        UserReportForm userReportForm = createUserReportForm();
        given(memberRepository.findById(userReportForm.targetMemberId())).willReturn(Optional.of(createMember()));
        given(userReportRepository.save(any(UserReport.class))).willReturn(any(UserReport.class));

        //when
        sut.report(memberSession, userReportForm);

        //then
        then(memberRepository).should().findById(userReportForm.targetMemberId());
        then(userReportRepository).should().save(any(UserReport.class));
    }

    @DisplayName("매물신고: input - MemberSession & Wrong UserReportForm | output - 404")
    @Test
    void givenMemberSessionAndWrongUserReportForm_whenReportingUser_thenReturnsNotFoundException(){
        //given
        MemberSession memberSession = createMemberSession(ID);
        UserReportForm userReportForm = createUserReportForm();
        given(memberRepository.findById(userReportForm.targetMemberId())).willReturn(Optional.empty());

        //when & then
        assertThrows(NotFoundMemberException.class, () -> sut.report(memberSession, userReportForm));
        then(memberRepository).should().findById(userReportForm.targetMemberId());
        then(userReportRepository).shouldHaveNoInteractions();
    }

    @DisplayName("단일조회: input - UserReportId | output - UserReportResponse")
    @Test
    void givenUserReportId_whenGettingById_thenReturnsUserReportResponse(){
        //given
        UserReport userReport = createUserReport();
        given(userReportRepository.findById(ID)).willReturn(Optional.of(userReport));

        //when
        UserReportResponse userReportResponse = sut.getById(ID);

        //then
        assertThat(userReportResponse)
                .hasFieldOrPropertyWithValue("reportMemberId", userReport.getReportMemberId())
                .hasFieldOrPropertyWithValue("targetMemberId", userReport.getTargetMemberId())
                .hasFieldOrPropertyWithValue("context", userReport.getContext());
        then(userReportRepository).should().findById(ID);
    }

    @DisplayName("단일조회: input - Wrong UserReportId | output - 404")
    @Test
    void givenWrongUserReportId_whenGettingById_thenReturnsNotFoundException(){
        //given
        given(userReportRepository.findById(ID)).willReturn(Optional.empty());

        //when & then
        assertThrows(NotFoundUserReportException.class, () -> sut.getById(ID));
        then(userReportRepository).should().findById(ID);
    }

    @DisplayName("모두조회: input - Nothing | output - List<UserReportResponse>")
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

    @DisplayName("신고내역삭제: input - UserReportId | output - void")
    @Test
    void givenUserReportId_whenDeleting_thenReturnsSuccess() {
        //given
        willDoNothing().given(userReportRepository).deleteById(ID);

        //when
        sut.delete(ID);

        //then
        then(userReportRepository).should().deleteById(ID);
    }
}
