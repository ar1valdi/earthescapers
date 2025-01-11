package com.earthescapers;

import com.earthescapers.Models.AlienTribe;
import com.earthescapers.Models.Person;
import com.earthescapers.Logic.Services.AlienTribesService;
import com.earthescapers.Logic.Services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DataInitializer {
    private final PeopleService _peopleService;
    private final AlienTribesService _alienTribeService;


    @Autowired
    DataInitializer(PeopleService ps, AlienTribesService as) {
        _peopleService = ps;
        _alienTribeService = as;
    }


    public void init() {
        Person p1 = new Person.PersonBuilder()
                .name("John")
                .surname("Krasicki")
                .age(35)
                .kidnappedBy(null)
                .id(UUID.randomUUID())
                .build();
        Person p2 = new Person.PersonBuilder()
                .name("Jan")
                .surname("Kaczerski")
                .age(36)
                .kidnappedBy(null)
                .id(UUID.randomUUID())
                .build();
        Person p3 = new Person.PersonBuilder()
                .name("Sample")
                .surname("Name")
                .age(30)
                .kidnappedBy(null)
                .id(UUID.randomUUID())
                .build();
        Person p4 = new Person.PersonBuilder()
                .name("Kuba")
                .surname("Rozpruwacz")
                .age(25)
                .kidnappedBy(null)
                .id(UUID.randomUUID())
                .build();

        AlienTribe a1 = new AlienTribe.AlienTribeBuilder()
                .id(UUID.fromString("0e65a49a-5e9b-4530-ac14-a1c2ac50c1a7"))
                .kidnappedPeople(null)
                .build();
        AlienTribe a2 = new AlienTribe.AlienTribeBuilder()
                .id(UUID.fromString("4389755f-45e9-4a72-92b2-bd553dfb58c4"))
                .kidnappedPeople(null)
                .build();

        a1.kidnap(p1);
        a1.kidnap(p2);
        a1.kidnap(p3);
        a2.kidnap(p4);

        _alienTribeService.addTribe(a1);
        _alienTribeService.addTribe(a2);
        _peopleService.addPerson(p2);
        _peopleService.addPerson(p3);
        _peopleService.addPerson(p4);
        _peopleService.addPerson(p1);
    }
}
