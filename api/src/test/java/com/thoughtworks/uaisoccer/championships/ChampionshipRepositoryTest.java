package com.thoughtworks.uaisoccer.championships;


import com.thoughtworks.uaisoccer.common.InvalidTeamNameException;
import com.thoughtworks.uaisoccer.teams.Team;
import com.thoughtworks.uaisoccer.teams.TeamKeyGenerator;
import com.thoughtworks.uaisoccer.teams.TeamRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "/test-application-context.xml"
})
@DirtiesContext
public class ChampionshipRepositoryTest {
    @Autowired
    private ChampionshipRepository championshipRepository;

    @Test
    public void shouldFindTeamByName() throws InvalidTeamNameException {
        String name = "some-championship";
        Championship newChampionship = new Championship();
        newChampionship.setName(name);

        championshipRepository.save(newChampionship);

        Championship championship = championshipRepository.findByName(newChampionship.getName());

        assertThat(championship, is(notNullValue()));
        assertThat(championship.getId(), is(greaterThan(0L)));
        assertThat(championship.getName(), is(championship.getName()));
    }

}
