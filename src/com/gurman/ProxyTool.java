package com.gurman;

import java.util.*;

class ProxyTool {
    private static List proxys;
    private static Set<String> proxy_good = new HashSet<>();
    private static int count = 0;

    ProxyTool(String pathProxy) {
        proxys = Tool.FileToArrayList(pathProxy);
    }

    static synchronized String getProxy() {
//        Random rnd = new Random();
        String proxy = null;

        if (proxy_good.size() > 0) {
            Iterator<String> iterator = proxy_good.iterator();
            proxy = iterator.next();
            proxy_good.remove(proxy);
        } else {
//            if(proxys.size() > 0){
//                proxy = (String) proxys.get(rnd.nextInt(proxys.size()));
//            }
            if(proxys.size() > 0){
                if(count < proxys.size()){
                    proxy = (String) proxys.get(count++);
                }else {
                    proxy = (String) proxys.get(count=0);
                }
            }

        }
//        System.out.println(proxy);
        return proxy;
    }

    static synchronized void setProxyGood(String proxy){
        proxy_good.add(proxy);
    }

    static synchronized void removeProxyGood(String proxy){
        proxy_good.remove(proxy);
    }

    static List getProxyList(){
        return proxys;
    }

    static Set getProxyGoodList(){
        return proxy_good;
    }
}




