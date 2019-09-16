package com.assessment.hero.service;

import com.assessment.hero.model.Hero;
import com.assessment.hero.repository.HeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HeroService {

    private final HeroRepository heroRepository;

    @Autowired
    public HeroService(HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
    }

    public void create(Hero hero){
        heroRepository.save(hero);
    }
}
