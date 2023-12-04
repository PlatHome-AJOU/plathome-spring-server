package com.example.plathome.service;


import com.example.plathome.estate.requested.repository.RequestedRepository;
import com.example.plathome.estate.requested.repository.ThumbNailRepository;
import com.example.plathome.estate.requested.service.RequestedService;
import com.example.plathome.global.service.S3Service;
import com.example.plathome.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("비니지스 로직 - 등록 요청 매물")
@ExtendWith(MockitoExtension.class)
class RequestedServiceTest {
    @InjectMocks private RequestedService sut;
    @Mock private S3Service s3Service;
    @Mock private RequestedRepository requestedRepository;
    @Mock private ThumbNailRepository thumbNailRepository;
    @Mock private MemberRepository memberRepository;

}