package com.thoughtworks.uaisoccer.championships.matches.generation;

import com.thoughtworks.uaisoccer.championships.Championship;
import com.thoughtworks.uaisoccer.championships.ChampionshipBuilder;
import com.thoughtworks.uaisoccer.championships.matches.Match;
import com.thoughtworks.uaisoccer.teams.Team;
import com.thoughtworks.uaisoccer.teams.TeamBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;

public class AllAgainstAllMatchGeneratorTest {

    AllAgainstAllMatchGenerator matchGenerator;
    private Team teamA;
    private Team teamB;
    private Team teamC;
    private Team teamD;
    private Team teamE;


    @Before
    public void setUp() {
        matchGenerator = new AllAgainstAllMatchGenerator();

        teamA = new TeamBuilder()
                .withId(1L)
                .withName("Atlético Mineiro")
                .withKey("atletico-mineiro")
                .withEnabled(true)
                .build();

        teamB = new TeamBuilder()
                .withId(2L)
                .withName("Cruzeiro")
                .withKey("cruzeiro")
                .withEnabled(true)
                .build();

        teamC = new TeamBuilder()
                .withId(3L)
                .withName("America")
                .withKey("america")
                .withEnabled(true)
                .build();

        teamD = new TeamBuilder()
                .withId(4L)
                .withName("Internacional")
                .withKey("internacional")
                .withEnabled(true)
                .build();

        teamE = new TeamBuilder()
                .withId(5L)
                .withName("Flamengo")
                .withKey("flamengo")
                .withEnabled(true)
                .build();
    }

    @Test(expected = MatchGenerationException.class)
    public void shouldFailToGenerateMatchesWithNullListOfTeams() throws MatchGenerationException {
        Championship championship = new ChampionshipBuilder().withTeams(null).build();
        matchGenerator.generateMatches(championship);
    }

    @Test(expected = MatchGenerationException.class)
    public void shouldFailToGenerateMatchesWithEmptyListOfTeams() throws MatchGenerationException {
        Championship championship = new ChampionshipBuilder().withTeams(new ArrayList<Team>()).build();
        matchGenerator.generateMatches(championship);
    }

    @Test(expected = MatchGenerationException.class)
    public void shouldFailToGenerateMatchesWithLessThanTwoTeams() throws MatchGenerationException {
        Championship championship = new ChampionshipBuilder().withTeam(teamA).build();
        matchGenerator.generateMatches(championship);
    }

    @Test
    public void shouldGenerateOneMatchForChampionshipWithTwoTeams() throws MatchGenerationException {
        Championship championship = new ChampionshipBuilder()
                .withTeam(teamA)
                .withTeam(teamB)
                .build();

        List<Match> matches = matchGenerator.generateMatches(championship);
        assertThat(matches, hasSize(1));
        Match match = matches.get(0);
        assertThat(match.getTeam1(), is(teamA));
        assertThat(match.getTeam2(), is(teamB));
        assertThat(match.getChampionship(), is(championship));
    }

    @Test
    public void shouldGenerateSixMatchesForChampionshipWithFourTeams() throws MatchGenerationException {
        Championship championship = new ChampionshipBuilder()
                .withTeams(Arrays.asList(teamA, teamB, teamC, teamD))
                .build();

        List<Match> matches = matchGenerator.generateMatches(championship);
        assertThat(matches, hasSize(6));

        assertThat(matches.get(0).hasTeam(teamA), is(true));
        assertThat(matches.get(0).hasTeam(teamB), is(true));

        assertThat(matches.get(1).hasTeam(teamA), is(true));
        assertThat(matches.get(1).hasTeam(teamC), is(true));

        assertThat(matches.get(2).hasTeam(teamA), is(true));
        assertThat(matches.get(2).hasTeam(teamD), is(true));

        assertThat(matches.get(3).hasTeam(teamB), is(true));
        assertThat(matches.get(3).hasTeam(teamC), is(true));

        assertThat(matches.get(4).hasTeam(teamB), is(true));
        assertThat(matches.get(4).hasTeam(teamD), is(true));

        assertThat(matches.get(5).hasTeam(teamC), is(true));
        assertThat(matches.get(5).hasTeam(teamD), is(true));
    }

    @Test
    public void shouldGenerateTenMatchesForChampionshipWithFiveTeams() throws MatchGenerationException {
        Championship championship = new ChampionshipBuilder()
                .withTeams(Arrays.asList(teamA, teamB, teamC, teamD, teamE))
                .build();

        List<Match> matches = matchGenerator.generateMatches(championship);
        assertThat(matches, hasSize(10));

        assertThat(matches.get(0).hasTeam(teamA), is(true));
        assertThat(matches.get(0).hasTeam(teamB), is(true));

        assertThat(matches.get(1).hasTeam(teamA), is(true));
        assertThat(matches.get(1).hasTeam(teamC), is(true));

        assertThat(matches.get(2).hasTeam(teamA), is(true));
        assertThat(matches.get(2).hasTeam(teamD), is(true));

        assertThat(matches.get(3).hasTeam(teamA), is(true));
        assertThat(matches.get(3).hasTeam(teamE), is(true));

        assertThat(matches.get(4).hasTeam(teamB), is(true));
        assertThat(matches.get(4).hasTeam(teamC), is(true));

        assertThat(matches.get(5).hasTeam(teamB), is(true));
        assertThat(matches.get(5).hasTeam(teamD), is(true));

        assertThat(matches.get(6).hasTeam(teamB), is(true));
        assertThat(matches.get(6).hasTeam(teamE), is(true));

        assertThat(matches.get(7).hasTeam(teamC), is(true));
        assertThat(matches.get(7).hasTeam(teamD), is(true));

        assertThat(matches.get(8).hasTeam(teamC), is(true));
        assertThat(matches.get(8).hasTeam(teamE), is(true));

        assertThat(matches.get(9).hasTeam(teamD), is(true));
        assertThat(matches.get(9).hasTeam(teamE), is(true));

    }
}