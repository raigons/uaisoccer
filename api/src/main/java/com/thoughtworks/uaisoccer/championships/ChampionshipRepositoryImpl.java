package com.thoughtworks.uaisoccer.championships;

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
    public void associateTeamsToChampionship(List<Team> teams, Championship championship) throws NonexistentTeamsException {
        checkTeamsExistence(teams);

        championship.setTeams(teams);
        repository.save(championship);
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
