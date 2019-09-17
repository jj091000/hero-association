package com.assessment.hero.controller;

import com.assessment.hero.exception.DuplicateRecordException;
import com.assessment.hero.exception.MissingRecordException;
import com.assessment.hero.model.Hero;
import com.assessment.hero.service.HeroService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class HeroController {
    private final static Logger LOGGER = LoggerFactory.getLogger(HeroController.class);
    private final HeroService heroService;

    @Autowired
    public HeroController(HeroService heroService) {
        this.heroService = heroService;
    }

    @PostMapping("/hero/create")
    public ResponseEntity<String> createHero(@RequestBody @Valid Hero hero) {
        LOGGER.info("incoming request : {}", hero);
        try {
            heroService.create(hero);
            return new ResponseEntity(HttpStatus.OK);
        } catch (DuplicateRecordException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/hero/read")
    public ResponseEntity<String> readHero(String superHeroName) {
        LOGGER.info("incoming request : {}", superHeroName);
        try {
            Hero fetchedHero = heroService.readHero(superHeroName);
            return new ResponseEntity(fetchedHero.toString(), HttpStatus.OK);
        } catch (MissingRecordException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/hero/update")
    public ResponseEntity<String> updateHero(@RequestBody @Valid Hero hero) {
        LOGGER.info("incoming request : {}", hero);
        try {
            heroService.updateHero(hero);
            return new ResponseEntity(HttpStatus.OK);
        } catch (MissingRecordException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
