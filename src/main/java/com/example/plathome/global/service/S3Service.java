package com.example.plathome.global.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.example.plathome.member.domain.MemberSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class S3Service {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public void uploadContract(MemberSession memberSession, MultipartFile file, String folder) {
        try {
            ObjectMetadata metadata= new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            amazonS3Client.putObject(bucket, folder + memberSession.email(),file.getInputStream(),metadata);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LocalDateTime uploadThumbNail(MemberSession memberSession, MultipartFile file, String folder) {
        LocalDateTime now = LocalDateTime.now();
        try {
            ObjectMetadata metadata= new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            amazonS3Client.putObject(bucket, folder + memberSession.email() + "/" + now.toString(),file.getInputStream(),metadata);
            return now;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return now;
    }

    public String getContractUrl(String email, String folder) {
        return amazonS3Client.getResourceUrl(bucket, folder + email);
    }

    public void deleteFile(String email, String folder) {
        amazonS3Client.deleteObject(bucket, folder + email);
    }

    public void deleteThumbNail(MemberSession memberSession, LocalDateTime dateTime, String folder) {
        amazonS3Client.deleteObject(bucket, folder + memberSession.email() + "/" + dateTime.toString());
    }

    public List<String> getThumbNailUrls(String folderName) {
        ListObjectsV2Request request = new ListObjectsV2Request().withBucketName(bucket).withPrefix(folderName + "/");
        ListObjectsV2Result result = amazonS3Client.listObjectsV2(request);

        return result.getObjectSummaries().stream()
                .map(s3ObjectSummary -> amazonS3Client.getUrl(bucket, s3ObjectSummary.getKey()).toString())
                .collect(Collectors.toList());
    }

    public void deleteFolder(String folder) {
        ObjectListing objectListing = amazonS3Client.listObjects(bucket, folder + "/");
        while (true) {
            for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                amazonS3Client.deleteObject(bucket, objectSummary.getKey());
            }

            // 더 이상 처리할 객체가 없으면 종료
            if (objectListing.isTruncated()) {
                objectListing = amazonS3Client.listNextBatchOfObjects(objectListing);
            } else {
                break;
            }
        }
    }
}
