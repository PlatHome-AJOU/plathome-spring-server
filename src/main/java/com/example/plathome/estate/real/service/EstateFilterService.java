package com.example.plathome.estate.real.service;

import com.example.plathome.estate.real.dto.response.MapInfoEstateResponse;
import com.example.plathome.estate.real.dto.response.SimpleEstateResponse;
import com.example.plathome.estate.real.dto.search.Filter;
import com.example.plathome.estate.real.repository.EstateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class EstateFilterService {
    private final EstateRepository estateRepository;

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
