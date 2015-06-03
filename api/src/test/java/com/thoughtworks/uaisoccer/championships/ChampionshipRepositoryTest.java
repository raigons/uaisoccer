package com.thoughtworks.uaisoccer.championships;

import com.thoughtworks.uaisoccer.BaseIntegrationTest;
import com.thoughtworks.uaisoccer.teams.InvalidTeamNameException;
import com.thoughtworks.uaisoccer.teams.TeamRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ChampionshipRepositoryTest extends BaseIntegrationTest {

    @Autowired
    private ChampionshipRepository championshipRepository;

    @Autowired
    private TeamRepository teamRepository;

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
