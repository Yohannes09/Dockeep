package com.dockeep.document.service;

import com.dockeep.document.dto.DocumentDto;
import com.dockeep.document.dto.DocumentPageDto;
import com.dockeep.document.entity.Document;
import com.dockeep.document.entity.DocumentVersion;
import com.dockeep.document.exception.DocumentNotFoundException;
import com.dockeep.document.mapper.DocumentMapper;
import com.dockeep.document.repository.DocumentRepository;
import com.dockeep.document.request.DocumentUploadRequest;
import com.dockeep.s3.S3Service;
import com.dockeep.user.User;
import com.dockeep.user.UserPrincipal;
import com.dockeep.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentMapper documentMapper;
    private final S3Service s3Service;
    private final UserService userService;
    private final DocumentRepository documentRepository;


    @Transactional
    public DocumentVersion uploadFile(DocumentUploadRequest uploadRequest, Authentication authentication) throws IOException {
        UserPrincipal ownerPrincipal = (UserPrincipal) authentication.getPrincipal();
        User owner = userService.findEntityById(ownerPrincipal.getId());

        Document document;
        int version;

        if(uploadRequest.documentId() != null){
            document = documentRepository
                    .findById(uploadRequest.documentId())
                    .orElseThrow(()-> new DocumentNotFoundException("Document"));
            version = document.getDocumentVersions().size();
        }else {
            version = 1;
            document = Document.builder()
                .owner(owner)
                .title(uploadRequest.title())
                .tags(uploadRequest.tags())
                .build();
        }

        String s3Key = s3Service.uploadFile(uploadRequest.file(), uploadRequest.title());

        DocumentVersion documentVersion = DocumentVersion.builder()
                .document(document)
                .versionNumber(version)
                .fileSize(uploadRequest.file().getSize())
                .s3Key(s3Key)
                .mimeType(uploadRequest.file().getContentType())
                .build();

        document.getDocumentVersions().add(documentVersion);
        documentRepository.save(document);

        return documentVersion;
    }

    public DocumentPageDto getDocuments(Long id, int pageNumber, int pageSize){
        final String sortByProperty = "createdAt";

        Pageable pageable = PageRequest.of(
                pageNumber, pageSize, Sort.by(sortByProperty).descending()
        );

        List<DocumentDto> documents = documentRepository
                .findAllByUserId(id, pageable)
                .map(documentMapper::entityToDto)
                .toList();

        return DocumentPageDto.builder()
                .documents(documents)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .totalElements(documents.size())
                .build();
    }

}
