package com.assessment.hero.util;

import com.assessment.hero.model.Hero;
import com.assessment.hero.repository.database.model.HeroDAO;

public class HeroUtil {

    public static final String FIRST_NAME = "FirstName";
    public static final String LAST_NAME = "LastName";
    public static final String SUPER_HERO_NAME = "SuperHeroName";

    private HeroUtil(){}

    public static Hero BuildHero(){
        Hero hero =  new Hero();
        hero.setFirstName(FIRST_NAME);
        hero.setLastName(LAST_NAME);
        hero.setSuperHeroName(SUPER_HERO_NAME);
        return hero;
    }

    public static HeroDAO BuildHeroDAO(){
        HeroDAO heroDAO =  new HeroDAO();
        heroDAO.setFirstName(FIRST_NAME);
        heroDAO.setLastName(LAST_NAME);
        heroDAO.setSuperHeroName(SUPER_HERO_NAME);
        return heroDAO;
    }
}
