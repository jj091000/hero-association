package com.assessment.hero.repository;

import com.assessment.hero.model.Hero;
import com.assessment.hero.repository.database.HeroCRUDRepository;
import com.assessment.hero.repository.database.model.HeroDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static com.assessment.hero.HeroUtil.BuildHero;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class HeroRepositoryTest {

    private HeroRepository heroRepository;
    @Mock
    private HeroCRUDRepository heroCRUDRepository;

    private Hero hero;

    @Before
    public void setUp() throws Exception {
        heroRepository = new HeroRepository(heroCRUDRepository);
        hero = BuildHero();
    }

    @Test
    public void save_should_create_new_record_in_database_with_received_hero_info(){
        //when
        heroRepository.save(hero);

        //then
        ArgumentCaptor<HeroDAO> argumentCaptor = ArgumentCaptor.forClass(HeroDAO.class);
        Mockito.verify(heroCRUDRepository).save(argumentCaptor.capture());
        HeroDAO captorValue = argumentCaptor.getValue();
        assertThat(captorValue).isEqualToComparingFieldByField(hero);
    }
}