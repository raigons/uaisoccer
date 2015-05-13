package com.thoughtworks.uaisoccer.championships;

import com.thoughtworks.uaisoccer.BaseWebIntegrationTest;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ChampionshipControllerTest extends BaseWebIntegrationTest {

    @Autowired
    ChampionshipStore store;

    Championship fixtureChampionship;

    @Before
    public void setUp() {
        fixtureChampionship = new Championship();
        fixtureChampionship.setName("Libertadores");
        store.create(fixtureChampionship);
    }

    @Test
    public void shouldCreateChampionshipResource() throws Exception {
        Championship championship = new Championship();
        championship.setName("Brasileirão");

        mockMvc.perform(post("/championships")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(convertObjectToJson(championship))
                )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.success", Matchers.is(true)))
            .andExpect(jsonPath("$.message", Matchers.is(nullValue())))
            .andExpect(jsonPath("$.value.id", Matchers.is(greaterThan(0))))
            .andExpect(jsonPath("$.value.name", Matchers.is(championship.getName())));
    }

    @Test
    public void shouldUpdateExistingChampionshipResource() throws Exception {
        fixtureChampionship.setName("Campeonato Mineiro");

        mockMvc.perform(put("/championships/" + fixtureChampionship.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(convertObjectToJson(fixtureChampionship))
                )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success", Matchers.is(true)))
            .andExpect(jsonPath("$.message", Matchers.is(nullValue())))
            .andExpect(jsonPath("$.value.id", Matchers.is(fixtureChampionship.getId().intValue())))
            .andExpect(jsonPath("$.value.name", Matchers.is(fixtureChampionship.getName())));
    }

    @Test
    public void shouldFailWithHttp404NotFoundWhenUpdatingNonexistentChampionship() throws Exception {
        Long fakeId = 999999999L;

        mockMvc.perform(put("/championships/" + fakeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(fixtureChampionship))
        )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success", Matchers.is(false)))
                .andExpect(jsonPath("$.message", containsString("Could not find object")))
                .andExpect(jsonPath("$.value", is(nullValue())));
    }

}