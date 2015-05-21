package com.thoughtworks.uaisoccer.teams;

public class TeamBuilder {

    private Team instance;

    public TeamBuilder() {
        this.instance = new Team();
    }

    public TeamBuilder withName(String name) {
        instance.setName(name);
        return this;
    }

    public TeamBuilder withKey(String key) {
        instance.setKey(key);
        return this;
    }

    public TeamBuilder withEnabled(Boolean enabled) {
        instance.setEnabled(enabled);
        return this;
    }

    public Team build() {
        return instance;
    }
}
