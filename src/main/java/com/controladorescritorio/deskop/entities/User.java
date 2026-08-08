package com.controladorescritorio.deskop.entities;

import com.controladorescritorio.deskop.exchanges.type.RoleType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "organization_id")
    private Organization organization;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false,name = "password_hash")
    private String passwordHash;

    @Column(nullable = false, name = "full_name")
    private String fullName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleType role;

    @Builder.Default
    @Column(nullable = false, name = "is_active")
    private boolean is_Active = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

}
