package com.example.plathome.real_estate.repository;

import com.example.plathome.real_estate.domain.Estate;
import com.example.plathome.real_estate.dto.search.Filter;

import java.util.List;

public interface EstateRepositoryCustom {
    List<Estate> filterSearch(Filter filter);
}
