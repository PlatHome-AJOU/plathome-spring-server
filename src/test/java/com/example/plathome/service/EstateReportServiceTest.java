package com.example.plathome.service;


import com.example.plathome.ObjectBuilder;
import com.example.plathome.real_estate.exception.NotFoundEstateException;
import com.example.plathome.real_estate.repository.EstateRepository;
import com.example.plathome.estate_report.domain.EstateReport;
import com.example.plathome.estate_report.dto.request.EstateReportForm;
import com.example.plathome.estate_report.dto.response.EstateReportResponse;
import com.example.plathome.estate_report.exception.NotFoundEstateReportException;
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

    @DisplayName("매물신고: input - MemberSession & EstateReportForm | output - void")
    @Test
    void givenMemberSessionAndEstateReportForm_whenReportingEstate_thenReturnsSuccess(){
        //given
        MemberSession memberSession = createMemberSession(ID);
        EstateReportForm estateReportForm = createEstateReportForm();
        given(estateRepository.findById(estateReportForm.estateId())).willReturn(Optional.of(createEstate()));
        given(estateReportRepository.save(any(EstateReport.class))).willReturn(any(EstateReport.class));

        //when
        sut.report(memberSession, estateReportForm);

        //then
        then(estateRepository).should().findById(estateReportForm.estateId());
        then(estateReportRepository).should().save(any(EstateReport.class));
    }

    @DisplayName("매물신고: input - MemberSession & Wrong EstateReportForm | output - 404")
    @Test
    void givenMemberSessionAndWrongEstateReportForm_whenReportingEstate_thenReturnsNotFoundException(){
        //given
        MemberSession memberSession = createMemberSession(ID);
        EstateReportForm estateReportForm = createEstateReportForm();
        given(estateRepository.findById(estateReportForm.estateId())).willReturn(Optional.empty());

        //when & then
        assertThrows(NotFoundEstateException.class, () -> sut.report(memberSession, estateReportForm));
        then(estateRepository).should().findById(estateReportForm.estateId());
        then(estateReportRepository).shouldHaveNoInteractions();
    }

    @DisplayName("단일조회: input - EstateReportId | output - EstateReportResponse")
    @Test
    void givenEstateReportId_whenGettingById_thenReturnsEstateReportResponse(){
        //given
        EstateReport estateReport = createEstateReport();
        given(estateReportRepository.findById(ID)).willReturn(Optional.of(estateReport));

        //when
        EstateReportResponse estateReportResponse = sut.getById(ID);

        //then
        assertThat(estateReportResponse)
                .hasFieldOrPropertyWithValue("memberId", estateReport.getMemberId())
                .hasFieldOrPropertyWithValue("estateId", estateReport.getEstateId())
                .hasFieldOrPropertyWithValue("context", estateReport.getContext());
        then(estateReportRepository).should().findById(ID);
    }

    @DisplayName("단일조회: input - Wrong EstateReportId | output - 404")
    @Test
    void givenWrongEstateReportId_whenGettingById_thenReturnsNotFoundException(){
        //given
        given(estateReportRepository.findById(ID)).willReturn(Optional.empty());

        //when & then
        assertThrows(NotFoundEstateReportException.class, () -> sut.getById(ID));
        then(estateReportRepository).should().findById(ID);
    }

    @DisplayName("모두조회: input - Nothing | output - List<EstateReportResponse>")
    @Test
    void givenNothing_whenGettingAll_thenReturnsEstateReportResponseList(){
        //given
        EstateReport estateReport = createEstateReport();

        given(estateReportRepository.findAll()).willReturn(List.of(estateReport));

        //when
        List<EstateReportResponse> estateReportResponseList = sut.getAll();

        //then
        assertThat(estateReportResponseList)
                .hasSize(1)
                .extracting(EstateReportResponse::estateId).containsExactly(ID);
        then(estateReportRepository).should().findAll();
    }

    @DisplayName("신고내역삭제: input - EstateReportId | output - void")
    @Test
    void givenEstateReportId_whenDeleting_thenReturnsSuccess() {
        //given
        willDoNothing().given(estateReportRepository).deleteById(ID);

        //when
        sut.delete(ID);

        //then
        then(estateReportRepository).should().deleteById(ID);
    }
}
