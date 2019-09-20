package com.assessment.hero.repository.database.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "MISSION")
public class MissionDAO {
    @Id
    private String  missionName;
    private Boolean isCompleted = false;
    private Boolean isDeleted = false;
}
