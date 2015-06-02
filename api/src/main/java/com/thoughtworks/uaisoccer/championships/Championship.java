package com.thoughtworks.uaisoccer.championships;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thoughtworks.uaisoccer.common.BaseModel;
import com.thoughtworks.uaisoccer.teams.Team;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Championship extends BaseModel {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Pattern(regexp = "^(?![0-9]+$).*$", message = "cannot be a number")
    private String name;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "championship_team",
            joinColumns = { @JoinColumn(name = "championship_id") },
            inverseJoinColumns = { @JoinColumn(name = "team_id") })
    private List<Team> teams;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public void addTeam(Team team) {
        if (this.teams == null) {
            this.teams = new ArrayList<>();
        }
        this.teams.add(team);
    }

    @Override
    protected boolean deepEquals(Object obj) {
        Championship other = (Championship)obj;
        return Objects.equals(this.id, other.id) && Objects.equals(this.name, other.name);
    }

    @Override
    protected int deepHashCode() {
        return Objects.hash(this.id, this.name);
    }
}