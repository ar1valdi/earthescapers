package com.earthescapers.Logic.Controllers;

import com.earthescapers.Logic.Services.AlienTribesService;
import com.earthescapers.Logic.Services.PeopleService;
import com.earthescapers.Models.DTO.PersonDTO;
import com.earthescapers.Models.DTO.PersonListDTO;
import com.earthescapers.Models.Person;
import com.earthescapers.Models.requests.CreatePersonRequest;
import com.earthescapers.Models.requests.UpdatePersonRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;


@RestController
@RequestMapping("/api/people")
public class PeopleController {

    @Autowired
    private PeopleService _peopleService;

    @Autowired
    private AlienTribesService _tribesService;

    @GetMapping
    public ResponseEntity<PersonListDTO> getAllPeople() {
        var pDto = _peopleService
                .getAllPeople()
                .stream()
                .map(p -> new PersonDTO(p))
                .toList();

        if (pDto.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new PersonListDTO(pDto));
    }

    @GetMapping("/getByTribe/{id}")
    public ResponseEntity<PersonListDTO> getPeopleByTribe(@PathVariable String id) {
        var tribe = _tribesService.getById(UUID.fromString(id)).orElse(null);
        if (tribe == null) {
            return ResponseEntity.notFound().build();
        }

        var pDto = Arrays.stream(_peopleService.getAllByTribe(tribe).orElse(new Person[0]))
                .map(p -> new PersonDTO(p))
                .toList();

        var result = new PersonListDTO(pDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable String id) {
        var p = _peopleService.getById(UUID.fromString(id));
        return p.map(per -> ResponseEntity.ok(new PersonDTO(per))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PersonDTO> createPerson(@RequestBody CreatePersonRequest request) {
        var tribe = _tribesService.getById(UUID.fromString(request.getKidnappedBy())).orElse(null);
        if (tribe == null) {
            return ResponseEntity.notFound().build();
        }

        Person person = new Person.PersonBuilder()
                .name(request.getName())
                .age(request.getAge())
                .surname(request.getSurname())
                .id(UUID.randomUUID())
                .kidnappedBy(tribe)
                .build();

        _peopleService.addPerson(person);
        _peopleService.setKidnapped(person.getId(), UUID.fromString(request.getKidnappedBy()));

        return ok(new PersonDTO(person));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePerson(@PathVariable String id, @RequestBody UpdatePersonRequest updatedPerson) {
        Person p = new Person
                .PersonBuilder()
                .age(updatedPerson.getAge())
                .name(updatedPerson.getName())
                .surname(updatedPerson.getSurname())
                .build();
        p.setId(UUID.fromString(updatedPerson.getId()));

        try {
            _peopleService.updatePerson(p, UUID.fromString(updatedPerson.getKidnappedBy()));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable String id) {
        boolean isDeleted = _peopleService.removePerson(UUID.fromString(id));
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
