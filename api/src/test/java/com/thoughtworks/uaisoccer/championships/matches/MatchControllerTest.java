package com.thoughtworks.uaisoccer.championships.matches;

import com.thoughtworks.uaisoccer.BaseWebIntegrationTest;
import com.thoughtworks.uaisoccer.championships.Championship;
import com.thoughtworks.uaisoccer.championships.ChampionshipBuilder;
import com.thoughtworks.uaisoccer.championships.ChampionshipRepository;
import com.thoughtworks.uaisoccer.teams.Team;
import com.thoughtworks.uaisoccer.teams.TeamBuilder;
import com.thoughtworks.uaisoccer.teams.TeamRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MatchControllerTest extends BaseWebIntegrationTest {

    @Autowired
    private ChampionshipRepository championshipRepository;

    @Autowired
    private TeamRepository teamRepository;

    private Championship championship;
    private Team teamA;
    private Team teamB;
    private Team teamC;

    @Before
    public void setUp() {

        teamA = new TeamBuilder()
                .withName("Atlético Mineiro")
                .withKey("atletico-mineiro")
                .withEnabled(true)
                .build();
        teamRepository.save(teamA);

        teamB = new TeamBuilder()
                .withName("Cruzeiro")
                .withKey("cruzeiro")
                .withEnabled(true)
                .build();
        teamRepository.save(teamB);

        teamC = new TeamBuilder()
                .withName("America")
                .withKey("america")
                .withEnabled(true)
                .build();
        teamRepository.save(teamC);

        championship = new ChampionshipBuilder()
                .withName("Brasileirão")
                .withTeams(Arrays.asList(teamA, teamB, teamC))
                .build();
        championshipRepository.save(championship);
    }

    @Test
    public void shouldGetAllAgainstAllMatchesBetweenTeamsInAChampionship() throws Exception {
        mockMvc.perform(get("/championships/" + championship.getId() + "/matches")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].team1.id", is(teamA.getId().intValue())))
                .andExpect(jsonPath("$[0].team2.id", is(teamB.getId().intValue())))
                .andExpect(jsonPath("$[1].team1.id", is(teamA.getId().intValue())))
                .andExpect(jsonPath("$[1].team2.id", is(teamC.getId().intValue())))
                .andExpect(jsonPath("$[2].team1.id", is(teamB.getId().intValue())))
                .andExpect(jsonPath("$[2].team2.id", is(teamC.getId().intValue())));
    }

    @Test
    public void shouldFailWith404NotFoundIfChampionshipIdDoesNotExist() throws Exception {
        Long fakeId = 9999999999L;

        mockMvc.perform(get("/championships/" + fakeId + "/matches")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors[0].message", containsString("Could not find object")));
    }

    @Test
    public void shouldFailWith400BadRequestIfAChampionshipHasLessThanTwoTeams() throws Exception {
        Championship championshipWithOneTeam = new ChampionshipBuilder().withName("One team championship")
                .withTeam(teamA).build();
        championshipRepository.save(championshipWithOneTeam);

        mockMvc.perform(get("/championships/" + championshipWithOneTeam.getId() + "/matches")
                .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message", containsString("Can not generate matches with less than two teams")));

    }
}