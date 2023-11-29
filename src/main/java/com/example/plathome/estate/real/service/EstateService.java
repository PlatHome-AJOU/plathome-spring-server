package com.example.plathome.estate.real.service;

import com.example.plathome.estate.real.domain.Estate;
import com.example.plathome.estate.real.dto.request.EstateForm;
import com.example.plathome.estate.real.dto.request.UpdateEstateForm;
import com.example.plathome.estate.real.dto.response.EstateResponse;
import com.example.plathome.estate.real.dto.response.MapInfoEstateResponse;
import com.example.plathome.estate.real.dto.response.SimpleEstateResponse;
import com.example.plathome.estate.real.exception.DuplicationEstateException;
import com.example.plathome.estate.real.exception.NotFoundEstateException;
import com.example.plathome.estate.real.repository.EstateRepository;
import com.example.plathome.estate.requested.domain.ThumbNail;
import com.example.plathome.estate.requested.repository.RequestedRepository;
import com.example.plathome.estate.requested.repository.ThumbNailRepository;
import com.example.plathome.member.domain.MemberSession;
import com.example.plathome.member.exception.ForbiddenMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class EstateService {
    private final EstateRepository estateRepository;
    private final RequestedRepository requestedRepository;
    private final ThumbNailRepository thumbNailRepository;

    @Transactional
    public void register(EstateForm estateForm) {
        this.validDupEstate(estateForm.memberId());
        estateRepository.save(estateForm.toEntity());
        requestedRepository.deleteByMemberId(estateForm.memberId());
    }

    private void validDupEstate(long memberId) {
        Optional<Estate> optionalEstate = estateRepository.findByMemberId(memberId);
        if (optionalEstate.isPresent()) {
            throw new DuplicationEstateException();
        }
    }

    public List<MapInfoEstateResponse> getAllMapInfoResponse() {
        return estateRepository.findAll().stream()
                .map(MapInfoEstateResponse::from)
                .toList();
    }

    public List<SimpleEstateResponse> getAllSimpleResponse() {
        return estateRepository.findAll().stream()
                .map(SimpleEstateResponse::from)
                .toList();
    }

    public EstateResponse getDetail(long estateId) {
        Estate estate = estateRepository.findById(estateId).orElseThrow(NotFoundEstateException::new);
        Set<String> thumbNailUrls = thumbNailRepository.findByMemberId(estate.getMemberId()).stream()
                .map(ThumbNail::getUrl)
                .collect(Collectors.toUnmodifiableSet());
        return EstateResponse.from(estate, thumbNailUrls);
    }

    @Transactional
    public void delete(MemberSession memberSession, long estateId) {
        Estate estate = estateRepository.findById(estateId).orElseThrow(NotFoundEstateException::new);
        this.isValidUser(memberSession.id(), estate.getMemberId());
        estateRepository.deleteById(estateId);
    }

    @Transactional
    public void update(MemberSession memberSession, long estateId, UpdateEstateForm updateEstateForm) {
        Estate estate = estateRepository.findById(estateId).orElseThrow(NotFoundEstateException::new);
        this.isValidUser(memberSession.id(), estate.getMemberId());
        estate.update(
                updateEstateForm.context(),
                updateEstateForm.contractTerm(),
                updateEstateForm.option(),
                updateEstateForm.squareFeet(),
                updateEstateForm.deposit(),
                updateEstateForm.maintenanceFee(),
                updateEstateForm.monthlyRent()
        );
    }

    private void isValidUser(long memberId, long exceptedMemberId) {
        if (memberId != exceptedMemberId) {
            throw new ForbiddenMemberException();
        }
    }
}
