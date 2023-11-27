package com.example.plathome.estate.real.repository;

import com.example.plathome.estate.real.domain.Estate;
import com.example.plathome.estate.real.dto.search.Filter;

import java.util.List;

public interface EstateRepositoryCustom {
    List<Estate> filterSearch(Filter filter);
}
