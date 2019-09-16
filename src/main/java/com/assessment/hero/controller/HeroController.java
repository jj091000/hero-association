package com.assessment.hero.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeroController {

    @PostMapping("/hero/create")
    public ResponseEntity<String> createHero() {
        return new ResponseEntity(HttpStatus.OK);
    }
}
