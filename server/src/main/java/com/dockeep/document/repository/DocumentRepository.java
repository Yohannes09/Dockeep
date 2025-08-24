package com.dockeep.document.repository;

import com.dockeep.document.entity.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    Page<Document> findAllByUserId(Long userId, Pageable pageable);
}
