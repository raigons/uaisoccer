package com.thoughtworks.uaisoccer.teams;

import com.thoughtworks.uaisoccer.common.AbstractDomainObject;

import java.util.Objects;

public class Team extends AbstractDomainObject {

    private Long id;
    private String name;
    private String key;

    public Team() {}

    public Team(Long id, String name, String key) {
        this.id = id;
        this.name = name;
        this.key = key;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int hashCode() {
        return Objects.hash(id, name, key);
    }

    public boolean deepEquals(Object obj) {
        Team other = (Team)obj;
        return Objects.equals(this.id, other.id) &&
                Objects.equals(this.name, other.name) &&
                Objects.equals(this.key, other.key);
    }
}