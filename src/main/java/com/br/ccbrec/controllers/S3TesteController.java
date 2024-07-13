package com.br.ccbrec.controllers;

import com.br.ccbrec.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class S3TesteController {

    @Autowired
    private S3Service s3Service;

    @GetMapping("/teste")
    public List<String> get(){
        return this.s3Service.listBucketFiles();
    }
}
