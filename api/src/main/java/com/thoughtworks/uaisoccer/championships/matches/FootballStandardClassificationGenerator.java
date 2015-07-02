package com.thoughtworks.uaisoccer.championships.matches;

import com.thoughtworks.uaisoccer.championships.Classification;
import com.thoughtworks.uaisoccer.teams.Team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FootballStandardClassificationGenerator implements ClassificationGenerator {

    public static int POINTS_PER_VICTORY = 3;
    public static int POINTS_PER_DRAW = 1;

    @Override
    public List<Classification> getClassification(List<Team> teams, List<Match> matches) {
        List<Classification> classificationTable = new ArrayList<>();
        Classification row;
        for (Team team : teams) {
            row = new Classification(team, getPoints(team, matches), getGoals(team, matches));
            classificationTable.add(row);
        }
        Collections.sort(classificationTable);
        return classificationTable;
    }

    @Override
    public int getGoals(Team team, List<Match> matches) {
        int goals = 0;
        for (Match match : matches) {
            if (match.contains(team)) {
                goals += match.getGoals(team);
            }
        }
        return goals;
    }

    @Override
    public int getPoints(Team team, List<Match> matches) {
        int points = 0;
        for (Match match : matches) {
            try {
                if (match.getWinner().equals(team)) {
                    points += POINTS_PER_VICTORY;
                }
            } catch (DrawException e) {
                points += POINTS_PER_DRAW;;
            }
        }
        return points;
    }

}
