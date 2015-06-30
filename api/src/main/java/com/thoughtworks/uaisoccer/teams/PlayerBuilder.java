package com.thoughtworks.uaisoccer.teams;


public class PlayerBuilder {

    Player instance = new Player();

    public PlayerBuilder withId(double id) {
        instance.setId(id);
        return this;
    }

    public PlayerBuilder withName(String name) {
        instance.setName(name);
        return this;
    }

    public PlayerBuilder withNickname(String nickname) {
        instance.setNickname(nickname);
        return this;
    }

    public PlayerBuilder withTeam(Team team) {
        instance.setTeam(team);
        return this;
    }

    public Player build() {
        return instance;
    }
}
