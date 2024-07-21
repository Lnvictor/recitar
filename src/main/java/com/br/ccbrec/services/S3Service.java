package com.br.ccbrec.services;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.Calendar;

@Service
@RequiredArgsConstructor
public class S3Service {
    @Value("${aws.bucketName}")
    private String bucketName;
    private final AmazonS3 s3Client;

    public String getPresignedUrlByKey(String key) {
        Calendar expiresAt = Calendar.getInstance();
        expiresAt.add(Calendar.SECOND, 604800);

        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, key)
                .withMethod(HttpMethod.GET)
                .withExpiration(expiresAt.getTime());
        URL url = s3Client.generatePresignedUrl(request);
        return url.toString();
    }

    public String uploadFile(MultipartFile mFile, String filename) {
        String originalFilename = mFile.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.indexOf("."));
        filename = filename.concat(extension);

        File file = new File(mFile.getOriginalFilename());

        try (InputStream inputStream = mFile.getInputStream()) {
            Files.copy(inputStream, file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filename, file);
        s3Client.putObject(putObjectRequest);

        String preSignedUrl = getPresignedUrlByKey(filename);
        file.delete();
        return preSignedUrl;
    }
}
