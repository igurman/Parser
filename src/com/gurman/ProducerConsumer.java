package com.gurman;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class ProducerConsumer {

    private static ArrayBlockingQueue<String[]> queue_1 = new ArrayBlockingQueue<>(20);
    private static ArrayBlockingQueue<String[]> queue_2 = new ArrayBlockingQueue<>(20);
    private static Settings settings;

    static void Go(Settings settings) {
        ProducerConsumer.settings = settings;

        Thread thread = new Thread(ProducerConsumer::Parser);
        Thread thread2 = new Thread(ProducerConsumer::Consumer_Modidfy);
        Thread thread3 = new Thread(ProducerConsumer::Consumer_Save);

        thread.start();
        thread2.start();
        thread3.start();

        try {
            thread.join();
            Thread.sleep(5000);

            thread3.interrupt();
            Thread.sleep(1000);
            thread2.interrupt();

            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(ProxyTool.getProxyGoodList());
    }

    private static void Parser() {
        List urls = Tool.FileToArrayList(settings.pathIn);
        ExecutorService executorService = Executors.newFixedThreadPool(settings.threadsCount);
        for (Object _url : urls) {
            executorService.submit(new Worker(_url.toString(), settings));
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void Consumer_Modidfy() {
        String d = settings.getReplace();

        while (!Thread.interrupted()) {
            if (d.equals("#")) {
                String[] res = takeQueue_1();
                ProducerConsumer.putQueue_2(res[0], res[1]);
            } else {
                Modify modify = new Modify(d);
                String[] res = takeQueue_1();
                ProducerConsumer.putQueue_2(res[0], modify.getText(res[1]));
            }
        }
    }

    private static void Consumer_Save() {
        while (!Thread.interrupted()) {
            String[] res = takeQueue_2();
            if (settings.isSave_key()) {
                SaveFile.Save(res[0] + "\t" + res[1]);
            } else {
                SaveFile.Save(res[1]);
            }
        }
    }

    static void putQueue_1(String key, String value) {
        try {
            queue_1.put(new String[]{key, value});
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static String[] takeQueue_1() {
        try {
            return queue_1.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new String[]{};
    }

    private static void putQueue_2(String key, String value) {
        try {
            queue_2.put(new String[]{key, value});
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static String[] takeQueue_2() {
        try {
            return queue_2.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new String[]{};
    }
}
