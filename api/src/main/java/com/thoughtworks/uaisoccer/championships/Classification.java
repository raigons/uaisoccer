package com.thoughtworks.uaisoccer.championships;


import com.thoughtworks.uaisoccer.teams.Team;

public class Classification implements Comparable{

    private Team team;
    private int points;
    private int goals;


    public Classification(Team team, int points, int goals) {
        this.team = team;
        this.points = points;
        this.goals = goals;
    }

    public Team getTeam() {
        return team;
    }

    public int getPoints() {
        return points;
    }

    public int getGoals() { return goals; }

    @Override
    public int compareTo(Object o) {
        int comparator = 0;

        if (o instanceof Classification) {
            Classification c = (Classification)o;
            if (this.points != c.points) {
                comparator = (this.points < c.points) ? 1 : -1;
            }
        }

        return comparator;
    }


}
