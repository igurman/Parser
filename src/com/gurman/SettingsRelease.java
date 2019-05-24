package com.gurman;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

class SettingsRelease extends Settings {

    private Properties property = getProperties("res\\config.properties");

    SettingsRelease(String prefix) {

        this.pathIn                 = getString("main.pathIn");
        this.threadsCount           = getInt("main.threadsCount", "1");
        this.curlReadTimeout        = getInt("main.curlReadTimeout", "10");
        this.curlConnectTimeout     = getInt("main.curlConnectTimeout", "10");
        this.save_key               = getBoolean("main.save_key", "true");
        this.count_get_page         = getInt("main.count_get_page", "100");
        this.savePage               = getBoolean("main.savePage", "true");

        this.acceptLanguage         = getString("main.acceptLanguage");
        this.host                   = getString("main.host");
        this.userAgent              = getString("main.userAgent");

        this.settingsName           = prefix;
        this.query                  = getString(prefix + ".query");
        this.target                 = getString(prefix + ".target");
        this.regexp                 = getString(prefix + ".regexp");
        this.rule404                = getBoolean(prefix + ".rule404", "false");

        this.new_line               = getString(prefix + ".new_line", System.lineSeparator());
        if (this.new_line.equals("")) {
            this.new_line = System.lineSeparator();
        }

        this.replace                = getString(prefix + ".replace", "#");
        if (this.replace.equals("")) {
            this.replace = "#";
        }
    }

    private boolean getBoolean(String key, String defaults){
        String a = property.getProperty(key, defaults);
        return a.equals("1") || a.equals("true");
    }

    private int getInt(String key, String defaults){
        return Integer.parseInt(property.getProperty(key, defaults));
    }

    private String getString(String key, String defaults){
        return property.getProperty(key, defaults);
    }

    private String getString(String key){
        return property.getProperty(key);
    }

    private Properties getProperties(String pathSettings) {
        Properties property = new Properties();
        try {
            property.load(new FileInputStream(pathSettings));
        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
        }
        return property;
    }

}
