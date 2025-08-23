package com.dockeep.document.service;

import com.dockeep.document.entity.Document;
import com.dockeep.document.request.DocumentUploadRequest;
import com.dockeep.s3.S3Service;
import com.dockeep.user.User;
import com.dockeep.user.UserPrincipal;
import com.dockeep.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocService {
    private final S3Service s3Service;
    private final UserService userService;

    public void uploadFile(DocumentUploadRequest uploadRequest, Authentication authentication){
        UserPrincipal ownerPrincipal = (UserPrincipal) authentication.getPrincipal();
        User owner = userService.findEntityById(ownerPrincipal.getId());
        Document document = Document.builder()
                .title(uploadRequest.title())
                .owner(owner)
                .tags(uploadRequest.tags())
                .shares()
                .build();
    }
}
