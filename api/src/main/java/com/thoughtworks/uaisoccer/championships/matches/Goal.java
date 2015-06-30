package com.thoughtworks.uaisoccer.championships.matches;

import com.thoughtworks.uaisoccer.teams.Player;

public class Goal implements MatchEvent {

    private double time;
    private Player player;
    private boolean auto;

    public void setTime(double time) {
        this.time = time;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() { return player; }


    public void setAuto(boolean auto) {
        this.auto = auto;
    }

    public boolean isAuto() { return auto; }
}
