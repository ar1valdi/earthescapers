package com.earthescapers.Logic.Services;

import com.earthescapers.Logic.Repositories.PeopleRepository;
import com.earthescapers.Models.AlienTribe;
import com.earthescapers.Logic.Repositories.AlienTribesRepository;
import com.earthescapers.Models.Person;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AlienTribesService {
    private final AlienTribesRepository _tribesRepository;
    private final PeopleRepository _peopleRepository;

    @Autowired
    public AlienTribesService(AlienTribesRepository repository,
                              PeopleRepository peopleRepository) {
        _tribesRepository = repository;
        _peopleRepository = peopleRepository;
    }

    @Transactional
    public Optional<AlienTribe> getById(UUID id) {
        AlienTribe tribe = _tribesRepository.findById(id).orElse(null);
        if (tribe != null) {
            Hibernate.initialize(tribe.getKidnappedPeople());
        } else {
            return Optional.empty();
        }
        return Optional.of(tribe);
    }

    public void addTribe(AlienTribe tribe) {
        addTribe(tribe, null);
    }

    public void addTribe(AlienTribe tribe, List<UUID> kidnappedPeople) {
        if (kidnappedPeople != null) {
            List<Person> people = new LinkedList();
            for (UUID id : kidnappedPeople) {
                Person p = _peopleRepository.findById(id).orElse(null);

                if (p == null) {
                    throw new EntityNotFoundException();
                }

                tribe.kidnap(p);
                _peopleRepository.save(p);
            }
        }

        _tribesRepository.save(tribe);
    }

    @Transactional
    public List<AlienTribe> getAllTribes() {
        var results = _tribesRepository.findAll();
        if (!results.isEmpty()) {
            for (AlienTribe result : results) {
                Hibernate.initialize(result.getKidnappedPeople());
            }
        }
        return results;
    }

    public boolean removeTribe(UUID id) {
        var a = _tribesRepository.findById(id);
        if (a.isEmpty()) {
            return false;
        }

        _tribesRepository.deleteById(id);
        return true;
    }
}
