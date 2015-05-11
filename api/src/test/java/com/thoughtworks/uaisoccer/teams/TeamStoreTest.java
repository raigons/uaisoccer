package com.thoughtworks.uaisoccer.teams;

import com.thoughtworks.uaisoccer.BaseIntegrationTest;
import com.thoughtworks.uaisoccer.common.ObjectNotFoundException;
import org.hibernate.Query;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;


public class TeamStoreTest extends BaseIntegrationTest {

    @Autowired
    private TeamStore store;

    private Team fixtureTeam;

    @Before
    public void setUp() throws Exception {
        fixtureTeam = new Team("América", "america");
        store.create(fixtureTeam);
    }

    @Test
    public void shouldReadTeam() throws ObjectNotFoundException {
        Team readTeam = store.read(fixtureTeam.getId());
        assertThat(readTeam, is(equalTo(fixtureTeam)));
    }

    @Test(expected = ObjectNotFoundException.class)
    public void shouldFailIfTeamDoesNotExist() throws ObjectNotFoundException {
        store.read(999999999L);
    }

    @Test
    public void shouldCreateTeam() {
        Team team = new Team("Cruzeiro Esporte Clube", "cruzeiro");
        Long id = store.create(team);

        assertThat(id, is(notNullValue()));
        assertThat(team.getId(), is(id));

        Query query = getSession().createQuery("from Team where id = :id");
        query.setParameter("id", id);

        @SuppressWarnings("unchecked")
        List<Team> queryResult = (List<Team>)query.list();
        assertThat(queryResult.contains(team), is(true));

        Team createdTeam = queryResult.get(queryResult.indexOf(team));
        assertThat(createdTeam, is(equalTo(team)));
    }
}