package com.xivs.common.parameters;

public abstract class Parameter<T> {
    protected T value;

    public Parameter(T defaultValue) {
        this.value = defaultValue;
    }

    public boolean set(T value) {
        if (!this.validate(value)) return false;
        this.value = value;
        return true;
    }

    public Parameter<T> getObject() {
        return this;
    }

    public T get() {
        return value;
    }

    public abstract boolean parse(String s_value);

    boolean validate(T value) {
        return true;
    }
}
