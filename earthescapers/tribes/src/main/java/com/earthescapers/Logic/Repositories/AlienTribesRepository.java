package com.earthescapers.Logic.Repositories;

import com.earthescapers.Models.AlienTribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AlienTribesRepository extends JpaRepository<AlienTribe, UUID> {
    Optional<AlienTribe> findByName(String name);
}
