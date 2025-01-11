package com.earthescapers.Logic.Services;

import com.earthescapers.Logic.Repositories.AlienTribesRepository;
import com.earthescapers.Models.AlienTribe;
import com.earthescapers.Models.Person;
import com.earthescapers.Logic.Repositories.PeopleRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PeopleService {
    private final PeopleRepository _peopleRepository;
    private final AlienTribesRepository _aliensRepostiory;

    @Autowired
    public PeopleService(PeopleRepository repository, AlienTribesRepository alienTribesRepository) {
        _peopleRepository = repository;
        _aliensRepostiory = alienTribesRepository;
    }

    public Optional<Person[]> getAllByTribe(AlienTribe category) {
        return _peopleRepository.findByKidnappedBy(category);
    }

    public void addPerson(Person person) {
        _peopleRepository.save(person);
    }

    public void updatePerson(Person person, UUID kidnappedById) {
        Person p = getById(person.getId()).orElse(null);
        if (p == null) {
            throw new EntityNotFoundException();
        }

        setKidnapped(p.getId(), kidnappedById);

        p = getById(person.getId()).orElse(null);
        p.setName(person.getName());
        p.setAge(person.getAge());
        p.setSurname(person.getSurname());

        _peopleRepository.save(p);
    }

    @Transactional
    public List<Person> getAllPeople() {
        var ppl = _peopleRepository.findAll();
        for (var person :
                ppl) {
            Hibernate.initialize(person.getKidnappedBy());
        }
        return ppl;
    }

    public Optional<Person> getById(UUID id) {
        return _peopleRepository.findById(id);
    }

    public boolean removePerson(UUID id) {
        Person p = _peopleRepository.findById(id).orElse(null);
        if (p == null) {
            return false;
        }

        var a = _aliensRepostiory.findById(p.getKidnappedBy().getId()).orElse(null);
        if (a == null) {
            return false;
        }

        a.getKidnappedPeople().remove(p);

        _aliensRepostiory.save(a);
        _peopleRepository.deleteById(id);
        return true;
    }

    public void setKidnapped(UUID personId, UUID kidnappedBy) {
        AlienTribe tribe = _aliensRepostiory.findById(kidnappedBy).orElse(null);
        Person person = _peopleRepository.findById(personId).orElse(null);

        if (tribe == null || person == null) {
            throw new EntityNotFoundException();
        }

        tribe.kidnap(person);
        _peopleRepository.save(person);
        _aliensRepostiory.save(tribe);
    }
}
