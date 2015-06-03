package com.thoughtworks.uaisoccer.championships;

import com.thoughtworks.uaisoccer.common.ObjectNotFoundException;
import com.thoughtworks.uaisoccer.teams.Team;

import java.util.List;

public interface ChampionshipRepositoryCustom {

    void associateTeamsToChampionship(List<Team> teams, Championship championship) throws ObjectNotFoundException, InvalidTeamsException;
}
