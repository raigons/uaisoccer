package com.thoughtworks.uaisoccer.championships;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thoughtworks.uaisoccer.championships.matches.Match;
import com.thoughtworks.uaisoccer.teams.Team;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Entity
@EqualsAndHashCode
public class Championship {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="championship_gen")
    @SequenceGenerator(name="championship_gen", sequenceName="championship_seq")
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


    @Transient
    private List<Match> matches;

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
}
