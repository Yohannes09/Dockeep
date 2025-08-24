package com.dockeep.document;

import com.dockeep.document.dto.DocumentPageDto;
import com.dockeep.document.entity.Document;
import com.dockeep.document.entity.DocumentVersion;
import com.dockeep.document.request.DocumentUploadRequest;
import com.dockeep.document.response.DocumentUploadResponse;
import com.dockeep.document.service.DocumentService;
import com.dockeep.s3.S3Service;
import com.dockeep.user.UserPrincipal;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("${endpoints.documents.base:/api/v1}")
@RequiredArgsConstructor
public class DocumentsController {
    private final DocumentService documentService;
    private final S3Service s3Service;

    @PostMapping("/documents")
    public ResponseEntity<DocumentUploadResponse> uploadDocument(
            @Valid @ModelAttribute DocumentUploadRequest uploadRequest,
            Authentication authentication
    ) throws IOException {
        DocumentVersion version = documentService.uploadFile(uploadRequest, authentication);

        Document document = version.getDocument();

        DocumentUploadResponse documentUploadResponse =
                DocumentUploadResponse.builder()
                        .id(document.getId())
                        .title(document.getTitle())
                        .versionNumber(version.getVersionNumber())
                        .s3Path(version.getS3Key())
                        .createdAt(version.getCreatedAt())
                        .tags(document.getTags())
                        .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(documentUploadResponse);
    }

    @QueryMapping
    public DocumentPageDto getDocumentsByUserId(
            Authentication authentication,
            @Argument int pageNumber,
            @Argument int pageSize
    ){
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Long id = Optional.of(userPrincipal)
                .orElseThrow(() -> new RuntimeException(""))
                .getId();

        return documentService.getDocuments(id, pageNumber, pageSize);
    }

    @GetMapping("/files/{key}")
    public ResponseEntity<String> getPresignedObjectRequest(@PathVariable @NotBlank String key){
        String signedGetUrl = s3Service.generateGetPresignedURL(key);
        return ResponseEntity.ok(signedGetUrl);
    }

}
