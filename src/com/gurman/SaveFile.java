package com.gurman;

import java.io.FileWriter;
import java.io.IOException;

class SaveFile {

    static synchronized void Save(String data) {
        try {
            FileWriter fw = new FileWriter(".\\res\\parse\\out.txt", true); //the true will append the new data
            fw.write(data);//appends the string to the file
            fw.close();
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }
}
