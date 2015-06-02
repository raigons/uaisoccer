package com.thoughtworks.uaisoccer.championships;

import java.util.List;

public class NonexistentTeamsException extends Exception {

    private List<Long> ids;

    public NonexistentTeamsException(List<Long> ids) {
        this.ids = ids;
    }

    public List<Long> getIds() {
        return ids;
    }
}
