package com.assessment.hero.repository;

import com.assessment.hero.mapping.HeroMapper;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.assessment.hero.HeroUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HeroRepositoryTest {

    private HeroRepository heroRepository;
    @Mock
    private HeroCRUDRepository heroCRUDRepository;
    @Mock
    private HeroMapper heroMapper;

    private Hero hero;
    private HeroDAO heroDAO;

    @Before
    public void setUp() throws Exception {
        heroRepository = new HeroRepository(heroCRUDRepository, heroMapper);
        hero = BuildHero();
        heroDAO = BuildHeroDAO();

        when(heroMapper.mapHeroToHeroDAO(eq(hero))).thenReturn(BuildHeroDAO());
        when(heroMapper.mapHeroDAOToHero(eq(heroDAO))).thenReturn(BuildHero());
        when(heroCRUDRepository.existsBySuperHeroName(eq(SUPER_HERO_NAME)))
                .thenReturn(false);
    }

    @Test
    public void save_should_call_mapper_to_transfer_data_from_hero_to_hero_DAO() throws Exception {
        //when
        heroRepository.create(hero);

        //then
        Mockito.verify(heroMapper).mapHeroToHeroDAO(eq(hero));
    }

    @Test
    public void save_should_create_new_record_in_database_with_received_hero_info() throws Exception {
        //when
        heroRepository.create(hero);

        //then
        ArgumentCaptor<HeroDAO> argumentCaptor = ArgumentCaptor.forClass(HeroDAO.class);
        Mockito.verify(heroCRUDRepository).save(argumentCaptor.capture());
        HeroDAO captorValue = argumentCaptor.getValue();
        assertThat(captorValue).isEqualToComparingFieldByField(hero);
    }

    @Test
    public void save_should_not_create_new_record_if_superHeroName_already_used() throws Exception {
        //given
        when(heroCRUDRepository.existsBySuperHeroName(eq(SUPER_HERO_NAME)))
                .thenReturn(true);
        //when
        Throwable throwable = catchThrowable(()-> heroRepository.create(hero));

        //then
        assertThat(throwable).hasMessage("Cannot create hero, Super hero name : " + SUPER_HERO_NAME + ", already used");
    }

    @Test
    public void findHeroBySuperHeroName_should_search_for_hero_record_in_database_with_received_hero_name(){
        //when
        Hero result = heroRepository.findHeroBySuperHeroName(SUPER_HERO_NAME);

        //then
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(heroCRUDRepository).findBySuperHeroName(argumentCaptor.capture());
        String superHeroName = argumentCaptor.getValue();
        assertThat(superHeroName).isEqualTo(SUPER_HERO_NAME);
    }

    @Test
    public void findHeroBySuperHeroName_should_return_null_when_received_list_is_null(){
        //when
        Hero result = heroRepository.findHeroBySuperHeroName(SUPER_HERO_NAME);

        //then
        assertThat(result).isNull();
    }

    @Test
    public void findHeroBySuperHeroName_should_return_null_when_received_list_is_empty(){
        //given
        when(heroCRUDRepository.findBySuperHeroName(eq(SUPER_HERO_NAME)))
                .thenReturn(Collections.emptyList());

        //when
        Hero result = heroRepository.findHeroBySuperHeroName(SUPER_HERO_NAME);

        //then
        assertThat(result).isNull();
    }

    @Test
    public void findHeroBySuperHeroName_should_return_null_when_received_list_only_contains_null(){
        //given
        List<HeroDAO> record = new ArrayList<>();
        record.add(null);
        when(heroCRUDRepository.findBySuperHeroName(eq(SUPER_HERO_NAME)))
                .thenReturn(record);

        //when
        Hero result = heroRepository.findHeroBySuperHeroName(SUPER_HERO_NAME);

        //then
        assertThat(result).isNull();
    }

    @Test
    public void findHeroBySuperHeroName_should_call_mapper_to_transfer_data_from_hero_DAO_to_hero(){
        //given
        List<HeroDAO> record = new ArrayList<>();
        record.add(heroDAO);
        when(heroCRUDRepository.findBySuperHeroName(eq(SUPER_HERO_NAME)))
                .thenReturn(record);

        //when
        heroRepository.findHeroBySuperHeroName(SUPER_HERO_NAME);

        //then
        Mockito.verify(heroMapper).mapHeroDAOToHero(eq(heroDAO));
    }

    @Test
    public void findHeroBySuperHeroName_should_return_hero_received_from_herocrudrepository(){
        //given
        List<HeroDAO> record = new ArrayList<>();
        record.add(heroDAO);
        when(heroCRUDRepository.findBySuperHeroName(eq(SUPER_HERO_NAME)))
                .thenReturn(record);

        //when
        Hero result = heroRepository.findHeroBySuperHeroName(SUPER_HERO_NAME);

        //then
        assertThat(result).isEqualToComparingFieldByField(heroDAO);
    }
}