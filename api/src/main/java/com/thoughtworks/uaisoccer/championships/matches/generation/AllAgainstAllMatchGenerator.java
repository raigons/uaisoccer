package com.thoughtworks.uaisoccer.championships.matches.generation;

import com.thoughtworks.uaisoccer.championships.Championship;
import com.thoughtworks.uaisoccer.championships.matches.Match;
import com.thoughtworks.uaisoccer.championships.matches.MatchBuilder;
import com.thoughtworks.uaisoccer.teams.Team;

import java.util.ArrayList;
import java.util.List;

public class AllAgainstAllMatchGenerator implements MatchGenerator {

    @Override
    public List<Match> generateMatches(Championship championship) throws MatchGenerationException {
        List<Team> teams = championship.getTeams();

        if (teams == null || teams.size() < 2)  {
            throw new MatchGenerationException("Can not generate matches with less than two teams");
        }

        List<Match> matches = new ArrayList<>();
        int n = teams.size();

        for (int i = 0; i < (n - 1); i++) {
            Team team1 = teams.get(i);
            for (int j = i + 1; j < n; j++) {
                Team team2 = teams.get(j);

                Match match = new MatchBuilder()
                        .withChampionship(championship)
                        .withTeam1(team1)
                        .withTeam2(team2)
                        .build();

                matches.add(match);
            }
        }

        return matches;
    }
}