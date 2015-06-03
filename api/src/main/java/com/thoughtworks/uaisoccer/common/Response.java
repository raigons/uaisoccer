package com.thoughtworks.uaisoccer.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(Include.NON_NULL)
public class Response<V> {

    @JsonInclude(Include.NON_EMPTY)
    private List<Error> errors;

    @JsonUnwrapped
    private V value;

    public void addError(Error error) {
        if (this.errors == null) {
            this.errors = new ArrayList<>();
        }
        this.errors.add(error);
    }

    public void addError(String message) {
        addError(message, null);
    }

    public void addError(String message, String field) {
        addError(new Error(field, message));
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
