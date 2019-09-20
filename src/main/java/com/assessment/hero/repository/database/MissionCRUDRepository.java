package com.assessment.hero.repository.database;

import com.assessment.hero.repository.database.model.MissionDAO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MissionCRUDRepository extends CrudRepository<MissionDAO, Long> {
    List<MissionDAO> findByMissionName(String missionName);
    boolean existsByMissionName(String missionName);
    void deleteByMissionName(String superHeroName);
}
