package com.thoughtworks.uaisoccer.championships.matches;

import com.thoughtworks.uaisoccer.championships.Championship;
import com.thoughtworks.uaisoccer.championships.ChampionshipBuilder;
import com.thoughtworks.uaisoccer.teams.Player;
import com.thoughtworks.uaisoccer.teams.PlayerBuilder;
import com.thoughtworks.uaisoccer.teams.Team;
import com.thoughtworks.uaisoccer.teams.TeamBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertTrue;

public class MatchTest {

    Match match;
    Match slightlyDifferentMatch;
    Match veryDifferentMatch;
    Match identicalMatch;
    List<MatchEvent> events;
    MatchEvent goal;
    Player moreno;

    @Before
    public void setUp() {

        Team atleticoMineiro = new TeamBuilder()
                .withId(10L)
                .withName("Atlético Mineiro")
                .withKey("atletico-mineiro")
                .withEnabled(true)
                .build();
        Team cruzeiro = new TeamBuilder()
                .withId(11L)
                .withName("Cruzeiro")
                .withKey("cruzeiro")
                .withEnabled(true)
                .build();

        Team america = new TeamBuilder()
                .withId(12L)
                .withName("America")
                .withKey("america")
                .withEnabled(true)
                .build();

        Team internacional = new TeamBuilder()
                .withId(13L)
                .withName("Internacional")
                .withKey("internacional")
                .withEnabled(true)
                .build();

        Championship campeonatoBrasileiro = new ChampionshipBuilder()
                .withId(1L)
                .withName("Campeonato Brasileiro")
                .withTeam(atleticoMineiro)
                .withTeam(cruzeiro)
                .withTeam(america)
                .withTeam(internacional)
                .build();

        Championship libertadores = new ChampionshipBuilder()
                .withId(2L)
                .withName("Libertadores")
                .withTeam(america)
                .withTeam(internacional)
                .build();

        moreno = new PlayerBuilder()
                .withId(1L)
                .withName("Marcelo Moreno")
                .withNickname("Moreno")
                .withTeam(cruzeiro)
                .build();

        goal = new GoalEventBuilder()
                .withTime(1)
                .withPlayer(moreno)
                .setAuto(false)
                .build();

        events = new ArrayList<>();
        events.add(goal);

        match = new MatchBuilder()
                .withTeam1(atleticoMineiro)
                .withTeam2(cruzeiro)
                .withChampionship(campeonatoBrasileiro)
                .withEvents(events)
                .build();

        identicalMatch = new MatchBuilder()
                .withTeam1(atleticoMineiro)
                .withTeam2(cruzeiro)
                .withChampionship(campeonatoBrasileiro)
                .withEvents(events)
                .build();

        slightlyDifferentMatch = new MatchBuilder()
                .withTeam1(america)
                .withTeam2(cruzeiro)
                .withChampionship(campeonatoBrasileiro)
                .build();

        veryDifferentMatch = new MatchBuilder()
                .withTeam1(america)
                .withTeam2(internacional)
                .withChampionship(libertadores)
                .build();
    }

    @Test
    public void shouldNotBeEqualsToNull() {
        assertThat(match, is(notNullValue()));
    }

    @Test
    public void shouldNotBeEqualsToDifferentClass() {
        assertThat(match, is(not(equalTo(new Match()))));
    }

    @Test
    public void shouldNotBeEqualsToSlightlyDifferentMatch() {
        assertThat(match, is(not(equalTo(slightlyDifferentMatch))));
    }

    @Test
    public void shouldNotBeEqualsToVeryDifferentMatch() {
        assertThat(match, is(not(equalTo(veryDifferentMatch))));
    }

    @Test
    public void shouldBeEqualsToIdenticalMatch() {
        assertThat(match, is(equalTo(identicalMatch)));
    }

    @Test
    public void hashCodeShouldBeEqualsToIdenticalMatchHashCode() {
        assertThat(match.hashCode(), is(equalTo(identicalMatch.hashCode())));
    }

    @Test
    public void hashCodeShouldBeDifferentToSlightlyDifferentMatchHashCode() {
        assertThat(match.hashCode(), is(not(equalTo(slightlyDifferentMatch.hashCode()))));
    }

    @Test
    public void hashCodeShouldBeDifferentToVeryDifferentMatchHashCode() {
        assertThat(match.hashCode(), is(not(equalTo(veryDifferentMatch.hashCode()))));
    }

    @Test
    public void hasAGoalEvent() {
        assertTrue(match.hasGoal());
    }

    @Test
    public void team1ShouldBeWinner() throws Exception{
        assertThat(match.getWinner(), is(equalTo(match.getTeam2())));
    }

}
