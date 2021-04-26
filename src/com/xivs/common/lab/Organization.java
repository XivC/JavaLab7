package com.xivs.common.lab;

import com.xivs.common.parameters.EnumParameter;
import com.xivs.common.parameters.numericalParameters.IntegerParameter;

import java.io.Serializable;

public class Organization implements Serializable {
    public Integer annualTurnover; //Поле не может быть null, Значение поля должно быть больше 0
    public OrganizationType type; //Поле может быть null
    public Address officialAddress; //Поле может быть null
    public static final Organization DEFAULT = new Organization(1, OrganizationType.NONE, Address.DEFAULT);

    public static class Params {
        public static IntegerParameter annualTurnover = new IntegerParameter(1).setLowerBound(0);
        public static EnumParameter<OrganizationType> type = new EnumParameter<>(OrganizationType.class, OrganizationType.NONE);
    }

    public Organization(Integer annualTurnover, OrganizationType type, Address officialAddress) {
        this.annualTurnover = annualTurnover;
        this.type = type;
        this.officialAddress = officialAddress;
    }

    public Organization() {
    }




}
