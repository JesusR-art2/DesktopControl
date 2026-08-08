package com.controladorescritorio.deskop.repositories;

import com.controladorescritorio.deskop.entities.AccesToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccesTokenRepository extends JpaRepository<AccesToken, UUID> {
}
