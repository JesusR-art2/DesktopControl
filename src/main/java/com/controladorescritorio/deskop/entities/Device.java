package com.controladorescritorio.deskop.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.net.InetAddress;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "device")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @Column(nullable = false, unique = true)
    private String deviceCode;

    private String hostname;

    private String operatingSystem;

    private String os_version;

    private String agent_version;

    @Column(nullable = false)
    private String public_key;

    private String unattended_hash;

    @Column(name = "last_seen_at")
    private Instant lastSeenAt;

    @Column(name = "last_ip", columnDefinition = "inet")
    private InetAddress lastIp;

    @Column(nullable = false, name = "created_at", updatable = false)
    @CreationTimestamp
    private Instant createdAt;



}
