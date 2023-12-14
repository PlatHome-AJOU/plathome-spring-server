package com.example.plathome.requested_estate.service;

import com.example.plathome.requested_estate.domain.Requested;
import com.example.plathome.requested_estate.domain.ThumbNail;
import com.example.plathome.requested_estate.dto.request.RequestedForm;
import com.example.plathome.requested_estate.dto.response.RequestedResponse;
import com.example.plathome.requested_estate.exception.DuplicationRequestedException;
import com.example.plathome.requested_estate.exception.NotFoundRequestedException;
import com.example.plathome.requested_estate.repository.RequestedRepository;
import com.example.plathome.requested_estate.repository.ThumbNailRepository;
import com.example.plathome.global.service.S3Service;
import com.example.plathome.member.domain.Member;
import com.example.plathome.member.domain.MemberSession;
import com.example.plathome.member.exception.NotFoundMemberException;
import com.example.plathome.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.plathome.global.constant.RequestedStaticField.CONTRACT_FOLDER;
import static com.example.plathome.global.constant.RequestedStaticField.ROOM_FOLDER;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RequestedService {

    private final S3Service s3Service;
    private final RequestedRepository requestedRepository;
    private final ThumbNailRepository thumbNailRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void saveContract(MemberSession memberSession, MultipartFile file) {
        this.validDupReq(memberSession.id());
        s3Service.uploadContract(memberSession, file, CONTRACT_FOLDER);
    }

    @Transactional
    public void saveForm(MemberSession memberSession, RequestedForm requestedForm) {
        this.validDupReq(memberSession.id());
        String url = s3Service.getContractUrl(memberSession.email(), CONTRACT_FOLDER);

        List<String> thumbNailUrls = s3Service.getThumbNailUrls(ROOM_FOLDER + memberSession.email());
        thumbNailUrls.forEach(s -> {
            ThumbNail thumbNail = ThumbNail.builder()
                    .memberId(memberSession.id())
                    .url(s).build();
            thumbNailRepository.save(thumbNail);
        });

        Requested requested = requestedForm.toEntity(memberSession.id(), url);
        requestedRepository.save(requested);
    }

    private void validDupReq(long memberId) {
        Optional<Requested> optionalRequested = requestedRepository.findByMemberId(memberId);
        if (optionalRequested.isPresent()) {
            throw new DuplicationRequestedException();
        }
    }

    public RequestedResponse getOne(Long requestedId) {
        Requested requested = requestedRepository.findById(requestedId).orElseThrow(NotFoundRequestedException::new);
        Set<String> thumbNailList = thumbNailRepository.findByMemberId(requested.getMemberId()).stream()
                .map(ThumbNail::getUrl)
                .collect(Collectors.toUnmodifiableSet());
        return RequestedResponse.from(requested, thumbNailList);
    }

    public List<RequestedResponse> getAll() {
        List<Requested> requestedList = requestedRepository.findAll();
        List<Long> memberIdList = requestedList.stream().map(Requested::getMemberId).toList();
        List<ThumbNail> thumbNailList = thumbNailRepository.findByMemberIdIn(memberIdList);

        Map<Long, Set<String>> thumNailUrlMap = thumbNailList.stream()
                .collect(Collectors.groupingBy(ThumbNail::getMemberId,
                                Collectors.mapping(ThumbNail::getUrl,
                                        Collectors.toSet())));

        return requestedList.stream().map(requested -> RequestedResponse.from(requested, thumNailUrlMap.get(requested.getMemberId()))).toList();
    }

    @Transactional
    public void updateContract(MemberSession memberSession, MultipartFile file) {
        s3Service.uploadContract(memberSession, file, CONTRACT_FOLDER);
    }

    @Transactional
    public void updateForm(MemberSession memberSession, RequestedForm requestedForm) {
        Requested requested = requestedRepository.findByMemberId(memberSession.id())
                .orElseThrow(NotFoundRequestedException::new);

        requested.updateForm(
                requestedForm.location(),
                requestedForm.context(),
                requestedForm.roomType(),
                requestedForm.rentalType(),
                requestedForm.floor(),
                requestedForm.contractTerm(),
                requestedForm.option(),
                requestedForm.squareFeet(),
                requestedForm.deposit(),
                requestedForm.maintenanceFee(),
                requestedForm.monthlyRent());
    }

    @Transactional
    public void delete(long memberId) {
        String email = memberRepository.findById(memberId)
                .map(Member::getEmail)
                .orElseThrow(NotFoundMemberException::new);
        requestedRepository.deleteByMemberId(memberId);
        thumbNailRepository.deleteByMemberId(memberId);
        s3Service.deleteFolder(ROOM_FOLDER + email);
    }
}
