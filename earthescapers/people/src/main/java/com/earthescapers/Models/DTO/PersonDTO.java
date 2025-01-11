package com.earthescapers.Models.DTO;

import com.earthescapers.Models.Person;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class PersonDTO implements Serializable {
    UUID id;
    String name;
    String surname;
    Integer age;

    String kidnappedByName;
    UUID kidnappedBy;

    public PersonDTO(Person p) {
        id = p.getId();
        name = p.getName();
        surname = p.getSurname();
        age = p.getAge();
        kidnappedBy = p.getKidnappedBy().getId();
    }
    public static class PersonBuilder {
        private final PersonDTO result;

        public PersonBuilder() {
            result = new PersonDTO();  // Initialize the result field
        }

        public PersonBuilder name(String name) {
            result.name = name;
            return this;
        }

        public PersonBuilder surname(String surname) {
            result.surname = surname;
            return this;
        }

        public PersonBuilder age(int age) {
            result.age = age;
            return this;
        }

        public PersonBuilder kidnappedBy(UUID kidnappedBy) {
            result.kidnappedBy = kidnappedBy;
            return this;
        }

        public PersonBuilder kidnappedByName(String kidnappedByName) {
            result.kidnappedByName = kidnappedByName;
            return  this;
        }

        public PersonDTO build() {
            return result;
        }
    }
}
