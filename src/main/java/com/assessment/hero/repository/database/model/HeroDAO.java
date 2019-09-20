package com.assessment.hero.repository.database.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "HERO")
public class HeroDAO {
    @Id
    private String superHeroName;
    private String lastName;
    private String firstName;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "hero_mission",
            joinColumns = @JoinColumn(name = "superHeroName"),
            inverseJoinColumns = @JoinColumn(name = "missionName"))
    private List<MissionDAO> missions = new ArrayList<>();

    public void addMission(MissionDAO missionDAO) {
        this.missions.add(missionDAO);
    }

    public void removeMission(MissionDAO missionDAO) {
        this.missions.remove(missionDAO);
    }
}
