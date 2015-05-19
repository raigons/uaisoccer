package com.thoughtworks.uaisoccer.teams;

import java.text.Normalizer;

public class TeamKeyGenerator {

    public String generateKeyFromName(String name) {
        String key;

        key = name.toLowerCase();
        key = key.replaceAll("\\s+", "-");
        key = Normalizer.normalize(key, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");

        return key;
    }
}
