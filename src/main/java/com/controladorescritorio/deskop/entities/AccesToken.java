package com.controladorescritorio.deskop.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name ="access_token")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccesToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    @Column(nullable = false)
    private String pin_hash;

    @Column(nullable = false, name = "expires_at")
    private Instant expires_at;

    @Column(nullable = false, name = "used_at")
    private Instant used_at;

    @Column(nullable = false, name = "created_at", updatable = false)
    private Instant created_at;
}
