package com.assessment.hero.controller;

import com.assessment.hero.HeroAssociation;
import com.assessment.hero.model.Hero;
import com.assessment.hero.service.HeroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.assessment.hero.HeroUtil.BuildHero;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = HeroAssociation.class)
@WebMvcTest(HeroController.class)
public class HeroControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private HeroService heroService;

    private Hero hero;

    @Before
    public void setUp() throws Exception {
        hero = BuildHero();
    }

    @Test
    public void test_create_hero_should_return_ok() throws Exception {
        //when
        ResultActions result = mockMvc.perform((post("/hero/create")
                .contentType(MediaType.APPLICATION_JSON))
                .content(objectMapper.writeValueAsString(hero)));

        //then
        result.andExpect(status().isOk());
    }

    @Test
    public void test_create_hero_should_return_bad_request_when_request_body_is_null() throws Exception {
        //given
        hero = null;

        //when
        ResultActions result = mockMvc.perform((post("/hero/create")
                .contentType(MediaType.APPLICATION_JSON))
                .content(objectMapper.writeValueAsString(null)));
        result.andExpect(status().isBadRequest());
    }

    @Test
    public void test_create_hero_should_call_service_to_create_received_hero() throws Exception {
        //when
        ResultActions result = mockMvc.perform((post("/hero/create")
                .contentType(MediaType.APPLICATION_JSON))
                .content(objectMapper.writeValueAsString(hero)));

        //then
        Mockito.verify(heroService).create(eq(hero));
    }
}