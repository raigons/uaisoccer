package com.thoughtworks.uaisoccer.teams;

import com.thoughtworks.uaisoccer.common.InvalidTeamNameException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.constraints.NotNull;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "/test-application-context.xml"
})
@DirtiesContext
public class TeamRepositoryTest {

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