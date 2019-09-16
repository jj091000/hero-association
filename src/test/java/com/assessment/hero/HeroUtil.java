package com.assessment.hero;

import com.assessment.hero.model.Hero;

public class HeroUtil {

    private HeroUtil(){}

    public static Hero BuildHero(){
        Hero hero =  new Hero();
        hero.setFirstName("FirstName");
        hero.setLastName("LastName");
        hero.setSuperHeroName("SuperHeroName");
        return hero;
    }
}
