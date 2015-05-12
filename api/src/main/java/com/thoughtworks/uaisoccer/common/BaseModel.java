package com.thoughtworks.uaisoccer.common;

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