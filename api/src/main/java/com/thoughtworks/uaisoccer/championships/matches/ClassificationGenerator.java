package com.thoughtworks.uaisoccer.championships.matches;

import com.thoughtworks.uaisoccer.championships.Classification;
import com.thoughtworks.uaisoccer.teams.Team;

import java.util.List;

public interface ClassificationGenerator {

    List<Classification> getClassification(List<Team> teams, List<Match> matches);

    int getGoals(Team team, List<Match> matches);

    int getPoints(Team team, List<Match> matches);

}
