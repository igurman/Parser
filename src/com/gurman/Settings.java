package com.gurman;

import java.util.Properties;

abstract class Settings {

    String pathSettings = null;         //путь до файла с настройками

    String pathIn = null;               //путь до файла с ключами
    int threadsCount = 1;               //количество потоков
    int curlReadTimeout = 10;           //время чтения страницы сек.
    int curlConnectTimeout = 10;        //время ожидания страницы сек.
    boolean save_key = true;           //добавить ключ в out
    int count_get_page = 100;             //сколько раз пытаться загрузить страницу
    boolean rule404 = false;           //учитывать 404 ответ
    boolean savePage = false;           //сохранить страницы на диск

    String acceptLanguage;
    String host;
    String userAgent;

    String settingsName = null;         //префикс настроек
    String query = null;
    String target = null;
    String regexp = null;
    String new_line = null;
    String replace = null;

    String getPathIn() {
        return pathIn;
    }

    int getThreadsCount() {
        return threadsCount;
    }

    int getCurlReadTimeout() {
        return curlReadTimeout;
    }

    int getCurlConnectTimeout() {
        return curlConnectTimeout;
    }

    boolean isSave_key() {
        return save_key;
    }

    boolean isRule404() {
        return rule404;
    }

    int getCount_get_page() {
        return count_get_page;
    }

    boolean isSavePage() {
        return savePage;
    }

    String getQuery() {
        return query;
    }

    String getTarget() {
        return target;
    }

    String getRegexp() {
        return regexp;
    }

    String getNew_line() {
        return new_line;
    }

    String getReplace() {
        return replace;
    }

    String getAcceptLanguage() {
        return acceptLanguage;
    }

    String getHost() {
        return host;
    }

    String getUserAgent() {
        return userAgent;
    }
}
