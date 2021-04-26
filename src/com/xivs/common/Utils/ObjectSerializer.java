package com.xivs.common.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectSerializer {
    public static byte[] serialize(Object o){
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        try {

            ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
            objectStream.writeObject(o);
            objectStream.flush();
            return byteStream.toByteArray();


        } catch (IOException ex) {
            ex.printStackTrace();
            return new byte[]{};
        }
    }
}
