package com.dockeep.demo.document.entity;

import com.dockeep.demo.document.constant.DocumentSharePermission;
import com.dockeep.demo.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Share {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "document_id")
    private Document document;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User recipient;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<DocumentSharePermission> documentSharePermissions = new HashSet<>();

    @CreationTimestamp
    private LocalDateTime beganSharingAt;

    @Column(name = "s3_path", nullable = false)
    private String s3Path;

    @PrePersist
    public void onCreate(){
        if (documentSharePermissions.isEmpty()){
            documentSharePermissions.add(DocumentSharePermission.READ);
        }
    }
}
