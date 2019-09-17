package com.assessment.hero.service;

import com.assessment.hero.exception.DuplicateRecordException;
import com.assessment.hero.model.Hero;
import com.assessment.hero.repository.HeroRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.assessment.hero.HeroUtil.BuildHero;
import static com.assessment.hero.HeroUtil.SUPER_HERO_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

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
    public void create_should_call_save_when_receives_hero() throws Exception {
        //when
        heroService.create(hero);

        //then
        verify(heroRepository).create(eq(hero));
    }

    @Test
    public void create_should_not_catch_exception_when_duplicate_is_thrown() throws Exception {
        //given
        DuplicateRecordException mockException = mock(DuplicateRecordException.class);
        doThrow(mockException).when(heroRepository).create(eq(hero));

        //when
        Throwable throwable = catchThrowable(()-> heroService.create(hero));

        //then
        assertThat(throwable).isEqualTo(mockException);
    }

    @Test
    public void readHero_should_call_findHeroBySuperHeroName_when_receives_hero_name() throws Exception {
        //when
        heroService.readHero(SUPER_HERO_NAME);

        //then
        verify(heroRepository).findHeroBySuperHeroName(eq(SUPER_HERO_NAME));
    }

    @Test
    public void readHero_should_throw_execption_when_receives_null_super_hero_name() throws Exception {
        //given
        hero.setSuperHeroName(null);

        //when
        heroService.readHero(SUPER_HERO_NAME);

        //then
        verify(heroRepository).findHeroBySuperHeroName(eq(SUPER_HERO_NAME));
    }

    @Test
    public void readHero_should_throw_execption_when_receives_empty_super_hero_name() throws Exception {
        //given
        hero.setSuperHeroName("");

        //when
        heroService.readHero(SUPER_HERO_NAME);

        //then
        verify(heroRepository).findHeroBySuperHeroName(eq(SUPER_HERO_NAME));
    }

    @Test
    public void readHero_should_throw_execption_when_receives_blank_super_hero_name() throws Exception {
        //given
        hero.setSuperHeroName(" ");

        //when
        heroService.readHero(SUPER_HERO_NAME);

        //then
        verify(heroRepository).findHeroBySuperHeroName(eq(SUPER_HERO_NAME));
    }

    @Test
    public void update_should_call_update_with_received_hero() throws Exception {
        //when
        heroService.updateHero(hero);

        //then
        verify(heroRepository).update(eq(hero));
    }
}