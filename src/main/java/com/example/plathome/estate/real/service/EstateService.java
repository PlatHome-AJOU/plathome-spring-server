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
import com.example.plathome.estate.requested.repository.RequestedRepository;
import com.example.plathome.member.domain.MemberSession;
import com.example.plathome.member.exception.ForbiddenMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class EstateService {
    private final EstateRepository estateRepository;
    private final RequestedRepository requestedRepository;

    @Transactional
    public void register(EstateForm estateForm) {
        this.validDupEstate(estateForm.userId());
        estateRepository.save(estateForm.toEntity());
        requestedRepository.deleteByUserId(estateForm.userId());
    }

    private void validDupEstate(String userId) {
        Optional<Estate> optionalEstate = estateRepository.findByUserId(userId);
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

    public EstateResponse getDetail(Long estateId) {
        return estateRepository.findById(estateId)
                .map(EstateResponse::from)
                .orElseThrow(NotFoundEstateException::new);
    }

    @Transactional
    public void delete(MemberSession memberSession, Long estateId) {
        Estate estate = estateRepository.findById(estateId).orElseThrow(NotFoundEstateException::new);
        this.isValidUser(memberSession.userId(), estate.getUserId());
        estateRepository.deleteById(estateId);
    }

    @Transactional
    public void update(MemberSession memberSession, Long estateId, UpdateEstateForm updateEstateForm) {
        Estate estate = estateRepository.findById(estateId).orElseThrow(NotFoundEstateException::new);
        this.isValidUser(memberSession.userId(), estate.getUserId());
        estate.update(
                updateEstateForm.contractTerm(),
                updateEstateForm.option(),
                updateEstateForm.squareFeet(),
                updateEstateForm.deposit(),
                updateEstateForm.maintenanceFee(),
                updateEstateForm.monthlyRent()
        );
    }

    private void isValidUser(String userId, String expectedUserId) {
        if (!userId.equals(expectedUserId)) {
            throw new ForbiddenMemberException();
        }
    }
}
