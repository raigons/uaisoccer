package com.thoughtworks.uaisoccer.championships;

import com.thoughtworks.uaisoccer.BaseIntegrationTest;
import com.thoughtworks.uaisoccer.common.ObjectNotFoundException;
import com.thoughtworks.uaisoccer.teams.Team;
import com.thoughtworks.uaisoccer.teams.TeamBuilder;
import com.thoughtworks.uaisoccer.teams.TeamStore;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hibernate.Query;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class ChampionshipStoreTest extends BaseIntegrationTest{

    @Autowired
    ChampionshipStore store;

    @Autowired
    TeamStore teamStore;

    Championship fixtureChampionship;

    @Before
    public void setUp() {
        fixtureChampionship = new Championship();
        fixtureChampionship.setName("Campeonato Brasileiro");

        Long newChampionshipId = store.create(fixtureChampionship);
        assertThat(newChampionshipId, is(fixtureChampionship.getId()));
    }

    @Test
    public void shouldCreateChampionship() {
        Championship championship = new Championship();
        championship.setName("Brasileirao");

        Long id = store.create(championship);

        assertThat(id, is(notNullValue()));
        assertThat(championship.getId(), is(id));

        Query query = getSession().createQuery("from Championship where id = :id");
        query.setParameter("id", id);

        @SuppressWarnings("unchecked")
        List<Championship> queryResult = (List<Championship>)query.list();
        assertThat(queryResult.contains(championship), is(true));

        Championship createdChampionship = queryResult.get(queryResult.indexOf(championship));
        assertThat(createdChampionship, is(equalTo(championship)));
    }

    @Test
    public void shouldUpdateExistingChampionship() throws ObjectNotFoundException {
        fixtureChampionship.setName("UEFA Champions League");
        store.update(fixtureChampionship);

        Query query = getSession().createQuery("from Championship where id = :id");
        query.setParameter("id", fixtureChampionship.getId());

        @SuppressWarnings("unchecked")
        List<Championship> queryResult = (List<Championship>)query.list();
        MatcherAssert.assertThat(queryResult.contains(fixtureChampionship), Matchers.is(true));

        Championship updatedChampionship = queryResult.get(queryResult.indexOf(fixtureChampionship));
        assertThat(updatedChampionship, is(fixtureChampionship));
    }

    @Test(expected = ObjectNotFoundException.class)
    public void shouldFailToUpdateNonexistentChampionship() throws ObjectNotFoundException {
        Championship nonexistentChampionship = new Championship();
        nonexistentChampionship.setId(999999999L);
        nonexistentChampionship.setName("non-existent");
        store.update(nonexistentChampionship);
    }

    @Test
    public void shouldAssociateTeamsToChampionship() throws ObjectNotFoundException {
        Team america = new TeamBuilder()
                .withName("America")
                .withKey("america")
                .withEnabled(true)
                .build();

        Team cruzeiro = new TeamBuilder()
                .withName("Cruzeiro")
                .withKey("cruzeiro")
                .withEnabled(true)
                .build();

        teamStore.create(america);
        teamStore.create(cruzeiro);

        List<Team> teams = new ArrayList<Team>();
        teams.add(america);
        teams.add(cruzeiro);

        fixtureChampionship.associateTeams(teams);

        store.update(fixtureChampionship);

        Query query = getSession().createSQLQuery("select team_id from championship_team where championship_id = :id");
        query.setParameter("id", fixtureChampionship.getId());

        List queryResult = query.list();
        assertThat(queryResult.size(), is(teams.size()));

    }

}