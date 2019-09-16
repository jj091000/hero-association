package com.assessment.hero.repository.database;

import com.assessment.hero.repository.database.model.HeroDAO;
import org.springframework.data.repository.CrudRepository;

public interface HeroCRUDRepository extends CrudRepository<HeroDAO, Long> {

}
