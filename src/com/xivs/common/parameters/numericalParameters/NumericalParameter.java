package com.xivs.common.parameters.numericalParameters;

import com.xivs.common.parameters.Parameter;

public abstract class NumericalParameter<T extends Number> extends Parameter<T> {
    T lowerBound;
    T upperBound;

    public NumericalParameter(T value) {
        super(value);
        this.lowerBound = null;
        this.upperBound = null;
    }

    public NumericalParameter<T> setLowerBound(T lowerBound) {
        this.lowerBound = lowerBound;
        return this;
    }

    public NumericalParameter<T> setUpperBound(T upperBound) {
        this.upperBound = upperBound;
        return this;
    }

    abstract int compareValues(T value1, T value2);

    boolean validate(T value) {

        if (this.lowerBound != null && this.compareValues(this.lowerBound, this.value) < 0) return false;
        if (this.upperBound != null && this.compareValues(this.upperBound, this.value) > 0) return false;
        return true;
    }

}
