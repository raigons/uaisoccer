package com.thoughtworks.uaisoccer.championships;

import com.thoughtworks.uaisoccer.teams.Team;
import com.thoughtworks.uaisoccer.teams.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ChampionshipRepositoryImpl implements ChampionshipRepositoryCustom {

    @Autowired
    private ChampionshipRepository repository;

    @Autowired
    private TeamRepository teamRepository;

    @Override
    public void associateTeamsToChampionship(List<Team> teams, Championship championship) {
        championship.setTeams(teams);
        repository.save(championship);
    }
}
