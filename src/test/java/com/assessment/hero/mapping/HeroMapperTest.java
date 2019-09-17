package com.assessment.hero.mapping;

import com.assessment.hero.model.Hero;
import com.assessment.hero.repository.database.model.HeroDAO;
import org.junit.Before;
import org.junit.Test;

import static com.assessment.hero.HeroUtil.BuildHero;
import static com.assessment.hero.HeroUtil.BuildHeroDAO;
import static org.assertj.core.api.Assertions.assertThat;

public class HeroMapperTest {

    private HeroMapper heroMapper;

    @Before
    public void setUp() throws Exception {
        heroMapper = new HeroMapper();
    }

    @Test
    public void mapHeroToHeroDAO_should_transfer_data_from_hero_to_hero_DAO(){
        //given
        Hero hero = BuildHero();

        //when
        HeroDAO result = heroMapper.mapHeroToHeroDAO(hero);

        //then
        assertThat(result).isEqualToComparingFieldByField(hero);
    }

    @Test
    public void mapHeroToHeroDAO_should_transfer_data_from_hero_DAO_to_hero(){
        //given
        HeroDAO heroDAO = BuildHeroDAO();

        //when
        Hero result = heroMapper.mapHeroDAOToHero(heroDAO);

        //then
        assertThat(result).isEqualToComparingFieldByField(heroDAO);
    }
}