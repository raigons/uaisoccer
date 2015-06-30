package com.thoughtworks.uaisoccer.championships.matches;

import com.thoughtworks.uaisoccer.championships.Championship;
import com.thoughtworks.uaisoccer.teams.Team;

import java.util.List;

public class MatchBuilder {

    Match instance = new Match();

    public MatchBuilder withTeam1(Team team1) {
        instance.setTeam1(team1);
        return this;
    }

    public MatchBuilder withTeam2(Team team2) {
        instance.setTeam2(team2);
        return this;
    }

    public MatchBuilder withChampionship(Championship championship) {
        instance.setChampionship(championship);
        return this;
    }

    public MatchBuilder withEvents(List<MatchEvent> events) {
        instance.setEvents(events);
        return this;
    }

    public Match build() {
        return instance;
    }
}
