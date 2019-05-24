package com.gurman;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Tool {

    static List FileToArrayList(String res) {
        List<String> list = new ArrayList<>();
        try {
            Scanner sc = new Scanner(new File(res));
            while (sc.hasNextLine()) {
                list.add(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        return list;
    }

    static void saveHtml(String page) {
        String name = Long.toString(System.currentTimeMillis());
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(".\\res\\html\\" + name + ".html"));
            writer.write(page);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
