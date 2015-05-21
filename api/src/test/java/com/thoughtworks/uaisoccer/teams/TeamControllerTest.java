package com.thoughtworks.uaisoccer.teams;

import com.thoughtworks.uaisoccer.BaseWebIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TeamControllerTest extends BaseWebIntegrationTest {

    @Autowired
    TeamStore store;

    @Test
    public void shouldCreateTeamResource() throws Exception {
        Team team = new TeamBuilder().withName("Clube Atlético Mineiro").build();

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
        Team team = new TeamBuilder()
                .withName("Flamengo")
                .withKey("flamengo")
                .withEnabled(true)
                .build();
        store.create(team);

        mockMvc.perform(get("/teams/" + team.getId())
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").doesNotExist())
                .andExpect(jsonPath("$.id", is(team.getId().intValue())))
                .andExpect(jsonPath("$.name", is(team.getName())))
                .andExpect(jsonPath("$.key", is(team.getKey())))
                .andExpect(jsonPath("$.enabled", is(team.isEnabled())));
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
        Team team = new TeamBuilder()
                .withName("Flamengo")
                .withKey("flamengo")
                .withEnabled(true)
                .build();
        store.create(team);

        team.setName("Goiás");
        team.setKey("goias");

        mockMvc.perform(put("/teams/" + team.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(team))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").doesNotExist())
                .andExpect(jsonPath("$.id", is(team.getId().intValue())))
                .andExpect(jsonPath("$.name", is(team.getName())))
                .andExpect(jsonPath("$.key", is(team.getKey())))
                .andExpect(jsonPath("$.enabled", is(team.isEnabled())));
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

    @Test
    public void shouldReturnHttp409ConflictWhenCreatingTeamWithDuplicateKey() throws Exception {
        Team existingTeam = new TeamBuilder()
                .withName("Flamengo")
                .withKey("flamengo")
                .withEnabled(true)
                .build();
        store.create(existingTeam);

        Team duplicatedTeam = new TeamBuilder().withName(existingTeam.getName()).build();

        mockMvc.perform(post("/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(duplicatedTeam ))
        )
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message", is("Could not execute request because it violates a database constraint")))
                .andExpect(jsonPath("$.id").doesNotExist());
    }

    @Test
    public void shouldListAllTeamResources() throws Exception {
        Team team = new TeamBuilder()
                .withName("Flamengo")
                .withKey("flamengo")
                .withEnabled(true)
                .build();
        store.create(team);

        mockMvc.perform(get("/teams")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(team.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(team.getName())))
                .andExpect(jsonPath("$[0].key", is(team.getKey())))
                .andExpect(jsonPath("$[0].enabled", is(team.isEnabled())));
    }

    @Test
    public void shouldReturn204NoContentAndEmptyBodyIfNoTeamsExist() throws Exception {
        mockMvc.perform(get("/teams")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNoContent())
                .andExpect(content().string(isEmptyOrNullString()));
    }
}
