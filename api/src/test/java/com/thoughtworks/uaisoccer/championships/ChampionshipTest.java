package com.thoughtworks.uaisoccer.championships;

import com.thoughtworks.uaisoccer.championships.matches.GoalBuilder;
import com.thoughtworks.uaisoccer.championships.matches.Match;
import com.thoughtworks.uaisoccer.championships.matches.MatchBuilder;
import com.thoughtworks.uaisoccer.championships.matches.MatchEvent;
import com.thoughtworks.uaisoccer.championships.matches.generation.AllAgainstAllMatchGenerator;
import com.thoughtworks.uaisoccer.championships.matches.generation.MatchGenerationException;
import com.thoughtworks.uaisoccer.teams.Player;
import com.thoughtworks.uaisoccer.teams.PlayerBuilder;
import com.thoughtworks.uaisoccer.teams.Team;
import com.thoughtworks.uaisoccer.teams.TeamBuilder;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

public class ChampionshipTest {

    Championship championship;
    Championship slightlyDifferentChampionship;
    Championship veryDifferentChampionship;
    Championship identicalChampionship;

    @Before
    public void setUp() {
        championship = new Championship();
        championship.setName("Brasileirão");
        championship.setId(1L);

        identicalChampionship = new Championship();
        identicalChampionship.setName("Brasileirão");
        identicalChampionship.setId(1L);

        slightlyDifferentChampionship = new Championship();
        identicalChampionship.setName("Brasileirão");
        slightlyDifferentChampionship.setId(5L);

        veryDifferentChampionship = new Championship();
        veryDifferentChampionship.setName("Libertadores");
        veryDifferentChampionship.setId(9L);
    }

    @SuppressWarnings("ObjectEqualsNull")
    @Test
    public void shouldNotBeEqualsToNull() {
        assertThat(championship.equals(null), is(false));
    }

    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Test
    public void shouldNotBeEqualsToDifferentClassType() {
        String anotherType = "a string";
        assertThat(championship.equals(anotherType), is(false));
    }

    @Test
    public void shouldNotBeEqualsToSlightlyDifferentChampionship() {
        assertThat(championship.equals(slightlyDifferentChampionship), is(false));
    }

    @Test
    public void shouldNotHaveSameHashCodeThatSlightlyDifferentChampionship() {
        assertThat(championship.hashCode(), not(equalTo(slightlyDifferentChampionship.hashCode())));
    }

    @Test
    public void shouldNotBeEqualsToVeryDifferentChampionship() {
        assertThat(championship.equals(veryDifferentChampionship), is(false));
    }

    @Test
    public void shouldNotHaveSameHashCodeThatVeryDifferentChampionship() {
        assertThat(championship.hashCode(), not(equalTo(veryDifferentChampionship.hashCode())));
    }

    @Test
    public void shouldBeEqualsToIdenticalChampionship() {
        assertThat(championship.equals(identicalChampionship), is(true));
    }

    @Test
    public void shouldHaveSameHashCodeToIdenticalChampionship() {
        assertThat(championship.hashCode(), is(Matchers.equalTo(identicalChampionship.hashCode())));
    }

    @Test
    public void internacionalShouldBeTheChampion() throws MatchGenerationException {
        createTeams();
        championship.setTeams(teams);
        championship.setMatches(matches);
        assertThat(championship.getChampion(), is(internacional));
    }

    List<Team> teams;
    Team internacional, gremio;
    Match grenal;
    List<Match> matches;
    List<MatchEvent> events;
    MatchEvent goalInternacional;
    Player dalessandro;

    private void createTeams() {
        teams = new ArrayList<Team>();

        gremio = new TeamBuilder()
                .withId(2L)
                .withName("Gremio")
                .withKey("gremio")
                .withEnabled(true)
                .build();

        internacional = new TeamBuilder()
                .withId(1L)
                .withName("Internacional")
                .withKey("internacional")
                .withEnabled(true)
                .build();

        teams.add(gremio);
        teams.add(internacional);

        dalessandro = new PlayerBuilder()
                .withTeam(internacional)
                .build();

        goalInternacional = new GoalBuilder()
                .withPlayer(dalessandro)
                .build();

        grenal = new MatchBuilder()
                .withTeam1(internacional)
                .withTeam2(gremio)
                .withEvents(events)
                .build();

        matches = new ArrayList<Match>();
        matches.add(grenal);
    }


}