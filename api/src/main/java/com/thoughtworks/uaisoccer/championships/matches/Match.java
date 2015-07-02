package com.thoughtworks.uaisoccer.championships.matches;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thoughtworks.uaisoccer.championships.Championship;
import com.thoughtworks.uaisoccer.teams.Team;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode
public class Match {

    private Team team1;
    private Team team2;
    private List<MatchEvent> events;

    @JsonIgnore
    private Championship championship;

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public Championship getChampionship() {
        return championship;
    }

    public void setChampionship(Championship championship) {
        this.championship = championship;
    }

    public void setEvents(List<MatchEvent> events) { this.events = events; }

    public List<MatchEvent> getEvents() { return events; }

    public boolean hasTeam(Team team) {
        return (team == this.team1 || team == this.team2);
    }

    public boolean hasGoal() {
        return (events.size() > 0);
    }

    public Team getWinner() throws DrawException {
        int goalsTeam1 = getGoals(team1);
        int goalsTeam2 = getGoals(team2);

        if (goalsTeam1 == goalsTeam2) {
            throw new DrawException("It's a Draw!");
        }

        Team winner = (goalsTeam1 > goalsTeam2) ? team1 : team2;
        return winner;
    }

    public int getGoals(Team team) {
        int goals = 0;
        for (MatchEvent event : events) {
            Goal goal;
            if (event instanceof Goal) {
                goal = (Goal) event;
                if ((goal.getPlayer().getTeam().equals(team) && !goal.isAuto()) ||
                   (!goal.getPlayer().getTeam().equals(team) && goal.isAuto())) {
                    goals++;
                }
            }
        }
        return goals;
    }

    public boolean contains(Team team) {
        return (team1.equals(team) || team2.equals(team));
    }
}
