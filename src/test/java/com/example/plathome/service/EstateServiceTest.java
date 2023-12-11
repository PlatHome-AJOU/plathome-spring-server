package com.example.plathome.service;


import com.example.plathome.ObjectBuilder;
import com.example.plathome.real_estate.domain.Estate;
import com.example.plathome.real_estate.dto.request.EstateForm;
import com.example.plathome.real_estate.dto.request.UpdateEstateForm;
import com.example.plathome.real_estate.dto.response.EstateResponse;
import com.example.plathome.real_estate.dto.response.MapInfoEstateResponse;
import com.example.plathome.real_estate.dto.response.SimpleEstateResponse;
import com.example.plathome.real_estate.exception.DuplicationEstateException;
import com.example.plathome.real_estate.exception.NotFoundEstateException;
import com.example.plathome.real_estate.repository.EstateRepository;
import com.example.plathome.real_estate.service.EstateService;
import com.example.plathome.requested_estate.repository.RequestedRepository;
import com.example.plathome.requested_estate.repository.ThumbNailRepository;
import com.example.plathome.member.domain.MemberSession;
import com.example.plathome.member.exception.ForbiddenMemberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("비니지스 로직 - 실제 매물")
@ExtendWith(MockitoExtension.class)
class EstateServiceTest extends ObjectBuilder {
    @InjectMocks private EstateService sut;
    @Mock private EstateRepository estateRepository;
    @Mock private RequestedRepository requestedRepository;
    @Mock private ThumbNailRepository thumbNailRepository;

    @DisplayName("매물 등록: 관리자가 매물 등록을 승인할 경우 - 200")
    @Test
    void givenEstateForm_whenRegistering_thenReturnsSuccess() {
        //given
        EstateForm estateForm = createEstateForm();
        given(estateRepository.findByMemberId(estateForm.memberId())).willReturn(Optional.empty());
        given(estateRepository.save(any(Estate.class))).willReturn(createEstate());
        willDoNothing().given(requestedRepository).deleteByMemberId(estateForm.memberId());

        //when
        sut.register(estateForm);

        //then
        then(estateRepository).should().findByMemberId(estateForm.memberId());
        then(estateRepository).should().save(any(Estate.class));
        then(requestedRepository).should().deleteByMemberId(estateForm.memberId());
    }

    @DisplayName("매물 등록: 이미 매물을 올린 회원일 경우 - 400")
    @Test
    void givenDuplicationMemberId_whenRegistering_thenReturnsDuplicationException() {
        //given
        EstateForm estateForm = createEstateForm();
        Estate estate = createEstate();
        given(estateRepository.findByMemberId(estateForm.memberId())).willReturn(Optional.of(estate));

        //when & then
        assertThrows(DuplicationEstateException.class, () -> sut.register(estateForm));
        then(estateRepository).should().findByMemberId(estateForm.memberId());
        then(estateRepository).shouldHaveNoMoreInteractions();
        then(requestedRepository).shouldHaveNoInteractions();
    }

    @DisplayName("지도 조회: 매물이 존재하는 경우 - 200")
    @Test
    void givenNothing_whenGettingAllMapInfo_thenReturnsMapInfoEstateResponseList() {
        //given
        Estate estate = createEstate();
        given(estateRepository.findAll()).willReturn(List.of(estate));

        //when
        List<MapInfoEstateResponse> mapInfoEstateResponseList = sut.getAllMapInfoResponse();

        //then
        assertThat(mapInfoEstateResponseList)
                .hasSize(1)
                .extracting("memberId").containsExactly(estate.getMemberId());
        then(estateRepository).should().findAll();
    }

    @DisplayName("게시판 조회: 매물이 존재하는 경우 - 200")
    @Test
    void givenNothing_whenGettingAllSimpleInfo_thenReturnsSimpleEstateResponseList() {
        //given
        Estate estate = createEstate();
        given(estateRepository.findAll()).willReturn(List.of(estate));

        //when
        List<SimpleEstateResponse> simpleEstateResponseList = sut.getAllSimpleInfoResponse();

        //then
        assertThat(simpleEstateResponseList)
                .hasSize(1)
                .extracting("memberId").containsExactly(estate.getMemberId());
        then(estateRepository).should().findAll();
    }

    @DisplayName("매물 상세 조회: 매물ID로 조회할 경우 - 200")
    @Test
    void givenEstateId_whenGettingDetail_thenReturnsEstateResponse() {
        //given
        long estateId = ID_1;
        Estate estate = createEstate();
        given(estateRepository.findById(estateId)).willReturn(Optional.of(estate));
        given(thumbNailRepository.findByMemberId(estate.getMemberId())).willReturn(List.of(createThumbNail()));

        //when
        EstateResponse estateResponse = sut.getDetail(estateId);

        //then
        assertThat(estateResponse)
                .hasFieldOrPropertyWithValue("memberId", estateResponse.memberId())
                .hasFieldOrPropertyWithValue("thumbNailUrls", Set.of(THUMBNAIL_URL));
        then(estateRepository).should().findById(estateId);
        then(thumbNailRepository).should().findByMemberId(estate.getMemberId());
    }

