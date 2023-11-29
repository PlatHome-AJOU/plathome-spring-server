package com.example.plathome.estate.real.service;

import com.example.plathome.estate.real.domain.Estate;
import com.example.plathome.estate.real.dto.response.MapInfoEstateResponse;
import com.example.plathome.estate.real.dto.response.SimpleEstateResponse;
import com.example.plathome.estate.real.dto.search.Filter;
import com.example.plathome.estate.real.repository.EstateRepository;
import com.example.plathome.estate.requested.domain.ThumbNail;
import com.example.plathome.estate.requested.repository.ThumbNailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
