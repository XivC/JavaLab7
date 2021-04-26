package com.xivs.common.parameters;

public class StringParameter extends Parameter<String> {
    private int maxLength;
    private int minLength;

    public StringParameter(String value) {
        super(value);
        this.maxLength = Integer.MAX_VALUE;
        this.minLength = 0;

    }

    public StringParameter setMaxLength(int maxLength) {
        this.maxLength = maxLength;
        return this;
    }

    public StringParameter setMinLength(int minLength) {
        this.minLength = minLength;
        return this;
    }

    public boolean parse(String s) {
        return this.set(s);
    }

    @Override
    boolean validate(String s) {
        if (s.length() > this.maxLength | s.length() < minLength) return false;
        return true;
    }
}
