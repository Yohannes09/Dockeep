package com.dockeep.demo.document.service;

import com.dockeep.demo.document.request.DocumentUploadRequest;
import com.dockeep.demo.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocService {
    private final S3Service s3Service;


    public void uploadFile(DocumentUploadRequest uploadRequest){

    }
}
