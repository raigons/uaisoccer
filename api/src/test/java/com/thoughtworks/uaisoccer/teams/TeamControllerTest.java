package com.thoughtworks.uaisoccer.teams;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.uaisoccer.BaseWebIntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TeamControllerTest extends BaseWebIntegrationTest {

    @Autowired
    TeamStore store;

    Team fixtureTeam;

    public static String convertObjectToJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(object);
    }

    @Before
    public void setUp() {
        fixtureTeam = new Team();
        fixtureTeam.setName("Flamengo");
        fixtureTeam.setKey("flamengo");

        store.create(fixtureTeam);
    }

    @Test
    public void shouldCreateTeamResource() throws Exception {
        Team team = new Team();
        team.setName("Cruzeiro Esporte Clube");
        team.setKey("cruzeiro");

        mockMvc.perform(post("/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(team))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.message", is(nullValue())))
                .andExpect(jsonPath("$.value.id", is(greaterThan(0))))
                .andExpect(jsonPath("$.value.name", is(team.getName())))
                .andExpect(jsonPath("$.value.key", is(team.getKey())));
    }

    @Test
    public void shouldReadTeamResource() throws Exception {
        mockMvc.perform(get("/teams/" + fixtureTeam.getId())
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.message", is(nullValue())))
                .andExpect(jsonPath("$.value.id", is(fixtureTeam.getId().intValue())))
                .andExpect(jsonPath("$.value.name", is(fixtureTeam.getName())))
                .andExpect(jsonPath("$.value.key", is(fixtureTeam.getKey())));
    }

    @Test
    public void shouldReturnHttp404NotFoundIfTeamResourceDoesNotExist() throws Exception {
        Long fakeId = 999999999L;

        mockMvc.perform(get("/teams/" + fakeId)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.message", containsString("Could not find object")))
                .andExpect(jsonPath("$.value", is(nullValue())));
    }
}
