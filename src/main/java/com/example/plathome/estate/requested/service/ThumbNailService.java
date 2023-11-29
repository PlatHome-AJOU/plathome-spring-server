package com.example.plathome.estate.requested.service;

import com.example.plathome.estate.requested.repository.ThumbNailRepository;
import com.example.plathome.global.service.S3Service;
import com.example.plathome.member.domain.MemberSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

import static com.example.plathome.estate.requested.common.ThumbNailStaticField.ROOM_FOLDER;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ThumbNailService {
    private final ThumbNailRepository thumbNailRepository;
    private final S3Service s3Service;

    @Transactional
    public LocalDateTime saveThumbNail(MemberSession memberSession, MultipartFile file) {
        return s3Service.uploadThumbNail(memberSession, file, ROOM_FOLDER);
    }

    @Transactional
    public void deleteThumbNail(MemberSession memberSession, LocalDateTime datetime) {
        s3Service.deleteThumbNail(memberSession, datetime, ROOM_FOLDER);
    }
}
