package com.dockeep.demo.document.entity;

import com.dockeep.demo.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = false)
    @ManyToOne
    private User owner;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @OneToMany
    private List<Share> shares = new ArrayList<>();

    private List<String> tags = new ArrayList<>();
}
