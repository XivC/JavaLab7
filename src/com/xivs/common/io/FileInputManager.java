package com.xivs.common.io;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class FileInputManager extends InputManager {
    InputStreamReader stream;
    BufferedInputStream bis;
    boolean hasNext;

    public FileInputManager(File file) throws FileNotFoundException {
        super();
        this.bis = new BufferedInputStream(new FileInputStream(file));
        this.stream = new InputStreamReader(this.bis);
        this.hasNext = true;
    }

    public boolean hasNext() {
        return this.hasNext;
    }

    /**
     * Читает следующую строку из файла.
     * Строка складывается в буфферы
     */
    public String nextLine() throws IOException {
        StringBuilder sb = new StringBuilder();
        try {

            boolean end = false;
            while (!end && this.hasNext()) {
                int c = this.stream.read();


                switch ((char) c) {
                    case '\n':
                        end = true;
                        break;
                    case '\r':
                        continue;
                    default:
                        if (c == -1) {
                            this.hasNext = false;
                            end = true;
                            stream.close();
                            break;
                        }
                        sb.append((char) c);
                        this.charBuffer.add((char) c);
                        break;
                }
            }
        } catch (IOException ex) {
            throw ex;
        }
        this.stringBuffer = sb.toString();
        this.wordBuffer = new ArrayList(Arrays.asList(sb.toString().split(" ")));
        return sb.toString();
    }

}


