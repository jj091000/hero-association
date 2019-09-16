package com.assessment.hero;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.assessment.hero"})
public class HeroAssociation {
    public static void main(String[] args) {
        SpringApplication.run(HeroAssociation.class, args);
    }
}
