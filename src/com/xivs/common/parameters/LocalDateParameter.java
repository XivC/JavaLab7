package com.xivs.common.parameters;

import java.time.DateTimeException;
import java.time.LocalDate;

public class LocalDateParameter extends Parameter<LocalDate> {
    public LocalDateParameter(LocalDate value) {
        super(value);
    }

    @Override
    public boolean parse(String s) {
        try {
            LocalDate value = LocalDate.parse(s);
            this.value = value;
            return true;
        } catch (DateTimeException ex) {
            return false;
        }
    }
}
