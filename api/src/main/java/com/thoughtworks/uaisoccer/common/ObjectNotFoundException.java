package com.thoughtworks.uaisoccer.common;

public class ObjectNotFoundException extends Exception {

    public ObjectNotFoundException(long identifier) {
        super("Could not find object with id: " + identifier);
    }
}