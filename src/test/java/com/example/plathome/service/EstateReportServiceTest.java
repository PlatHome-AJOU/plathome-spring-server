package com.example.plathome.service;


import com.example.plathome.ObjectBuilder;
import com.example.plathome.real_estate.exception.NotFoundEstateException;
import com.example.plathome.real_estate.repository.EstateRepository;
import com.example.plathome.estate_report.domain.EstateReport;
import com.example.plathome.estate_report.dto.request.EstateReportForm;
import com.example.plathome.estate_report.dto.response.EstateReportResponse;
import com.example.plathome.estate_report.exception.NotFoundEstateReportException;
import com.example.plathome.estate_report.exception.OwnEstateReportException;
import com.example.plathome.estate_report.repository.EstateReportRepository;
import com.example.plathome.estate_report.service.EstateReportService;
import com.example.plathome.member.domain.MemberSession;
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

@DisplayName("비지니스 로직 - 매물 신고")
@ExtendWith(MockitoExtension.class)
class EstateReportServiceTest extends ObjectBuilder {
    @InjectMocks private EstateReportService sut;
    @Mock private EstateReportRepository estateReportRepository;
    @Mock private EstateRepository estateRepository;

    @DisplayName("매물신고: 타인의 매물 신고 - 200")
    @Test
    void givenMemberSessionAndEstateReportForm_whenReportingEstate_thenReturnsSuccess(){
        //given
        MemberSession memberSession = createMemberSession(ID_2);
        EstateReportForm estateReportForm = createEstateReportForm();
        given(estateRepository.findById(estateReportForm.estateId())).willReturn(Optional.of(createEstate()));
        given(estateReportRepository.save(any(EstateReport.class))).willReturn(any(EstateReport.class));

        //when
        sut.report(memberSession, estateReportForm);

        //then
        then(estateRepository).should().findById(estateReportForm.estateId());
        then(estateReportRepository).should().save(any(EstateReport.class));
    }

    @DisplayName("매물신고: 존재하지 않는 매물일 경우 - 404")
    @Test
    void givenNonExistentEstateId_whenReportingEstate_thenThrowsNotFoundException(){
        //given
        MemberSession memberSession = createMemberSession(ID_1);
        EstateReportForm estateReportForm = createEstateReportForm();
        given(estateRepository.findById(estateReportForm.estateId())).willReturn(Optional.empty());

        //when & then
        assertThrows(NotFoundEstateException.class, () -> sut.report(memberSession, estateReportForm));
        then(estateRepository).should().findById(estateReportForm.estateId());
        then(estateReportRepository).shouldHaveNoInteractions();
    }

    @DisplayName("매물 신고: 본인의 매물 신고 - 403")
    @Test
    void givenOwnEstateId_whenReportingEstate_thenThrowsOwnEstateReportException(){
        //given
        MemberSession memberSession = createMemberSession(ID_1);
        EstateReportForm estateReportForm = createEstateReportForm();
        given(estateRepository.findById(estateReportForm.estateId())).willReturn(Optional.of(createEstate()));

        //when & then
        assertThrows(OwnEstateReportException.class, () -> sut.report(memberSession, estateReportForm));
        then(estateRepository).should().findById(estateReportForm.estateId());
        then(estateReportRepository).shouldHaveNoInteractions();
    }

    @DisplayName("매물 신고 단일 조회: 주어진 매물 신고 ID로 조회 시, 매물 신고 내역 응답 반환 - 200")
    @Test
    void givenEstateReportId_whenQuerying_thenReturnsEstateReportResponse(){
        //given
        EstateReport estateReport = createEstateReport();
        given(estateReportRepository.findById(ID_1)).willReturn(Optional.of(estateReport));

        //when
        EstateReportResponse estateReportResponse = sut.getById(ID_1);

        //then
        assertThat(estateReportResponse)
                .hasFieldOrPropertyWithValue("memberId", estateReport.getMemberId())
                .hasFieldOrPropertyWithValue("estateId", estateReport.getEstateId())
                .hasFieldOrPropertyWithValue("context", estateReport.getContext());
        then(estateReportRepository).should().findById(ID_1);
    }

    @DisplayName("매물 신고 단일 조회: 존재하지 않는 매물 신고 내역일 경우 - 404")
    @Test
    void givenNonExistentEstateReportId_whenQuerying_thenThrowsNotFoundException(){
        //given
        given(estateReportRepository.findById(ID_1)).willReturn(Optional.empty());

        //when & then
        assertThrows(NotFoundEstateReportException.class, () -> sut.getById(ID_1));
        then(estateReportRepository).should().findById(ID_1);
    }

    @DisplayName("매물 신고 전체 조회: 전체 조회 - 200")
    @Test
    void givenNothing_whenGettingAllEstateReports_thenReturnsEstateReportResponseList(){
        //given
        EstateReport estateReport = createEstateReport();
        given(estateReportRepository.findAll()).willReturn(List.of(estateReport));

        //when
        List<EstateReportResponse> estateReportResponseList = sut.getAll();

        //then
        assertThat(estateReportResponseList)
                .hasSize(1)
                .extracting(EstateReportResponse::estateId).containsExactly(ID_1);
        then(estateReportRepository).should().findAll();
    }

    @DisplayName("매물 신고 삭제: 매물 신고 ID로 삭제 - 204")
    @Test
    void givenEstateReportId_whenDeletingById_thenReturnsNoContent() {
        //given
        willDoNothing().given(estateReportRepository).deleteById(ID_1);

        //when
        sut.delete(ID_1);

        //then
        then(estateReportRepository).should().deleteById(ID_1);
    }
}
