package com.thoughtworks.uaisoccer.championships;

import com.thoughtworks.uaisoccer.BaseIntegrationTest;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hibernate.Query;
import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ChampionshipStoreTest extends BaseIntegrationTest{

    @Autowired
    ChampionshipStore store;

    private Championship championship;

    @Before
    public void setUp() {
        championship = new Championship();
        championship.setName("Campeonato Brasileiro");

        Long newChampionshipId = store.save(championship);
        assertThat(newChampionshipId, is(championship.getId()));
    }

    @Test
    public void shouldSaveANewChampionship() {
        Championship brazilianLeague = new Championship();
        brazilianLeague.setName("Brasileirao");

        Long championshipId = store.save(brazilianLeague);

        assertThat(championshipId, is(brazilianLeague.getId()));
    }

    @Test
    public void shouldUpdateAnExistingChampionship() {
        championship.setName("UEFA Champions League");
        store.update(championship);

        Query query = getSession().createQuery("from Championship where id = :id");
        query.setParameter("id", championship.getId());
        List<Championship> queryResult = (List<Championship>)query.list();
        MatcherAssert.assertThat(queryResult.contains(championship), Matchers.is(true));

        Championship updatedChampionship = queryResult.get(queryResult.indexOf(championship));
        assertThat(updatedChampionship.getName(), is(championship.getName()));
    }

}