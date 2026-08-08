package com.controladorescritorio.deskop.repositories;

import com.controladorescritorio.deskop.entities.RemoteSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RemoteSessionRepository extends JpaRepository<RemoteSession, UUID> {
}
