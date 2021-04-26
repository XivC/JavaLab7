package com.xivs.common.lab;

import com.xivs.common.parameters.numericalParameters.DoubleParameter;
import com.xivs.common.parameters.numericalParameters.LongParameter;

import java.io.Serializable;

public class Coordinates implements Serializable {
    public Long x; //Поле не может быть null
    public Double y; //Максимальное значение поля: 139, Поле не может быть null
    public static Coordinates DEFAULT = new Coordinates(0L, 0D);

    public static class Params {
        public static LongParameter x = new LongParameter(0L);
        public static DoubleParameter y = new DoubleParameter(0D).setUpperBound(139D);
    }

    public Coordinates(Long x, Double y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates() {
    }




}

