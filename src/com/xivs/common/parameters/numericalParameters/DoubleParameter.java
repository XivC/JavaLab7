package com.xivs.common.parameters.numericalParameters;

public class DoubleParameter extends NumericalParameter<Double> {
    public DoubleParameter(Double value) {
        super(value);
    }

    int compareValues(Double value1, Double value2) {
        return (int) Math.floor(value1 - value2);
    }

    public DoubleParameter setLowerBound(Double lowerBound) {
        super.setLowerBound(lowerBound);
        return this;
    }

    public DoubleParameter setUpperBound(Double upperBound) {
        super.setUpperBound(upperBound);
        return this;
    }

    public boolean parse(String s) {
        try {
            Double value = Double.valueOf(s);
            this.set(value);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
