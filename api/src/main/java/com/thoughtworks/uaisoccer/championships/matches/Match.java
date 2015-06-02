package com.thoughtworks.uaisoccer.championships.matches;

import com.thoughtworks.uaisoccer.championships.Championship;
import com.thoughtworks.uaisoccer.common.BaseModel;
import com.thoughtworks.uaisoccer.teams.Team;

import java.util.Objects;

public class Match extends BaseModel {

    private Team team1;
    private Team team2;
    private Championship championship;

    @Override
    protected boolean deepEquals(Object obj) {
        Match other = (Match)obj;
        return Objects.equals(this.team1, other.team1) &&
                Objects.equals(this.team2, other.team2) &&
                Objects.equals(this.championship, other.championship);
    }

    @Override
    protected int deepHashCode() {
        return Objects.hash(this.team1, this.team2, this.championship);
    }

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
}
