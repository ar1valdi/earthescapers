package com.earthescapers.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name="ALIEN_TRIBES")
public class AlienTribe implements Comparable<AlienTribe>{

    @Id
    private UUID id;

    @OneToMany(mappedBy="kidnappedBy", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private List<Person> kidnappedPeople;

    public AlienTribe() {
        kidnappedPeople = new LinkedList<>();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj.getClass() != this.getClass()) return false;

        AlienTribe p = (AlienTribe) obj;
        return  p.id.equals(this.id) &&
                p.kidnappedPeople.equals(this.kidnappedPeople);
    }

    public void kidnap(Person p) {
        p.setKidnappedBy(this);

        if (kidnappedPeople == null) {
            kidnappedPeople = new LinkedList<>();
        }

        kidnappedPeople.add(p);
    }

    @Override
    public int compareTo(AlienTribe o) {
        return this.id.compareTo(o.id);
    }
    @Override
    public int hashCode() {
        return  id.hashCode();
    }

    @Override
    public String toString() {
        return "{id:" + id + "}";
    }
    public static class AlienTribeBuilder {
        AlienTribe result;
        public AlienTribeBuilder() {
            result = new AlienTribe();
        }
        public AlienTribeBuilder kidnappedPeople(List<Person> value) {
            result.kidnappedPeople = value;
            return this;
        }
        public AlienTribeBuilder id(UUID id) {
            result.id = id;
            return this;
        }
        public AlienTribe build() {
            return result;
        }
    }
}
