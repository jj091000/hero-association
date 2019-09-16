package com.assessment.hero.repository.database;

import com.assessment.hero.repository.database.model.HeroDAO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeroCRUDRepository extends CrudRepository<HeroDAO, Long> {
    List<HeroDAO> findBySuperHeroName(String superHeroName);
    boolean existsBySuperHeroName(String superHeroName);
    void deleteBySuperHeroName(String superHeroName);
}
