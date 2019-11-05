package com.gurman;

public class Main {

    public static void main(String[] args) {

        String[] arr = new String[20];
        arr[0] = "site";
        arr[1] = "site2";
        arr[2] = "site3";

        Settings settings = new SettingsRelease(arr[0]);
        ProxyTool proxy = new ProxyTool(".\\res\\proxy.txt");
        ProducerConsumer.Go(settings);
    }
}

