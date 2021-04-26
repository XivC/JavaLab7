package com.xivs.common.parameters;

public class EnumParameter<T extends Enum<T>> extends Parameter<T> {
    Class<T> enumType;

    public EnumParameter(Class<T> enumType, T value) {
        super(value);
        this.enumType = enumType;
    }

    public boolean parse(String s) {
        try {
            this.value = T.valueOf(enumType, s);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
}
