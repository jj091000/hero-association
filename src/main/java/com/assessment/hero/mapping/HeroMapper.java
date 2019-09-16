package com.assessment.hero.mapping;

import com.assessment.hero.model.Hero;
import com.assessment.hero.repository.database.model.HeroDAO;
import org.springframework.stereotype.Component;

@Component
public class HeroMapper {

    public HeroDAO mapHeroToHeroDAO(Hero hero){
        HeroDAO heroDAO = new HeroDAO();
        heroDAO.setFirstName(hero.getFirstName());
        heroDAO.setLastName(hero.getLastName());
        heroDAO.setSuperHeroName(hero.getSuperHeroName());
        return heroDAO;
    }
}
