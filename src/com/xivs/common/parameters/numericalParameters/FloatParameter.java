package com.xivs.common.parameters.numericalParameters;

public class FloatParameter extends NumericalParameter<Float> {
    public FloatParameter(Float value) {
        super(value);
    }

    int compareValues(Float value1, Float value2) {
        return (int) Math.floor(value1 - value2);
    }

    public FloatParameter setLowerBound(Float lowerBound) {
        super.setLowerBound(lowerBound);
        return this;
    }

    public FloatParameter setUpperBound(Float upperBound) {
        super.setUpperBound(upperBound);
        return this;
    }

    public boolean parse(String s) {
        try {
            Float value = Float.valueOf(s);
            this.set(value);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
