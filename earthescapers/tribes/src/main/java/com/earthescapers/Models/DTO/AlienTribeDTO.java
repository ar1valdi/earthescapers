package com.earthescapers.Models.DTO;

import com.earthescapers.Models.AlienTribe;
import lombok.*;
import lombok.Builder;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@AllArgsConstructor
public class AlienTribeDTO {
    private String id;
    private String name;
    private Integer population;
    private Integer spaceshipSpeed;

    public AlienTribeDTO(AlienTribe fullTribe) {
        id = fullTribe.getId().toString();
        name = fullTribe.getName();
        population = fullTribe.getPopulation();
        spaceshipSpeed = fullTribe.getSpaceshipSpeed();
    }
}
