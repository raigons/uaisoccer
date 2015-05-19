package com.thoughtworks.uaisoccer.teams;

import java.text.Normalizer;

public class TeamKeyGenerator {

    public String generateKeyFromName(String name) {
        String key;

        key = name.toLowerCase();
        key = key.replaceAll("[-_\\s]+", "-");
        key = Normalizer.normalize(key, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        key = key.replaceAll("[^a-zA-Z0-9\\-]", "");

        return key;
    }
}
