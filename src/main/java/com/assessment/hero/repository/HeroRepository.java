package com.assessment.hero.repository;

import com.assessment.hero.exception.DuplicateRecordException;
import com.assessment.hero.mapping.HeroMapper;
import com.assessment.hero.model.Hero;
import com.assessment.hero.repository.database.HeroCRUDRepository;
import com.assessment.hero.repository.database.model.HeroDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HeroRepository {

    private final HeroCRUDRepository heroCRUDRepository;
    private final HeroMapper heroMapper;

    @Autowired
    public HeroRepository(HeroCRUDRepository heroCRUDRepository, HeroMapper heroMapper) {
        this.heroCRUDRepository = heroCRUDRepository;
        this.heroMapper = heroMapper;
    }

    public void create(Hero hero) throws DuplicateRecordException {
        String superHeroName = hero.getSuperHeroName();
        if(heroCRUDRepository.existsBySuperHeroName(superHeroName)){
            throw new DuplicateRecordException("Cannot create hero, Super hero name : " + superHeroName + ", already used");
        }
        save(hero);
    }

    private void save(Hero hero){
        HeroDAO heroDAO = heroMapper.mapHeroToHeroDAO(hero);
        heroCRUDRepository.save(heroDAO);
    }
}
