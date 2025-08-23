package com.dockeep.document.entity;

import com.dockeep.document.constant.DocumentSharePermission;
import com.dockeep.user.User;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class DocumentShare {
    @Id
    private Long id;

    @ManyToOne
    private Document document;

    @ManyToOne
    private User user;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<DocumentSharePermission> permissions = new HashSet<>();
}
