package com.controladorescritorio.deskop.entities;

import com.controladorescritorio.deskop.exchanges.type.ConnectionType;
import jakarta.persistence.*;

import java.math.BigInteger;
import java.time.Instant;
import java.util.UUID;
import lombok.*;


@Entity
@Table(name = "remote_session")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RemoteSession {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "support_id", nullable = false)
    private User support;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "connection_type")
    private ConnectionType connectionType;

    @Column(nullable = false, name = "end_reason")
    private String endReason;

    @Column(nullable = false, name = "started_at")
    private Instant startedAt;

    @Column(nullable = false, name = "ended_at")
    private Instant endedAt;

    private Integer avg_latency_ms;

    private BigInteger bytes_transferred;


}
