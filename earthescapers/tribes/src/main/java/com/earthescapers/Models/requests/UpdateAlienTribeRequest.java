package com.earthescapers.Models.requests;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@AllArgsConstructor
public class UpdateAlienTribeRequest {
    private String name;
    private Integer population;
    private Integer spaceshipSpeed;
}