package com.thoughtworks.uaisoccer.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(Include.NON_NULL)
public class Response<V> {

    @JsonInclude(Include.NON_EMPTY)
    private List<ValidationError> errors;

    @JsonUnwrapped
    private V value;

    public void addError(ValidationError validationError) {
        if (this.errors == null) {
            this.errors = new ArrayList<>();
        }
        this.errors.add(validationError);
    }

    public void addError(String message) {
        addError(message, null);
    }

    public void addError(String message, String field) {
        addError(new ValidationError(field, message));
    }

    public List<ValidationError> getErrors() {
        return errors;
    }

    public void setErrors(List<ValidationError> errors) {
        this.errors = errors;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
