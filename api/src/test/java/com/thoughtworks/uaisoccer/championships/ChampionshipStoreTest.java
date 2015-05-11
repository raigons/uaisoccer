package com.thoughtworks.uaisoccer.championships;

import com.thoughtworks.uaisoccer.BaseIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ChampionshipStoreTest extends BaseIntegrationTest{

    @Autowired
    ChampionshipStore store;

    @Test
    public void shouldSaveANewChampionship(){
        Championship brazilianLeague = new Championship();
        brazilianLeague.setName("Brasileirao");

        Long championshipId = store.save(brazilianLeague);

        assertThat(championshipId, is(brazilianLeague.getId()));
    }

    @Test
    public void shouldUpdateAnExistingChampionship() {
        Championship europeanLeague = new Championship();
        europeanLeague.setName("UEFA");

        Long championshipId = store.save(europeanLeague);

        assertThat(championshipId, is(europeanLeague.getId()));

        europeanLeague.setName("UEFA Champions League");

        store.update(europeanLeague);

        Championship updatedEuropeanLeague = store.get(europeanLeague.getId());

        assertThat(updatedEuropeanLeague.getName(), is(europeanLeague.getName()));
    }

}