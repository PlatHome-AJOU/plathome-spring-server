package com.example.plathome.requested_estate.service;

import com.example.plathome.requested_estate.repository.ThumbNailRepository;
import com.example.plathome.global.service.S3Service;
import com.example.plathome.member.domain.MemberSession;
import com.example.plathome.global.constant.RequestedStaticField;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ThumbNailService {
    private final ThumbNailRepository thumbNailRepository;
    private final S3Service s3Service;

    @Transactional
    public LocalDateTime saveThumbNail(MemberSession memberSession, MultipartFile file) {
        return s3Service.uploadThumbNail(memberSession, file, RequestedStaticField.ROOM_FOLDER);
    }

    @Transactional
    public void deleteThumbNail(MemberSession memberSession, LocalDateTime datetime) {
        s3Service.deleteThumbNail(memberSession, datetime, RequestedStaticField.ROOM_FOLDER);
    }
}
