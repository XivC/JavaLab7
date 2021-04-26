package com.xivs.common.io;

import java.io.IOException;
import java.util.ArrayList;

public abstract class InputManager {
    String stringBuffer;
    ArrayList<String> wordBuffer;
    ArrayList<Character> charBuffer;

    public InputManager() {
        this.stringBuffer = "";
        this.wordBuffer = new ArrayList<>();
        this.charBuffer = new ArrayList<>();
    }

    public abstract String nextLine() throws IOException;

    /**
     * Вернуть словарный буффер
     *
     * @return
     */
    public ArrayList<String> getWords() {
        return this.wordBuffer;
    }

    /**
     * ернуть строковый буффер
     *
     * @return
     */
    public String getString() {
        return this.stringBuffer;
    }

    /**
     * Вернуть символьный буффер
     *
     * @return
     */
    public ArrayList<Character> getCharacter() {
        return this.charBuffer;
    }

    public abstract boolean hasNext();

}
