package com.thoughtworks.uaisoccer.championships;

import com.thoughtworks.uaisoccer.common.ObjectNotFoundException;
import com.thoughtworks.uaisoccer.teams.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ChampionshipRepository extends JpaRepository<Championship, Long>, ChampionshipRepositoryCustom {
    Championship findByName(String name);
    void associateTeamsToChampionship(List<Team> teams, Championship championship) throws ObjectNotFoundException,
            NonexistentTeamsException;
}
