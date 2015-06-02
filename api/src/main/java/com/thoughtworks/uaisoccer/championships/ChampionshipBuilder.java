package com.thoughtworks.uaisoccer.championships;

import com.thoughtworks.uaisoccer.teams.Team;

import java.util.ArrayList;
import java.util.List;

public class ChampionshipBuilder {

    private Championship instance = new Championship();

    public ChampionshipBuilder withId(Long id) {
        instance.setId(id);
        return this;
    }

    public ChampionshipBuilder withName(String name) {
        instance.setName(name);
        return this;
    }

    public ChampionshipBuilder withTeam(Team team) {
        if (instance.getTeams() == null) {
            instance.setTeams(new ArrayList<Team>());
        }
        instance.getTeams().add(team);
        return this;
    }

    public ChampionshipBuilder withTeams(List<Team> teams) {
        instance.setTeams(teams);
        return this;
    }

    public Championship build() {
        return instance;
    }
}