package com.example.plathome.real_estate.service;

import com.example.plathome.real_estate.domain.Estate;
import com.example.plathome.real_estate.dto.request.EstateForm;
import com.example.plathome.real_estate.dto.request.UpdateEstateForm;
import com.example.plathome.real_estate.dto.response.EstateResponse;
import com.example.plathome.real_estate.dto.response.MapInfoEstateResponse;
import com.example.plathome.real_estate.dto.response.SimpleEstateResponse;
import com.example.plathome.real_estate.exception.DuplicationEstateException;
import com.example.plathome.real_estate.exception.NotFoundEstateException;
import com.example.plathome.real_estate.repository.EstateRepository;
import com.example.plathome.requested_estate.domain.ThumbNail;
import com.example.plathome.requested_estate.repository.RequestedRepository;
import com.example.plathome.requested_estate.repository.ThumbNailRepository;
import com.example.plathome.member.domain.MemberSession;
import com.example.plathome.member.exception.ForbiddenMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    public List<SimpleEstateResponse> getAllSimpleInfoResponse() {
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
    public void update(MemberSession memberSession, UpdateEstateForm updateEstateForm) {
        Estate estate = estateRepository.findById(updateEstateForm.estateId()).orElseThrow(NotFoundEstateException::new);
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
