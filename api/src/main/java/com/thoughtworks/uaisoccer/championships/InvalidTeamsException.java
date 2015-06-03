package com.thoughtworks.uaisoccer.championships;

import com.thoughtworks.uaisoccer.common.Error;

import java.util.List;

public class InvalidTeamsException extends Exception {

    private List<Error> errors;

    public InvalidTeamsException(List<Error> errors) {
        this.errors = errors;
    }

    public List<Error> getErrors() {
        return errors;
    }
}
