package com.assessment.hero.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Hero {
    @NotBlank
    private String superHeroName;
    private String lastName;
    private String firstName;
}
