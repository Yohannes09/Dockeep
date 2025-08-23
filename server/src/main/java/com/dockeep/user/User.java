package com.dockeep.user;

import com.dockeep.authorization.entity.Role;
import com.dockeep.document.entity.Document;
import com.dockeep.document.entity.DocumentShare;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    @Email
    private String email;

    @Column(nullable = false, unique = true)
    @Size(min = 4, max = 30)
    private String username;

    @Column(nullable = false)
    @Size(min = 2, max = 30)
    private String firstName;

    @Column(nullable = false)
    @NotBlank
    private String lastName;

    @OneToMany(mappedBy = "owner")
    private List<Document> documents = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<DocumentShare> sharedDocuments = new ArrayList<>();

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private String status;

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
}
