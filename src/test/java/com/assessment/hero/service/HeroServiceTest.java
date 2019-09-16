package com.assessment.hero.service;

import com.assessment.hero.model.Hero;
import com.assessment.hero.repository.HeroRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static com.assessment.hero.HeroUtil.BuildHero;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(MockitoJUnitRunner.class)
public class HeroServiceTest {

    private HeroService heroService;
    @Mock
    private HeroRepository heroRepository;

    private Hero hero;

    @Before
    public void setUp() throws Exception {
        heroService = new HeroService(heroRepository);
        hero = BuildHero();
    }

    @Test
    public void create_should_call_save_when_receives_hero(){
        //when
        heroService.create(hero);

        //then
        Mockito.verify(heroRepository).save(eq(hero));
    }
}