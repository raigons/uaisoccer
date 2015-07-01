package com.thoughtworks.uaisoccer.championships;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thoughtworks.uaisoccer.championships.matches.Match;
import com.thoughtworks.uaisoccer.teams.Team;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Collections;
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

    private List<Match> matches;
    private List<Classification> classificationTable;

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
        generateClassificationTable();
    }

    private void generateClassificationTable() {
        classificationTable = new ArrayList<Classification>();
        Classification row;
        for (Team team : teams) {
            row = new Classification(team, 0, 0); //TODO: create a Builder
            classificationTable.add(row);
        }
    }

    public void addTeam(Team team) {
        if (this.teams == null) {
            this.teams = new ArrayList<>();
        }
        this.teams.add(team);
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public Team getChampion() {
        updateClassification();
        Classification winnerRow = classificationTable.get(0);
        return winnerRow.getTeam();
    }

    //TODO: make classification a proper data structure
    private void updateClassification() {
        List<Classification> auxClassificationTable = new ArrayList<>();
        Classification row;
        for (Team team : teams) {
            row = new Classification(team, getPoints(team), getGoals(team));
            auxClassificationTable.add(row);
        }
        Collections.sort(auxClassificationTable);
        for (Classification c : auxClassificationTable) {
            System.out.print(c.getTeam().getName() + " " + c.getPoints());
        }
        classificationTable = auxClassificationTable;
    }

    private int getGoals(Team team) {
        return 0;
    }

    private int getPoints(Team team) {
        int points = 0;
        for (Match match : matches) {
            try {
                if (match.getWinner().equals(team)) {
                    points += 3;
                }
            } catch (Exception e) {
                points++;
            }
        }
        return points;
    }

    /* potentially this is the solution
    private HashMap<String, List<Match>> rounds;

    public HashMap<String, List<Match>> getRounds() { return rounds; }

    public List<Match> getRound(String round) {
        return rounds.get(round);
    }
    */

}
