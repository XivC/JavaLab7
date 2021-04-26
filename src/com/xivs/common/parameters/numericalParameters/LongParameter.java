package com.xivs.common.parameters.numericalParameters;

public class LongParameter extends NumericalParameter<Long> {
    public LongParameter(Long value) {
        super(value);
    }

    int compareValues(Long value1, Long value2) {
        return (int) Math.floor(value1 - value2);
    }

    public LongParameter setLowerBound(Long lowerBound) {
        super.setLowerBound(lowerBound);
        return this;
    }

    public LongParameter setUpperBound(Long upperBound) {
        super.setUpperBound(upperBound);
        return this;
    }

    public boolean parse(String s) {
        try {
            Long value = Long.valueOf(s);
            return this.set(value);

        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
