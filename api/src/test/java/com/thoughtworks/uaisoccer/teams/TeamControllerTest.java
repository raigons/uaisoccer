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
    private TeamRepository teamRepository;

    @Test
    public void shouldCreateTeamResource() throws Exception {
        Team team = new TeamBuilder().withName("Clube Atlético Mineiro").build();

        mockMvc.perform(post("/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(team))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.errors").doesNotExist())
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
        teamRepository.save(team);

        mockMvc.perform(get("/teams/" + team.getId())
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errors").doesNotExist())
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
                .andExpect(jsonPath("$.errors[0].message", containsString("Could not find object with id: " + fakeId)))
                .andExpect(jsonPath("$.id").doesNotExist());
    }

    @Test
    public void shouldUpdateExistingResource() throws Exception {
        Team team = new TeamBuilder()
                .withName("Flamengo")
                .withKey("flamengo")
                .withEnabled(true)
                .build();
        teamRepository.save(team);

        team.setName("Goiás");

        mockMvc.perform(put("/teams/" + team.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(team))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errors").doesNotExist())
                .andExpect(jsonPath("$.id", is(team.getId().intValue())))
                .andExpect(jsonPath("$.name", is(team.getName())))
                .andExpect(jsonPath("$.key", is("goias")))
                .andExpect(jsonPath("$.enabled", is(team.isEnabled())));
    }

    @Test
    public void shouldReturnHttp404NotFoundWhenUpdatingNonexistentTeamResource() throws Exception {
        Long fakeId = 999999999L;
        Team fakeTeam = new TeamBuilder().withName("Nonexistent Team").build();
        mockMvc.perform(put("/teams/" + fakeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(fakeTeam))
        )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors[0].message", containsString("Could not find object with id: " + fakeId)));
    }

    @Test
    public void shouldReturnHttp409ConflictWhenCreatingTeamWithDuplicateKey() throws Exception {
        Team existingTeam = new TeamBuilder()
                .withName("Flamengo")
                .withKey("flamengo")
                .withEnabled(true)
                .build();
        teamRepository.save(existingTeam);

        Team duplicatedTeam = new TeamBuilder().withName(existingTeam.getName()).build();

        mockMvc.perform(post("/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(duplicatedTeam))
        )
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errors[0].message", is("Could not execute request because it violates a database constraint")))
                .andExpect(jsonPath("$.id").doesNotExist());
    }

    @Test
    public void shouldReturnHttp409ConflictWhenUpdatingTeamWithDuplicateName() throws Exception {
        Team flamengo = new TeamBuilder().withName("Flamengo").withKey("flamengo").build();
        teamRepository.save(flamengo);

        Team goias = new TeamBuilder().withName("Goias").withKey("goias").build();
        teamRepository.save(goias);



        goias.setName(flamengo.getName());

        mockMvc.perform(put("/teams/" + goias.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(goias))
        )
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errors[0].message", is("Could not execute request because it violates a database constraint")));
    }

    @Test
    public void shouldListAllTeamResources() throws Exception {
        Team team = new TeamBuilder()
                .withName("Flamengo")
                .withKey("flamengo")
                .withEnabled(true)
                .build();
        teamRepository.save(team);

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

    @Test
    public void shouldFindExistingTeamByKey() throws Exception {
        Team teamToBeFound = new TeamBuilder()
                .withName("Cruzeiro")
                .withKey("cruzeiro")
                .withEnabled(true)
                .build();
        teamRepository.save(teamToBeFound);

        Team flamengo = new TeamBuilder()
                .withName("Flamengo")
                .withKey("flamengo")
                .withEnabled(true)
                .build();
        teamRepository.save(flamengo);

        Team ipatinga = new TeamBuilder()
                .withName("Ipatinga")
                .withKey("ipatinga")
                .withEnabled(true)
                .build();
        teamRepository.save(ipatinga);

        mockMvc.perform(get("/teams?key=" + teamToBeFound.getKey())
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(teamToBeFound.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(teamToBeFound.getName())))
                .andExpect(jsonPath("$[0].key", is(teamToBeFound.getKey())))
                .andExpect(jsonPath("$[0].enabled", is(teamToBeFound.isEnabled())));
    }

    @Test
    public void shouldReturn204NoContentAndEmptyBodyWhenFindingNonexistentKey() throws Exception {
        mockMvc.perform(get("/teams?key=nonexistent-key")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNoContent())
                .andExpect(content().string(isEmptyOrNullString()));
    }

    @Test
    public void shouldReturn400BadRequestForInvalidAttributes() throws Exception {
        String invalidContent = "{\"name\":  \"team\", \"fruit\": \"pineapple\"}";

        mockMvc.perform(post("/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidContent)
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().string(isEmptyOrNullString()));
    }

    @Test
    public void shouldReturn400BadRequestWhenUpdatingWithInvalidAttributes() throws Exception {
        Team team = new TeamBuilder()
                        .withName("Ipatinga")
                        .withKey("ipatinga")
                        .withEnabled(true)
                        .build();
        teamRepository.save(team);

        String invalidContent = "{\"name\":  \"team\", \"fruit\": \"pineapple\"}";
        mockMvc.perform(put("/teams/" + 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidContent)
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().string(isEmptyOrNullString()));
    }

    @Test
    public void shouldReturn400BadRequestForTeamWithNullName() throws Exception {
        Team teamWithoutName = new Team();
        mockMvc.perform(post("/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(teamWithoutName))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field", is("name")))
                .andExpect(jsonPath("$.errors[0].message", is("may not be empty")));
    }

    @Test
    public void shouldReturn400BadRequestWhenUpdatingTeamWithNullName() throws Exception {
        Team team = new TeamBuilder()
                        .withName("Ipatinga")
                        .withKey("ipatinga")
                        .withEnabled(true)
                        .build();
        teamRepository.save(team);

        Team teamWithoutName = new Team();
        mockMvc.perform(put("/teams/" + team.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(teamWithoutName))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field", is("name")))
                .andExpect(jsonPath("$.errors[0].message", is("may not be empty")));
    }

    @Test
    public void shouldReturn400BadRequestForTeamWithBlankName() throws Exception {
        Team teamWithEmptyName = new Team();
        teamWithEmptyName.setName("");
        mockMvc.perform(post("/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(teamWithEmptyName))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field", is("name")))
                .andExpect(jsonPath("$.errors[0].message", is("may not be empty")));
    }

    @Test
    public void shouldReturn400BadRequestWhenUpdatingTeamWithBlankName() throws Exception {
        Team team = new TeamBuilder()
                        .withName("Ipatinga")
                        .withKey("ipatinga")
                        .withEnabled(true)
                        .build();
        teamRepository.save(team);

        Team teamWithEmptyName = new Team();
        teamWithEmptyName.setName("");
        mockMvc.perform(put("/teams/" + team.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(teamWithEmptyName))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field", is("name")))
                .andExpect(jsonPath("$.errors[0].message", is("may not be empty")));
    }

    @Test
    public void shouldReturn400BadRequestForTeamWithWhitespaceName () throws Exception {
        Team teamWithEmptyName = new Team();
        teamWithEmptyName.setName("          ");
        mockMvc.perform(post("/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(teamWithEmptyName))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field", is("name")))
                .andExpect(jsonPath("$.errors[0].message", is("may not be empty")));
    }

    @Test
    public void shouldReturn400BadRequestWhenUpdatingTeamWithWhitespaceName () throws Exception {
        Team team = new TeamBuilder()
                        .withName("Ipatinga")
                        .withKey("ipatinga")
                        .withEnabled(true)
                        .build();
        teamRepository.save(team);

        Team teamWithEmptyName = new Team();
        teamWithEmptyName.setName("          ");
        mockMvc.perform(put("/teams/" + team.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(teamWithEmptyName))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field", is("name")))
                .andExpect(jsonPath("$.errors[0].message", is("may not be empty")));
    }

    @Test
    public void shouldReturn400BadRequestWhenCreatingTeamWithNumericName() throws Exception {
        String invalidContent = "{\"name\": 123456}";

        mockMvc.perform(post("/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidContent)
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("cannot be numeric")));
    }

    @Test
    public void shouldReturn400BadRequestWhenUpdatingTeamWithNumericName() throws Exception {
        Team team = new TeamBuilder()
                .withName("Flamengo")
                .withKey("flamengo")
                .withEnabled(true)
                .build();
        teamRepository.save(team);

        String teamWithNumericName = "{\"name\": 123456}";

        mockMvc.perform(put("/teams/" + team.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(teamWithNumericName)
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("cannot be numeric")));
    }

    @Test
    public void shouldReturn400BadRequestForTeamWithInvalidCharacters () throws Exception {
        Team teamWithEmptyName = new Team();
        teamWithEmptyName.setName("-!@#$%^&*+=[]{}()<>\\|/?'\"`~;:.,");
        mockMvc.perform(post("/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(teamWithEmptyName))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message", containsString("does not contain any valid characters to create a key")));
    }

    @Test
    public void shouldReturn400BadRequestWhenUpdatingTeamNameWithInvalidChars() throws Exception {
        Team team = new TeamBuilder()
                        .withName("Ipatinga")
                        .withKey("ipatinga")
                        .withEnabled(true)
                        .build();
        teamRepository.save(team);

        Team teamWithInvalidName = new Team();
        teamWithInvalidName.setName("-!@#$%^&*+=[]{}()<>\\|/?'\"`~;:.,");
        mockMvc.perform(put("/teams/" + team.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(teamWithInvalidName))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message", containsString("does not contain any valid characters to create a key")));
    }

}
