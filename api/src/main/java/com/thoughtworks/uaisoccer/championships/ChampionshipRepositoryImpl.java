package com.thoughtworks.uaisoccer.championships;

import com.thoughtworks.uaisoccer.common.ObjectNotFoundException;
import com.thoughtworks.uaisoccer.common.ValidationError;
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
    public void associateTeamsToChampionship(List<Team> teams, Championship championship) throws InvalidTeamsException,
            ObjectNotFoundException {
        checkChampionshipExistence(championship);
        validateTeams(teams);

        championship.setTeams(teams);
        repository.save(championship);
    }

    private void checkChampionshipExistence(Championship championship) throws ObjectNotFoundException {
        if (!repository.exists(championship.getId()))
            throw new ObjectNotFoundException(championship.getId());
    }

    private void validateTeams(List<Team> teams) throws InvalidTeamsException {
        List<ValidationError> errors = new ArrayList<>();

        for (Team team : teams) {
            Team persistedTeam = teamRepository.findOne(team.getId());

            if (persistedTeam == null) {
                errors.add(new ValidationError("team.id", String.format("Could not find object with id %d", team.getId())));
            } else if (!persistedTeam.isEnabled()) {
                errors.add(new ValidationError("team.enabled", String.format("Could not assign disabled team with id %d to championship", team.getId())));
            }
        }

        if (!errors.isEmpty())
            throw new InvalidTeamsException(errors);
    }

}

