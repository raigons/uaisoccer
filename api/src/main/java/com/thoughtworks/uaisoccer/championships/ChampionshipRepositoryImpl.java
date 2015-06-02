package com.thoughtworks.uaisoccer.championships;

import com.thoughtworks.uaisoccer.common.ObjectNotFoundException;
import com.thoughtworks.uaisoccer.teams.Team;
import com.thoughtworks.uaisoccer.teams.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ChampionshipRepositoryImpl implements ChampionshipRepositoryCustom {

    @Autowired
    private ChampionshipRepository repository;

    @Autowired
    private TeamRepository teamRepository;

    @Override
    public void associateTeamsToChampionship(List<Team> teams, Championship championship) throws NonexistentTeamsException,
            ObjectNotFoundException {
        checkChampioshipExistence(championship);
        checkTeamsExistence(teams);

        championship.setTeams(teams);
        repository.save(championship);
    }

    private void checkChampioshipExistence(Championship championship) throws ObjectNotFoundException {
        if (!repository.exists(championship.getId()))
            throw new ObjectNotFoundException(String.format("Could not find Championship with id %d", championship.getId()));
    }

    private void checkTeamsExistence(List<Team> teams) throws NonexistentTeamsException {
        List<Long> nonexistentIds = new ArrayList<>();
        for (Team team : teams) {
            if (teamRepository.exists(team.getId()))
                continue;
            nonexistentIds.add(team.getId());
        }

        if (!nonexistentIds.isEmpty())
            throw new NonexistentTeamsException(nonexistentIds);
    }
}
