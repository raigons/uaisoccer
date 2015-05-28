package com.thoughtworks.uaisoccer.common;

public class InvalidTeamNameException extends Exception {

    public InvalidTeamNameException(String name) {
        super(String.format("%s does not contain any valid characters to create a key", name));
    }
}
