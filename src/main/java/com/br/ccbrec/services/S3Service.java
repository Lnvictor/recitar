package com.br.ccbrec.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class S3Service {
    @Value("${aws.bucketName}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    public List<String> listBucketFiles(){
        ObjectListing s = s3Client.listObjects(bucketName);
        List<String> fileNames = new ArrayList<String>();

        for (S3ObjectSummary q : s.getObjectSummaries()) {
            fileNames.add(q.getKey());
        }

        return fileNames;
    }
}
