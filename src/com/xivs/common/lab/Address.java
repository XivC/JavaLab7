package com.xivs.common.lab;

import com.xivs.common.parameters.StringParameter;

import java.io.Serializable;

public class Address implements Serializable {
    public String street; //Поле может быть null
    public String zipCode; //Длина строки должна быть не меньше 9, Поле не может быть null
    public static final Address DEFAULT = new Address("", "000000000");

    public static class Params implements Serializable {
        public static StringParameter street = new StringParameter("");
        public static StringParameter zipCode = new StringParameter("000000000").setMinLength(9);

    }

    public Address(String street, String zipCode) {
        this.street = street;
        this.zipCode = zipCode;
    }

    public Address() {
    }



}
