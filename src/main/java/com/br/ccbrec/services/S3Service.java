package com.br.ccbrec.services;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class S3Service {
    @Value("${aws.bucketName}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    public List<String> listBucketFiles() {
        ObjectListing s = s3Client.listObjects(bucketName);
        List<String> fileNames = new ArrayList<>();

        for (S3ObjectSummary q : s.getObjectSummaries()) {
            GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, q.getKey())
                    .withMethod(HttpMethod.GET);
            URL url = s3Client.generatePresignedUrl(request);
            fileNames.add(q.getKey());
        }

        return fileNames;
    }

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
