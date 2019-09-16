package com.assessment.hero.repository;

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

    public void save(Hero hero){
        HeroDAO heroDAO = new HeroDAO();
        heroDAO.setFirstName(hero.getFirstName());
        heroDAO.setLastName(hero.getLastName());
        heroDAO.setSuperHeroName(hero.getSuperHeroName());
        heroCRUDRepository.save(heroDAO);
    }
}
