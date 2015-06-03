package com.thoughtworks.uaisoccer.championships;

import com.thoughtworks.uaisoccer.common.ValidationError;

import java.util.List;

public class InvalidTeamsException extends Exception {

    private List<ValidationError> errors;

    public InvalidTeamsException(List<ValidationError> errors) {
        this.errors = errors;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }
}
