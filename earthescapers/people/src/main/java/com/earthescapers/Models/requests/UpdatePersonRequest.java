package com.earthescapers.Models.requests;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@AllArgsConstructor
public class UpdatePersonRequest {
    private String id;
    private String name;
    private String surname;
    private Integer age;
    private String kidnappedBy;
}