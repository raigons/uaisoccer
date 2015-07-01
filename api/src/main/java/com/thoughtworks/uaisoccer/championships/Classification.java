package com.thoughtworks.uaisoccer.championships;


import com.thoughtworks.uaisoccer.teams.Team;

public class Classification {

    private Team team;
    private int points;
    private int goals;

    public Classification() {
    }

    public Classification(Team team, int points, int goals) {
        this.team = team;
        this.points = points;
        this.goals = goals;
    }

    public Team getTeam() {
        return team;
    }
}
