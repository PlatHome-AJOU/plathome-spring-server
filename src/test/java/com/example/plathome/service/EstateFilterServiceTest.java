package com.example.plathome.service;


import com.example.plathome.ObjectBuilder;
import com.example.plathome.real_estate.domain.Estate;
import com.example.plathome.real_estate.dto.response.MapInfoEstateResponse;
import com.example.plathome.real_estate.dto.response.SimpleEstateResponse;
import com.example.plathome.real_estate.dto.search.Filter;
import com.example.plathome.real_estate.repository.EstateRepository;
import com.example.plathome.real_estate.service.EstateFilterService;
import com.example.plathome.requested_estate.repository.ThumbNailRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("비지니스 로직 - 필터 검색")
@ExtendWith(MockitoExtension.class)
class EstateFilterServiceTest extends ObjectBuilder {
    @InjectMocks private EstateFilterService sut;
    @Mock private EstateRepository estateRepository;
    @Mock private ThumbNailRepository thumbNailRepository;

    @DisplayName("지도 필터 검색: 매칭되는 매물 존재 - 200")
    @Test
    void givenFilter_whenFilteringInMap_thenReturnsMapInfoEstateResponseList(){
        //given
        Filter filter = createFilter();
        Estate estate = createEstate();
        given(estateRepository.filterSearch(filter)).willReturn(List.of(estate));

        //when
        List<MapInfoEstateResponse> mapInfoEstateResponseList = sut.mapFilter(filter);

        //then
        assertThat(mapInfoEstateResponseList)
                .hasSize(1)
                .extracting(MapInfoEstateResponse::memberId).containsExactly(estate.getMemberId());
        then(estateRepository).should().filterSearch(filter);
    }

    @DisplayName("지도 필터 검색: 매칭되는 매물 없음 - 200")
    @Test
    void givenFilter_whenNoMatchingEstate_thenReturnsEmptyList(){
        //given
        Filter filter = createFilter();
        given(estateRepository.filterSearch(filter)).willReturn(Collections.emptyList());

        //when
        List<MapInfoEstateResponse> mapInfoEstateResponseList = sut.mapFilter(filter);

        //then
        assertThat(mapInfoEstateResponseList)
                .isEmpty();
        then(estateRepository).should().filterSearch(filter);
    }

    @DisplayName("게시판 필터 검색: 매칭되는 매물 존재 - 200")
    @Test
    void givenFilter_whenFilteringInBoard_thenReturnsSimpleEstateResponseList(){
        //given
        Filter filter = createFilter();
        Estate estate = createEstate();
        given(estateRepository.filterSearch(filter)).willReturn(List.of(estate));

        //when
        List<SimpleEstateResponse> simpleEstateResponseList = sut.boardFilter(filter);

        //then
        assertThat(simpleEstateResponseList)
                .hasSize(1)
                .extracting(SimpleEstateResponse::memberId).containsExactly(estate.getMemberId());
        then(estateRepository).should().filterSearch(filter);
    }

    @DisplayName("게시판 필터 검색: 매칭되는 매물 없음 - 200")
    @Test
    void givenFilter_whenNoMatchingEstateInBoard_thenReturnsEmptyList(){
        //given
        Filter filter = createFilter();
        given(estateRepository.filterSearch(filter)).willReturn(Collections.emptyList());

        //when
        List<SimpleEstateResponse> simpleEstateResponseList = sut.boardFilter(filter);

        //then
        assertThat(simpleEstateResponseList)
                .isEmpty();
        then(estateRepository).should().filterSearch(filter);
    }
}