    @DisplayName("매물 상세 조회: 매물ID에 매칭되는 매물이 존재하지 않을 경우 - 404")
    @Test
    void givenNonExistentEstateId_whenGettingDetail_thenReturnsNotFoundException() {
        //given
        long estateId = ID_1;
        given(estateRepository.findById(estateId)).willReturn(Optional.empty());

        //when & then
        assertThrows(NotFoundEstateException.class, () -> sut.getDetail(estateId));
        then(estateRepository).should().findById(estateId);
        then(thumbNailRepository).shouldHaveNoInteractions();
    }

    @DisplayName("매물 삭제: 본인의 매물을 삭제 요청을 할 경우 - 200")
    @Test
    void givenMemberSessionAndEstateId_whenDeletingEstate_thenReturnsSuccess() {
        //given
        MemberSession memberSession = createMemberSession(ID_1);
        given(estateRepository.findById(ID_1)).willReturn(Optional.of(createEstate()));
        willDoNothing().given(estateRepository).deleteById(ID_1);

        //when
        sut.delete(memberSession, ID_1);

        //then
        then(estateRepository).should().findById(ID_1);
        then(estateRepository).should().deleteById(ID_1);
    }

    @DisplayName("매물 삭제: 매물ID에 매칭되는 매물이 존재하지 않는 경우 - 404 ")
    @Test
    void givenNonExistentEstateId_whenDeletingEstate_thenReturnsNotFoundException() {
        //given
        MemberSession memberSession = createMemberSession(ID_1);
        given(estateRepository.findById(ID_1)).willReturn(Optional.empty());

        //when & then
        assertThrows(NotFoundEstateException.class, () -> sut.delete(memberSession, ID_1));
        then(estateRepository).should().findById(ID_1);
        then(estateRepository).shouldHaveNoMoreInteractions();
    }

    @DisplayName("매물 삭제: 타인의 매물을 삭제하려는 경우 - 403")
    @Test
    void givenForbiddenMemberId_whenDeletingEstate_thenReturnsForbiddenException() {
        //given
        MemberSession memberSession = createMemberSession(ID_2);
        given(estateRepository.findById(ID_1)).willReturn(Optional.of(createEstate()));

        //when & then
        assertThrows(ForbiddenMemberException.class, () -> sut.delete(memberSession, ID_1));
        then(estateRepository).should().findById(ID_1);
        then(estateRepository).shouldHaveNoMoreInteractions();
    }

    @DisplayName("매물 수정: 본인의 매물을 수정하려는 경우 - 200")
    @Test
    void givenMemberSessionAndUpdateEstateForm_whenUpdatingEstate_thenReturnsSuccess() {
        //given
        MemberSession memberSession = createMemberSession(ID_1);
        UpdateEstateForm updateEstateForm = createUpdateEstateForm();
        given(estateRepository.findById(ID_1)).willReturn(Optional.of(createEstate()));

        //when
        sut.update(memberSession, updateEstateForm);

        //then
        then(estateRepository).should().findById(ID_1);
    }

    @DisplayName("매물 수정: 매물ID에 매핑되는 매물이 존재하지 않을 경우 - 404")
    @Test
    void givenNonExistentEstateId_whenUpdatingEstate_thenReturnsNotFoundException() {
        //given
        MemberSession memberSession = createMemberSession(ID_1);
        UpdateEstateForm updateEstateForm = createUpdateEstateForm();
        given(estateRepository.findById(ID_1)).willReturn(Optional.empty());

        //when & then
        assertThrows(NotFoundEstateException.class, () -> sut.update(memberSession, updateEstateForm));
        then(estateRepository).should().findById(ID_1);
    }

    @DisplayName("매물 수정: 타인의 매물을 수정하려는 경우 - 403")
    @Test
    void givenForbiddenMemberId_whenUpdatingEstate_thenReturnsForbiddenException() {
        //given
        MemberSession memberSession = createMemberSession(ID_2);
        UpdateEstateForm updateEstateForm = createUpdateEstateForm();
        given(estateRepository.findById(ID_1)).willReturn(Optional.of(createEstate()));

        //when & then
        assertThrows(ForbiddenMemberException.class, () -> sut.update(memberSession, updateEstateForm));
        then(estateRepository).should().findById(ID_1);
    }
}
