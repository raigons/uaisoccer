package com.thoughtworks.uaisoccer.teams;

public class InvalidTeamNameException extends Exception {

    public InvalidTeamNameException(String name) {
        super(String.format("%s does not contain any valid characters to create a key", name));
    }
}
