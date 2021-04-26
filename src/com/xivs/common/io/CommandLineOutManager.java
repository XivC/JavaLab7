package com.xivs.common.io;

public class CommandLineOutManager extends OutputManager {
    public void print(String s) {
        System.out.print(s);
    }

    public void println(String s) {
        System.out.println(s);
    }
}
