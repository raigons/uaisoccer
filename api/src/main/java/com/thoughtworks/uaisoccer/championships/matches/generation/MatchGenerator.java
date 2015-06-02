package com.thoughtworks.uaisoccer.championships.matches.generation;

import com.thoughtworks.uaisoccer.championships.Championship;
import com.thoughtworks.uaisoccer.championships.matches.Match;

import java.util.List;

public interface MatchGenerator {

    List<Match> generateMatches(Championship championship) throws MatchGenerationException;
}
