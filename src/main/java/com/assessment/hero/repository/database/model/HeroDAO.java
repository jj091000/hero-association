package com.assessment.hero.repository.database.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "HERO")
public class HeroDAO {
    @Id
    private String superHeroName;
    private String lastName;
    private String firstName;
}
