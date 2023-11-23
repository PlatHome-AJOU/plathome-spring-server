package com.example.plathome.estate.requested.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.plathome.member.domain.MemberSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.example.plathome.estate.requested.common.RequestedStaticField.CONTRACT_FOLDER;

@RequiredArgsConstructor
@Service
public class S3Service {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public void upload(MemberSession memberSession, MultipartFile file) {
        try {
            ObjectMetadata metadata= new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            amazonS3Client.putObject(bucket, CONTRACT_FOLDER + memberSession.userId(),file.getInputStream(),metadata);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFile(String userId) {
        return amazonS3Client.getResourceUrl(bucket, CONTRACT_FOLDER + userId);
    }

    public void deleteFile(String userId) {
        amazonS3Client.deleteObject(bucket, CONTRACT_FOLDER + userId);
    }
}
