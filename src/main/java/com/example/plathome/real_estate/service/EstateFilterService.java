package com.example.plathome.real_estate.service;

import com.example.plathome.real_estate.dto.response.MapInfoEstateResponse;
import com.example.plathome.real_estate.dto.response.SimpleEstateResponse;
import com.example.plathome.real_estate.dto.search.Filter;
import com.example.plathome.real_estate.repository.EstateRepository;
import com.example.plathome.requested_estate.repository.ThumbNailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class EstateFilterService {
    private final EstateRepository estateRepository;
    private final ThumbNailRepository thumbNailRepository;

    public List<MapInfoEstateResponse> mapFilter(Filter filter) {
        return estateRepository.filterSearch(filter).stream()
                .map(MapInfoEstateResponse::from)
                .toList();
    }

    public List<SimpleEstateResponse> boardFilter(Filter filter) {
        return estateRepository.filterSearch(filter).stream()
                .map(SimpleEstateResponse::from)
                .toList();
    }
}
