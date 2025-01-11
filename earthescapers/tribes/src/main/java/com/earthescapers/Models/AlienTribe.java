package com.earthescapers.Models;

import com.earthescapers.Logic.Controllers.AlienTribeController;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name="ALIEN_TRIBES")
public class AlienTribe implements Comparable<AlienTribe>{
    @Column(name="NAME")
    private String name;

    @Column(name="POPULATION")
    private Integer population;

    @Column(name="SPACESHIP_SPEED")
    private Integer spaceshipSpeed;

    @Id
    private UUID id;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj.getClass() != this.getClass()) return false;

        AlienTribe p = (AlienTribe) obj;
        return  p.name.equals(this.name) &&
                p.population.equals(this.population) &&
                p.spaceshipSpeed.equals(this.spaceshipSpeed);
    }

    @Override
    public int compareTo(AlienTribe o) {
        return this.name.compareTo(o.name);
    }
    @Override
    public int hashCode() {
        return  name.hashCode() &
                population.hashCode() &
                spaceshipSpeed.hashCode();
    }

    @Override
    public String toString() {
        return "{id:" + id +
                ";name:" + name +
                ";population:" + population +
                ";spaceshipSpeed:" + spaceshipSpeed +
                "}";
    }
    public static class AlienTribeBuilder {
        AlienTribe result;

        public AlienTribeBuilder id(UUID id) {
            result.id = id;
            return this;
        }

        public AlienTribeBuilder() {
            result = new AlienTribe();
        }

        public AlienTribeBuilder name(String name) {
            result.name = name;
            return this;
        }
        public AlienTribeBuilder population(int value) {
            result.population = value;
            return this;
        }
        public AlienTribeBuilder spaceshipSpeed(int value) {
            result.spaceshipSpeed = value;
            return this;
        }
        public AlienTribe build() {
            return result;
        }
    }
}
