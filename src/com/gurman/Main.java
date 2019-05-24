package com.gurman;

public class Main {

    public static void main(String[] args) {

        String[] arr = new String[20];
        arr[0] = "google";
        arr[1] = "google50";
        arr[2] = "googleDop";
        arr[3] = "similarweb";
        arr[4] = "archive-org-spark";
        arr[5] = "archive-org-calend";
        arr[6] = "nethouse";
        arr[7] = "googleImg";
        arr[8] = "domain-status";
        arr[9] = "proxy-checker";
        arr[10] = "dns-revers";
        arr[11] = "cubdomain-com";
        arr[12] = "cubdomain-com2";
        arr[13] = "telegram";

        Settings settings = new SettingsRelease(arr[0]);
        ProxyTool proxy = new ProxyTool(".\\res\\proxy.txt");
        ProducerConsumer.Go(settings);
    }
}

