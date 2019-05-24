package com.gurman;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Worker implements Runnable {

    private String url;
    private Settings settings;

    Worker(String url, Settings settings) {
        this.url = url;
        this.settings = settings;
    }

    @Override
    public void run() {
        int count = 0;
        int count_step = 10;
        int count_max = 100;
        boolean around = false;

        String new_url = buildUrl();

        String page = "";
        for (int i = 0; i < settings.getCount_get_page(); i++) {
            String proxy = ProxyTool.getProxy();

            if (proxy != null) {
                new_url = new_url.replace("[PAGE]", Integer.toString(count));
                page = new Curl(new_url, proxy, settings).getUrl();
                //если страница не пуста
                if (!page.equals("")) {
                    //если на странице есть указанные маркеры
                    if (isPageHasTarget(page)) {
                        savePage(page);
                        saveProxyGoog(proxy);
                        ProducerConsumer.putQueue_1(url, regexParse(page));
                        System.out.println("ok\t" + url);
                        //System.out.println(proxy);
                        if (around && count < count_max) {
                            count += count_step;
                        } else {
                            break;
                        }
                    }
                } else {
                    ProxyTool.removeProxyGood(proxy);
                }
            } else {
                throw new NullPointerException("нет прокси");
            }
        }


    }

    private String buildUrl() {
        String new_url = "";
        try {
            new_url = settings.getQuery().replace("[KEY]", URLEncoder.encode(url, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new_url;
    }

    private boolean isPageHasTarget(String page){
        String[] targets = settings.getTarget().split("\\|");
        return (page.contains(targets[0])
                && page.contains(targets[1])
                && !page.contains(targets[2])
        )
                || page.contains("404");
    }

    private void savePage(String page){
        if (settings.isSavePage()) {
            Tool.saveHtml(page);
        }
    }

    private void saveProxyGoog(String proxy){
        ProxyTool.setProxyGood(proxy);
    }

    private String regexParse(String page) {

        StringBuilder html = new StringBuilder();
        Pattern pattern = Pattern.compile(settings.getRegexp(), Pattern.DOTALL);
        Matcher matcher = pattern.matcher(page);
        while (matcher.find()) {
            html.append(matcher.group(1)).append(settings.getNew_line());
        }
        //если отделяем найденное вверху через TAB, то добавим перенос каретки в конец строки
        if(!settings.getNew_line().contains(System.lineSeparator()) && html.length() != 0){
            html.append(System.lineSeparator());
        }

        if(html.length() == 0){
            html.append(" ---").append(System.lineSeparator());
        }

        return html.toString();
    }



}
