package com.thoughtworks.uaisoccer.championships.matches;

import com.thoughtworks.uaisoccer.teams.Player;

public class GoalEventBuilder {

    Goal instance = new Goal();

    public GoalEventBuilder withTime(double timestamp) {
        instance.setTime(timestamp);
        return this;
    }

    public GoalEventBuilder withPlayer(Player player) {
        instance.setPlayer(player);
        return this;
    }

    public GoalEventBuilder setAuto(boolean auto) {
        instance.setAuto(auto);
        return this;
    }

    public Goal build() {
        return instance;
    }
}
