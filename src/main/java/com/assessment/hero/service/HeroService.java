package com.assessment.hero.service;

import com.assessment.hero.exception.DuplicateRecordException;
import com.assessment.hero.exception.MissingRecordException;
import com.assessment.hero.model.Hero;
import com.assessment.hero.repository.HeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class HeroService {

    private final HeroRepository heroRepository;

    @Autowired
    public HeroService(HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
    }

    public void create(Hero hero) throws DuplicateRecordException {
        heroRepository.create(hero);
    }

    public Hero readHero(String superHeroName) throws MissingRecordException {
        validateSuperHeroName(superHeroName);
        return heroRepository.findHeroBySuperHeroName(superHeroName);
    }

    public void updateHero(Hero hero) throws MissingRecordException {
        heroRepository.update(hero);
    }

    private void validateSuperHeroName(String superHeroName) {
        if(StringUtils.isEmpty(superHeroName)){
            throw new IllegalArgumentException("Please Enter valid super hero name ");
        }
    }

    public void assignMission(String superHeroName, String missionName) throws MissingRecordException {
        validateSuperHeroName(superHeroName);
        heroRepository.addMissionToHeroRecord(superHeroName, missionName);
    }
}
