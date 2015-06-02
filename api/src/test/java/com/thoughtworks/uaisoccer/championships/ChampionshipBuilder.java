package com.thoughtworks.uaisoccer.championships;

import com.thoughtworks.uaisoccer.teams.Team;

import java.util.ArrayList;

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
            instance.setTeams(new ArrayList<>());
        }
        instance.getTeams().add(team);
        return this;
    }

    public Championship build() {
        return instance;
    }
}