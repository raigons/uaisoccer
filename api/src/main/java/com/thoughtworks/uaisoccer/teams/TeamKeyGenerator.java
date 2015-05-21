package com.thoughtworks.uaisoccer.teams;

import java.text.Normalizer;

public class TeamKeyGenerator {

    public String generateKeyFromName(String name) {
        String key;

        key = name.trim().toLowerCase();
        key = key.replaceAll("[-_\\s]+", "-");
        key = Normalizer.normalize(key, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        key = key.replaceAll("[^a-zA-Z0-9\\-]", "");
        key = key.replaceAll("[-_]$", "");
        key = key.replaceAll("^[-_]", "");

        return key;
    }
}
