package com.thoughtworks.uaisoccer.teams;

import com.thoughtworks.uaisoccer.common.InvalidTeamNameException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "/test-application-context.xml"
})
@DirtiesContext
public class TeamRepositoryTest {
    @Autowired
    private TeamRepository teamRepository;

    @Test
    public void shouldCreateANewTeam() throws InvalidTeamNameException {
        Team team = new Team();
        team.setName("name");
        team.setKey(new TeamKeyGenerator().generateKeyFromName(team.getName()));
        team.setEnabled(true);

        teamRepository.save(team);

        team = teamRepository.findByName(team.getName());
        assertThat(team.getId(), is(greaterThan(0L)));
    }
}
