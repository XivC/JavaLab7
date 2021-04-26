package com.xivs.common.dataTransfer;

public class Utils {
    public static void printResponseMessages(Response resp) {
        try {
            for (String msg : resp.messages) {
                System.out.println(msg);
            }
        }
        catch(NullPointerException ex){
            return;
        }
    }
}
