package com.thoughtworks.uaisoccer.common;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BaseModel {

    public boolean equals(Object obj) {
        if (obj == null || !(obj.getClass() == this.getClass())) {
            return false;
        }
        return deepEquals(obj);
    }

    protected abstract boolean deepEquals(Object obj);

    public int hashCode() {
        return deepHashCode();
    }

    protected abstract int deepHashCode();
}
