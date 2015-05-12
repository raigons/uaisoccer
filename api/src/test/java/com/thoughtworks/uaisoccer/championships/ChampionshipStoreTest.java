package com.thoughtworks.uaisoccer.championships;

import com.thoughtworks.uaisoccer.BaseIntegrationTest;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hibernate.Query;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class ChampionshipStoreTest extends BaseIntegrationTest{

    @Autowired
    ChampionshipStore store;

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
    public void shouldUpdateExistingChampionship() {
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

}