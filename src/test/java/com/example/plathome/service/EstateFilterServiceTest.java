package com.example.plathome.service;


import com.example.plathome.ObjectBuilder;
import com.example.plathome.estate.real.domain.Estate;
import com.example.plathome.estate.real.dto.response.MapInfoEstateResponse;
import com.example.plathome.estate.real.dto.response.SimpleEstateResponse;
import com.example.plathome.estate.real.dto.search.Filter;
import com.example.plathome.estate.real.repository.EstateRepository;
import com.example.plathome.estate.real.service.EstateFilterService;
import com.example.plathome.estate.requested.repository.ThumbNailRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @DisplayName("지도 필터검색: input - Filter | output - List<MapInfoEstateResponse>")
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

    @DisplayName("게시판 필터검색: input - Filter | output - List<SimpleEstateResponse>")
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
}
