package com.assessment.hero.repository;

import com.assessment.hero.model.Hero;
import com.assessment.hero.repository.database.HeroCRUDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HeroRepository {

    private final HeroCRUDRepository heroCRUDRepository;

    @Autowired
    public HeroRepository(HeroCRUDRepository heroCRUDRepository) {
        this.heroCRUDRepository = heroCRUDRepository;
    }

    public void save(Hero hero){
        heroCRUDRepository.save(hero);
    }
}
