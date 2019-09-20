package com.assessment.hero.repository;

import com.assessment.hero.exception.DuplicateRecordException;
import com.assessment.hero.exception.MissingRecordException;
import com.assessment.hero.mapping.HeroMapper;
import com.assessment.hero.model.Hero;
import com.assessment.hero.repository.database.HeroCRUDRepository;
import com.assessment.hero.repository.database.MissionCRUDRepository;
import com.assessment.hero.repository.database.model.HeroDAO;
import com.assessment.hero.repository.database.model.MissionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HeroRepository {
    private final static Logger LOGGER = LoggerFactory.getLogger(HeroRepository.class);

    private final HeroCRUDRepository heroCRUDRepository;
    private final MissionCRUDRepository missionCRUDRepository;
    private final HeroMapper heroMapper;

    @Autowired
    public HeroRepository(HeroCRUDRepository heroCRUDRepository, MissionCRUDRepository missionCRUDRepository, HeroMapper heroMapper) {
        this.heroCRUDRepository = heroCRUDRepository;
        this.missionCRUDRepository = missionCRUDRepository;
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
        HeroDAO recordBySuperHeroName = findRecordBySuperHeroName(superHeroName);
        return heroMapper.mapHeroDAOToHero(recordBySuperHeroName);
    }

    public void addMissionToHeroRecord(String superHeroName, String missionName) throws MissingRecordException {
        HeroDAO recordBySuperHeroName = findRecordBySuperHeroName(superHeroName);
        if(!missionCRUDRepository.existsByMissionName(missionName)){
            throw new MissingRecordException("Mission: " + missionName + ", doesn't exists");
        }
        MissionDAO missionDAO = missionCRUDRepository.findByMissionName(missionName).get(0);
        recordBySuperHeroName.addMission(missionDAO);
        heroCRUDRepository.save(recordBySuperHeroName);
    }

    private HeroDAO findRecordBySuperHeroName(String superHeroName) throws MissingRecordException {
        if(!heroCRUDRepository.existsBySuperHeroName(superHeroName)){
            throw new MissingRecordException("Super hero: " + superHeroName + ", doesn't exists");
        }
        List<HeroDAO> bySuperHeroName = heroCRUDRepository.findBySuperHeroName(superHeroName);
        return bySuperHeroName.get(0);
    }

    private void save(Hero hero){
        HeroDAO heroDAO = heroMapper.mapHeroToHeroDAO(hero);
        heroCRUDRepository.save(heroDAO);
        LOGGER.info("saved hero : {}", hero);
    }
}
