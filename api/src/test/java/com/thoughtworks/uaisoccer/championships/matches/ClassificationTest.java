package com.thoughtworks.uaisoccer.championships.matches;


import com.thoughtworks.uaisoccer.championships.Championship;
import com.thoughtworks.uaisoccer.championships.matches.generation.MatchGenerationException;
import com.thoughtworks.uaisoccer.teams.Player;
import com.thoughtworks.uaisoccer.teams.PlayerBuilder;
import com.thoughtworks.uaisoccer.teams.Team;
import com.thoughtworks.uaisoccer.teams.TeamBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ClassificationTest {

    Championship championship;
    List<Team> teams;
    Team internacional, gremio;
    Match grenal;
    List<Match> matches;
    List<MatchEvent> events;
    MatchEvent goalInternacional;
    Player dalessandro;

    @Before
    public void setUp() {
        championship = new Championship();
        championship.setName("Brasileirão");
        championship.setId(1L);

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

        events = new ArrayList<>();
        events.add(goalInternacional);

        grenal = new MatchBuilder()
                .withTeam1(internacional)
                .withTeam2(gremio)
                .withEvents(events)
                .build();

        matches = new ArrayList<>();
        matches.add(grenal);
    }
/*
    @Test
    public void internacionalShouldBeTheChampion() throws MatchGenerationException {
        championship.setTeams(teams);
        championship.setMatches(matches);
        assertThat(championship.getChampion(), is(internacional));
    }
*/

}
