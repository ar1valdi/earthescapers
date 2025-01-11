package com.earthescapers.Models.DTO;

import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@AllArgsConstructor
public class AlienTribeListDTO {
    private List<AlienTribeDTO> alienTribes;
}
