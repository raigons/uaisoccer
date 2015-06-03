package com.thoughtworks.uaisoccer.teams;

import java.text.Normalizer;

public class TeamKeyGenerator {

    public String generateKeyFromName(String name) throws InvalidTeamNameException {
        String key;

        key = name.trim();
        key = key.toLowerCase();

        key = replaceUnderscoreAndWhitespaceByDash(key);
        key = removeAccentuation(key);
        key = removeNonAlphanumericCharacters(key);

        key = removeDuplicateDashes(key);

        if (key.isEmpty())
            throw new InvalidTeamNameException(name);

        return key;
    }

    private String removeNonAlphanumericCharacters(String key) {
        return key.replaceAll("[^a-zA-Z0-9\\-]", "");
    }

    private String removeAccentuation(String key) {
        return Normalizer.normalize(key, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    private String removeDuplicateDashes(String key) {
        key = key.replaceAll("(-)+", "-");
        key = key.replaceAll("(-)$", "");
        key = key.replaceAll("^(-)", "");
        return key;
    }

    private String replaceUnderscoreAndWhitespaceByDash(String key) {
        return key.replaceAll("[_\\s]+", "-");
    }
}