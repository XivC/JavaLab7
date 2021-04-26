package com.xivs.common.parameters.numericalParameters;

public class IntegerParameter extends NumericalParameter<Integer> {
    public IntegerParameter(Integer value) {
        super(value);
    }

    int compareValues(Integer value1, Integer value2) {
        return value1 - value2;
    }

    public IntegerParameter setLowerBound(Integer lowerBound) {
        super.setLowerBound(lowerBound);
        return this;
    }

    public IntegerParameter setUpperBound(Integer upperBound) {
        super.setUpperBound(upperBound);
        return this;
    }

    public boolean parse(String s) {
        try {
            Integer value = Integer.valueOf(s);
            return this.set(value);

        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
