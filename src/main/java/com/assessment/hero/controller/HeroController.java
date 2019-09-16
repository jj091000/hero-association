package com.assessment.hero.controller;

import com.assessment.hero.model.Hero;
import com.assessment.hero.service.HeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class HeroController {

    private final HeroService heroService;

    @Autowired
    public HeroController(HeroService heroService) {
        this.heroService = heroService;
    }

    @PostMapping("/hero/create")
    public ResponseEntity<String> createHero(@RequestBody @Valid Hero hero) {
        heroService.create(hero);
        return new ResponseEntity(HttpStatus.OK);
    }
}
