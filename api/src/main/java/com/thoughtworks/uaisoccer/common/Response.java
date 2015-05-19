package com.thoughtworks.uaisoccer.common;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class Response<V> {

    private String message;

    @JsonUnwrapped
    private V value;

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
}
