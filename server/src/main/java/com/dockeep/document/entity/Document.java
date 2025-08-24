package com.dockeep.document.entity;

import com.dockeep.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Document {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "document_id_sequence"
    )
    @SequenceGenerator(
            name = "document_id_sequence",
            sequenceName = "document_id_sequence",
            initialValue = 11_957_103,
            allocationSize = 9
    )
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @Column(nullable = false)
    private User owner;

    @OneToMany
    @Column
    private Set<DocumentShare> shares = new HashSet<>();

    @OneToMany(mappedBy = "document")
    @Column(name = "document_version")
    private List<DocumentVersion> documentVersions = new ArrayList<>();

    private List<String> tags = new ArrayList<>();

    public Optional<DocumentVersion> getCurrentVersion(){
        return documentVersions
                .stream()
                .max(Comparator.comparing(DocumentVersion::getLastModified));
    }
}
