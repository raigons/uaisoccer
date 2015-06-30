package com.thoughtworks.uaisoccer.championships.matches;

import com.thoughtworks.uaisoccer.teams.Player;

public class GoalBuilder {

    Goal instance = new Goal();

    public GoalBuilder withTime(double timestamp) {
        instance.setTime(timestamp);
        return this;
    }

    public GoalBuilder withPlayer(Player player) {
        instance.setPlayer(player);
        return this;
    }

    public GoalBuilder setAuto(boolean auto) {
        instance.setAuto(auto);
        return this;
    }

    public Goal build() {
        return instance;
    }
}
