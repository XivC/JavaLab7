package com.xivs.common.dataTransfer;

import java.io.Serializable;


public class DataTransference<T> implements Serializable {
    public final Class<T> type;
    T value;

    public DataTransference(Class<T> type, T value) {
        this.type = type;
        this.value = value;

    }

    public T get() {
        return this.value;
    }

}
