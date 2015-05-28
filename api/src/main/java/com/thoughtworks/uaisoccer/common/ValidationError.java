package com.thoughtworks.uaisoccer.common;

public class ValidationError {

    private String message;

    private String field;

    public ValidationError(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getField() {
        return field;
    }
}
