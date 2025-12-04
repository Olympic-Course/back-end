package com.org.olympiccourse.global.s3.service;

import com.org.olympiccourse.global.s3.dto.PresignRequestDto;
import com.org.olympiccourse.global.s3.dto.PresignedUrlResponse;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;

@Service
@RequiredArgsConstructor
public class S3PresignService {

    private final S3Presigner s3Presigner;
    private final Clock clock;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public PresignedUrlResponse createUploadUrl(PresignRequestDto request) {

        String fileName = createFileName(request.getExt());
        PutObjectRequest put = PutObjectRequest.builder()
            .bucket(bucket).key(fileName)
            .build();

        PresignedPutObjectRequest signed = s3Presigner.presignPutObject(
            b -> b.signatureDuration(Duration.ofMinutes(5)).putObjectRequest(put));

        return new PresignedUrlResponse(signed.url().toString(), fileName);
    }

    private String createFileName(String ext){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = LocalDateTime.now(clock).format(formatter);
        return timestamp + "_" + UUID.randomUUID() +  "." + ext;
    }
}
