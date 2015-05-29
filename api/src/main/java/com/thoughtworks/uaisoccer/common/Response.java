package com.thoughtworks.uaisoccer.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(Include.NON_NULL)
public class Response<V> {

    private String message;

    @JsonInclude(Include.NON_EMPTY)
    private List<ValidationError> errors = new ArrayList<>();

    @JsonUnwrapped
    private V value;

    public void addError(ValidationError validationError) {
        this.errors.add(validationError);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }
}
