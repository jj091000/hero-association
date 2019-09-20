package com.assessment.hero.repository;

import com.assessment.hero.exception.MissingRecordException;
import com.assessment.hero.mapping.HeroMapper;
import com.assessment.hero.model.Hero;
import com.assessment.hero.repository.database.HeroCRUDRepository;
import com.assessment.hero.repository.database.MissionCRUDRepository;
import com.assessment.hero.repository.database.model.HeroDAO;
import com.assessment.hero.repository.database.model.MissionDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static com.assessment.hero.util.HeroUtil.*;
import static com.assessment.hero.util.MissionUtil.MISSION_NAME;
import static com.assessment.hero.util.MissionUtil.buildMissionDAO;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HeroRepositoryTest {

    private HeroRepository heroRepository;
    @Mock
    private HeroCRUDRepository heroCRUDRepository;
    @Mock
    private MissionCRUDRepository missionCRUDRepository;
    @Mock
    private HeroMapper heroMapper;

    private Hero hero;
    private HeroDAO heroDAO;

    @Before
    public void setUp() throws Exception {
        heroRepository = new HeroRepository(heroCRUDRepository, missionCRUDRepository, heroMapper);
        hero = BuildHero();
        heroDAO = BuildHeroDAO();

        when(heroMapper.mapHeroToHeroDAO(eq(hero))).thenReturn(BuildHeroDAO());
        when(heroMapper.mapHeroDAOToHero(eq(heroDAO))).thenReturn(BuildHero());
        when(heroCRUDRepository.existsBySuperHeroName(eq(SUPER_HERO_NAME)))
                .thenReturn(true);
        when(heroCRUDRepository.findBySuperHeroName(eq(SUPER_HERO_NAME)))
                .thenReturn(asList(heroDAO));
    }

    @Test
    public void save_should_call_mapper_to_transfer_data_from_hero_to_hero_DAO() throws Exception {
        //given
        when(heroCRUDRepository.existsBySuperHeroName(eq(SUPER_HERO_NAME)))
                .thenReturn(false);

        //when
        heroRepository.create(hero);

        //then
        Mockito.verify(heroMapper).mapHeroToHeroDAO(eq(hero));
    }

    @Test
    public void save_should_create_new_record_in_database_with_received_hero_info() throws Exception {
        //given
        when(heroCRUDRepository.existsBySuperHeroName(eq(SUPER_HERO_NAME)))
                .thenReturn(false);

        //when
        heroRepository.create(hero);

        //then
        ArgumentCaptor<HeroDAO> argumentCaptor = ArgumentCaptor.forClass(HeroDAO.class);
        Mockito.verify(heroCRUDRepository).save(argumentCaptor.capture());
        HeroDAO captorValue = argumentCaptor.getValue();
        assertThat(captorValue).isEqualToIgnoringGivenFields(hero, "missions");
    }

    @Test
    public void save_should_not_create_new_record_if_superHeroName_already_used() throws Exception {
        //when
        Throwable throwable = catchThrowable(() -> heroRepository.create(hero));

        //then
        assertThat(throwable).hasMessage("Super hero: " + SUPER_HERO_NAME + ", already used");
    }

    @Test
    public void findHeroBySuperHeroName_should_search_for_hero_record_in_database_with_received_hero_name() throws Exception {
        //when
        Hero result = heroRepository.findHeroBySuperHeroName(SUPER_HERO_NAME);

        //then
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(heroCRUDRepository).findBySuperHeroName(argumentCaptor.capture());
        String superHeroName = argumentCaptor.getValue();
        assertThat(superHeroName).isEqualTo(SUPER_HERO_NAME);
    }

    @Test
    public void findHeroBySuperHeroName_should_throw_MissingRecordException_when_received_list_is_null() throws Exception {
        //when
        Throwable throwable = catchThrowable(() -> heroRepository.findHeroBySuperHeroName("not" + SUPER_HERO_NAME));

        //then
        assertThat(throwable).isInstanceOf(MissingRecordException.class)
                .hasMessage("Super hero: " + "not" + SUPER_HERO_NAME + ", doesn't exists");
    }

    @Test
    public void findHeroBySuperHeroName_should_throw_MissingRecordException_when_received_list_is_empty() throws Exception {
        //given
        when(heroCRUDRepository.existsBySuperHeroName(eq(SUPER_HERO_NAME)))
                .thenReturn(false);

        //when
        Throwable throwable = catchThrowable(() -> heroRepository.findHeroBySuperHeroName(SUPER_HERO_NAME));

        //then
        assertThat(throwable).isInstanceOf(MissingRecordException.class)
                .hasMessage("Super hero: " + SUPER_HERO_NAME + ", doesn't exists");
    }

    @Test
    public void findHeroBySuperHeroName_should_call_mapper_to_transfer_data_from_hero_DAO_to_hero() throws Exception {
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
    public void findHeroBySuperHeroName_should_return_hero_received_from_herocrudrepository() throws Exception {
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

    @Test
    public void update_should_throw_MissingRecordException_when_hero_does_not_exists_yet() throws Exception {
        //given
        hero.setSuperHeroName("not" + SUPER_HERO_NAME);

        //when
        Throwable throwable = catchThrowable(() -> heroRepository.update(hero));

        //then
        assertThat(throwable).isInstanceOf(MissingRecordException.class)
                .hasMessage("Super hero: " + "not" + SUPER_HERO_NAME + ", doesn't exists");
    }

    @Test
    public void update_should_call_mapper_to_update_info_and_save_modifications() throws Exception {
        //given
        Hero source = BuildHero();

        //when
        heroRepository.update(source);

        //then
        verify(heroMapper).mapUpdatedInfo(eq(hero), eq(source));
        verify(heroCRUDRepository).save(eq(heroDAO));
    }

    @Test
    public void addMissionToHeroRecord_should_throw_MissingRecordException_when_hero_does_not_exists_yet() throws Exception {
        //given
        hero.setSuperHeroName("not" + SUPER_HERO_NAME);

        //when
        Throwable throwable = catchThrowable(() -> heroRepository.addMissionToHeroRecord(SUPER_HERO_NAME, null));

        //then
        assertThat(throwable).isInstanceOf(MissingRecordException.class)
                .hasMessage("Super hero: " + "not" + SUPER_HERO_NAME + ", doesn't exists");
    }

    @Test
    public void addMissionToHeroRecord_should_throw_MissingRecordException_when_mission_does_not_exists_yet() throws Exception {
        //when
        Throwable throwable = catchThrowable(() -> heroRepository.addMissionToHeroRecord(SUPER_HERO_NAME, MISSION_NAME));

        //then
        assertThat(throwable).isInstanceOf(MissingRecordException.class)
                .hasMessage("Mission: " + MISSION_NAME + ", doesn't exists");
    }

    @Test
    public void addMissionToHeroRecord_should_add_mission_to_hero_DAO_and_save_updated_record() throws Exception {
        //given
        when(missionCRUDRepository.existsByMissionName(eq(MISSION_NAME))).thenReturn(true);
        MissionDAO missionDAO = buildMissionDAO();
        when(missionCRUDRepository.findByMissionName(eq(MISSION_NAME))).thenReturn(asList(buildMissionDAO()));

        //when
        heroRepository.addMissionToHeroRecord(SUPER_HERO_NAME, MISSION_NAME);

        //then
        ArgumentCaptor<HeroDAO> argumentCaptor = ArgumentCaptor.forClass(HeroDAO.class);
        Mockito.verify(heroCRUDRepository).save(argumentCaptor.capture());
        HeroDAO captorValue = argumentCaptor.getValue();
        assertThat(captorValue.getMissions()).containsOnly(missionDAO);
    }
}