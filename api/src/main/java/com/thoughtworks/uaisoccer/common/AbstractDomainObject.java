package com.thoughtworks.uaisoccer.common;

public abstract class AbstractDomainObject {

    public boolean equals(Object obj) {
        if (obj == null || !(obj.getClass() == this.getClass())) {
            return false;
        }
        return deepEquals(obj);
    }

    protected abstract boolean deepEquals(Object obj);
}