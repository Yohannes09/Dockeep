package com.dockeep.demo.document.repository;

import com.dockeep.demo.document.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
