package com.thoughtworks.uaisoccer.teams;

import com.thoughtworks.uaisoccer.BaseWebIntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TeamControllerTest extends BaseWebIntegrationTest {

    @Autowired
    TeamStore store;

    Team fixtureTeam;

    @Before
    public void setUp() {
        fixtureTeam = new Team();
        fixtureTeam.setName("Flamengo");
        fixtureTeam.setKey("flamengo");
        fixtureTeam.setEnabled(true);

        store.create(fixtureTeam);
    }

    @Test
    public void shouldCreateTeamResource() throws Exception {
        Team team = new Team();
        team.setName("Clube Atlético Mineiro");

        mockMvc.perform(post("/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(team))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").doesNotExist())
                .andExpect(jsonPath("$.id", is(greaterThan(0))))
                .andExpect(jsonPath("$.name", is(team.getName())))
                .andExpect(jsonPath("$.key", is("clube-atletico-mineiro")))
                .andExpect(jsonPath("$.enabled", is(team.isEnabled())));
    }

    @Test
    public void shouldReadTeamResource() throws Exception {
        mockMvc.perform(get("/teams/" + fixtureTeam.getId())
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").doesNotExist())
                .andExpect(jsonPath("$.id", is(fixtureTeam.getId().intValue())))
                .andExpect(jsonPath("$.name", is(fixtureTeam.getName())))
                .andExpect(jsonPath("$.key", is(fixtureTeam.getKey())))
                .andExpect(jsonPath("$.enabled", is(fixtureTeam.isEnabled())));
    }

    @Test
    public void shouldReturnHttp404NotFoundWhenReadingNonexistentTeamResource() throws Exception {
        Long fakeId = 999999999L;

        mockMvc.perform(get("/teams/" + fakeId)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", containsString("Could not find object")))
                .andExpect(jsonPath("$.id").doesNotExist());
    }

    @Test
    public void shouldUpdateExistingResource() throws Exception {
        fixtureTeam.setName("Goiás");
        fixtureTeam.setKey("goias");

        mockMvc.perform(put("/teams/" + fixtureTeam.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(fixtureTeam))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").doesNotExist())
                .andExpect(jsonPath("$.id", is(fixtureTeam.getId().intValue())))
                .andExpect(jsonPath("$.name", is(fixtureTeam.getName())))
                .andExpect(jsonPath("$.key", is(fixtureTeam.getKey())))
                .andExpect(jsonPath("$.enabled", is(fixtureTeam.isEnabled())));
    }

    @Test
    public void shouldReturnHttp404NotFoundWhenUpdatingNonexistentTeamResource() throws Exception {
        Long fakeId = 999999999L;
        mockMvc.perform(put("/teams/" + fakeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(new Team()))
        )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", containsString("Could not find object")))
                .andExpect(jsonPath("$.id").doesNotExist());
    }
}
