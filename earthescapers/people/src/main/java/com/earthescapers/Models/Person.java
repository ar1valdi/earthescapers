package com.earthescapers.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name="PEOPLE")
public class Person implements Comparable<Person>{
    @Column(name="SURNAME")
    private String surname;

    @Column(name="NAME")
    private String name;

    @Column(name="AGE")
    private Integer age;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="KIDNAPPED_BY")
    private AlienTribe kidnappedBy;

    @Id
    private UUID id;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj.getClass() != this.getClass()) return false;

        Person p = (Person) obj;
        return  p.name.equals(this.name) &&
                p.surname.equals(this.surname) &&
                p.age.equals(this.age) &&
                p.kidnappedBy.equals(this.kidnappedBy);
    }

    @Override
    public int hashCode() {
        return name.hashCode() & surname.hashCode() & age & kidnappedBy.hashCode();
    }

    @Override
    public String toString() {
        return "{id:" + id + " name:" + name + ";surname:" + surname + ";age:" + age + ";kidnappedBy:" + kidnappedBy.getId() + ";}";
    }

    private Person() {}

    @Override
    public int compareTo(Person o) {
        return this.name.compareTo(o.name);
    }

    public static class PersonBuilder {
        private Person result;

        public PersonBuilder() {
            result = new Person();
        }

        public PersonBuilder name(String name) {
            result.name = name;
            return this;
        }

        public PersonBuilder surname(String surname) {
            result.surname = surname;
            return this;
        }

        public PersonBuilder id(UUID id) {
            result.id = id;
            return this;
        }

        public PersonBuilder age(int age) {
            result.age = age;
            return this;
        }

        public PersonBuilder kidnappedBy(AlienTribe kidnappedBy) {
            result.kidnappedBy = kidnappedBy;
            return this;
        }

        public Person build() {
            return result;
        }
    }
}
