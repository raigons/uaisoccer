package com.thoughtworks.uaisoccer.teams;

import com.thoughtworks.uaisoccer.BaseIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TeamRepositoryTest extends BaseIntegrationTest {

    @Autowired
    private TeamRepository teamRepository;

    @Test
    public void shouldFindTeamByName() throws InvalidTeamNameException {
        String name = "some-name";
        String key = new TeamKeyGenerator().generateKeyFromName(name);

        Team newTeam = new Team();
        newTeam.setName(name);
        newTeam.setKey(key);
        newTeam.setEnabled(true);

        teamRepository.save(newTeam);

        Team team = teamRepository.findByName(newTeam.getName());

        assertThat(team, is(notNullValue()));
        assertThat(team.getId(), is(greaterThan(0L)));
        assertThat(team.getName(), is(newTeam.getName()));
        assertThat(team.getKey(), is(newTeam.getKey()));
    }

    @Test
    public void shouldFindTeamByKey() throws InvalidTeamNameException  {
        String name = "some-name-1";
        String key = new TeamKeyGenerator().generateKeyFromName(name);

        Team newTeam = new Team();
        newTeam.setName(name);
        newTeam.setKey(key);
        newTeam.setEnabled(true);

        teamRepository.save(newTeam);

        Team team = teamRepository.findByName(newTeam.getName());

        assertThat(team, is(notNullValue()));
        assertThat(team.getId(), is(greaterThan(0L)));
        assertThat(team.getName(), is(newTeam.getName()));
        assertThat(team.getKey(), is(newTeam.getKey()));
    }
}
