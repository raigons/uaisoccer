package com.thoughtworks.uaisoccer.championships;

import com.thoughtworks.uaisoccer.BaseIntegrationTest;
import com.thoughtworks.uaisoccer.common.InvalidTeamNameException;
import com.thoughtworks.uaisoccer.common.ObjectNotFoundException;
import com.thoughtworks.uaisoccer.teams.Team;
import com.thoughtworks.uaisoccer.teams.TeamBuilder;
import com.thoughtworks.uaisoccer.teams.TeamRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

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

    @Test
    public void shouldAssociateTeamsToChampionship() throws NonexistentTeamsException, ObjectNotFoundException {
        Championship championship = new Championship();
        championship.setName("Champions League");
        championshipRepository.save(championship);

        Team flamengo = new TeamBuilder()
                .withName("Flamengo")
                .withKey("flamengo")
                .withEnabled(true)
                .build();
        teamRepository.save(flamengo);

        Team cruzeiro = new TeamBuilder()
                .withName("Cruzeiro")
                .withKey("cruzeiro")
                .withEnabled(true)
                .build();
        teamRepository.save(cruzeiro);

        List<Team> teams = new ArrayList<>();
        teams.add(flamengo);
        teams.add(cruzeiro);

        championshipRepository.associateTeamsToChampionship(teams, championship);

        Query query = getEntityManager().createNativeQuery("select team_id from championship_team where championship_id = :id");
        query.setParameter("id", championship.getId());

        List queryResult = query.getResultList();
        assertThat(queryResult.size(), is(teams.size()));
    }

    @Test(expected = NonexistentTeamsException.class)
    public void shouldThrowExceptionWhenAssociatingNonexistentTeamsToChampionship() throws NonexistentTeamsException, ObjectNotFoundException {
        Championship championship = new Championship();
        championship.setName("Champions League");
        championshipRepository.save(championship);

        Team flamengo = new TeamBuilder()
                .withName("Flamengo")
                .withKey("flamengo")
                .withEnabled(true)
                .build();
        flamengo.setId(9999999L);

        List<Team> teams = new ArrayList<>();
        teams.add(flamengo);

        championshipRepository.associateTeamsToChampionship(teams, championship);
    }

    @Test(expected = ObjectNotFoundException.class)
    public void shouldThrowExceptionWhenAssociatingToNonexistentChampionship() throws NonexistentTeamsException,
            ObjectNotFoundException {

        Championship championship = new Championship();
        championship.setName("Champions League");
        championship.setId(9999999L);

        Team flamengo = new TeamBuilder()
                .withName("Flamengo")
                .withKey("flamengo")
                .withEnabled(true)
                .build();
        teamRepository.save(flamengo);

        List<Team> teams = new ArrayList<>();
        teams.add(flamengo);

        championshipRepository.associateTeamsToChampionship(teams, championship);
    }

}
