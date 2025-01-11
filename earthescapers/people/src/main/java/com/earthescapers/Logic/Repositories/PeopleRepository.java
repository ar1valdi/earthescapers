package com.earthescapers.Logic.Repositories;

import com.earthescapers.Models.AlienTribe;
import com.earthescapers.Models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PeopleRepository extends JpaRepository<Person, UUID> {
    Optional<Person[]> findByKidnappedBy(AlienTribe tribe);
}
