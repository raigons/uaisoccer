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

    private Championship championship;

    @Before
    public void setUp() {
        championship = new Championship();
        championship.setName("Campeonato Brasileiro");

        Long newChampionshipId = store.create(championship);
        assertThat(newChampionshipId, is(championship.getId()));
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
        championship.setName("UEFA Champions League");
        store.update(championship);

        Query query = getSession().createQuery("from Championship where id = :id");
        query.setParameter("id", championship.getId());

        @SuppressWarnings("unchecked")
        List<Championship> queryResult = (List<Championship>)query.list();
        MatcherAssert.assertThat(queryResult.contains(championship), Matchers.is(true));

        Championship updatedChampionship = queryResult.get(queryResult.indexOf(championship));
        assertThat(updatedChampionship.getName(), is(championship.getName()));
    }

}