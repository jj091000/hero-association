package com.assessment.hero.repository.database;

import com.assessment.hero.repository.database.model.HeroDAO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroCRUDRepository extends CrudRepository<HeroDAO, Long> {

}
