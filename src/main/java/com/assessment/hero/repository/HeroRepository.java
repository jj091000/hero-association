package com.assessment.hero.repository;

import com.assessment.hero.exception.DuplicateRecordException;
import com.assessment.hero.exception.MissingRecordException;
import com.assessment.hero.mapping.HeroMapper;
import com.assessment.hero.model.Hero;
import com.assessment.hero.repository.database.HeroCRUDRepository;
import com.assessment.hero.repository.database.model.HeroDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Repository
public class HeroRepository {
    private final static Logger LOGGER = LoggerFactory.getLogger(HeroRepository.class);

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
            throw new DuplicateRecordException("Super hero: " + superHeroName + ", already used");
        }
        save(hero);
    }

    public void update(Hero hero) throws MissingRecordException {
        Hero target = findHeroBySuperHeroName(hero.getSuperHeroName());
        heroMapper.mapUpdatedInfo(target, hero);
        save(target);
    }

    public Hero findHeroBySuperHeroName(String superHeroName) throws MissingRecordException {
        List<HeroDAO> bySuperHeroName = findRecordBySuperHeroName(superHeroName);

        if(CollectionUtils.isEmpty(bySuperHeroName)){
            throw new MissingRecordException("Super hero: " + superHeroName + ", doesn't exists");
        }
        return heroMapper.mapHeroDAOToHero(bySuperHeroName.get(0));
    }

    private List<HeroDAO> findRecordBySuperHeroName(String superHeroName) {
        List<HeroDAO> bySuperHeroName = heroCRUDRepository.findBySuperHeroName(superHeroName);
        LOGGER.info("found hero : {}", bySuperHeroName);
        return bySuperHeroName;
    }

    private void save(Hero hero){
        HeroDAO heroDAO = heroMapper.mapHeroToHeroDAO(hero);
        heroCRUDRepository.save(heroDAO);
        LOGGER.info("saved hero : {}", hero);
    }
}
