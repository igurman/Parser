package com.gurman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

class Curl {
    private String url;
    private String proxy_line;
    private Map<String, String> post = new HashMap<>();
    private HttpURLConnection conn = null;
    private Settings settings;

    Curl(String url, String proxy, Settings settings) {
        this.url = url;
        this.proxy_line = proxy;
        this.settings = settings;

        post.put("Accept-Language", settings.getAcceptLanguage());
        post.put("Host", settings.getHost());
        post.put("User-Agent", settings.getUserAgent());
    }

    String getUrl() {
        openConnection(url, getProxy());
        assert conn != null;

        setRequestPropertyAll();
        setTimeout();

        int status = getResponseCode();

        if (settings.isRule404() && status == HttpURLConnection.HTTP_NOT_FOUND) {
            return "404";
        }

        if (isRedirect(status)) {
            redirect();
        }

        System.out.println(url);
        return getPage();
    }

    private void openConnection(String url, Proxy proxy) {
        try {
            conn = (HttpURLConnection) new URL(url).openConnection(proxy);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getResponseCode() {
        int status = 0;
        try {
            status = conn.getResponseCode();
        } catch (IOException e) {
            //System.out.println(e.getMessage());
        }
        return status;
    }

    private Proxy getProxy() {
        String[] pr = proxy_line.split(":");
        return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(pr[0], Integer.parseInt(pr[1])));
    }

    private void setRequestPropertyAll() {
        for (Map.Entry<String, String> entry : post.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            conn.setRequestProperty(key, value);
        }
    }

    private void setTimeout() {
        conn.setReadTimeout(settings.getCurlReadTimeout() * 1000);
        conn.setConnectTimeout(settings.getCurlConnectTimeout() * 1000);
    }

    private void redirect() {
        String newUrl = conn.getHeaderField("Location");
        String cookies = conn.getHeaderField("Set-Cookie");

        openConnection(newUrl, getProxy());
        conn.setRequestProperty("Cookie", cookies);

        setRequestPropertyAll();
        conn.addRequestProperty("Referer", "google.com");
        setTimeout();

        System.out.println("Redirect to URL : " + newUrl);
    }

    private boolean isRedirect(int status) {
        if (status != HttpURLConnection.HTTP_OK) {
            return status == HttpURLConnection.HTTP_MOVED_TEMP
                    || status == HttpURLConnection.HTTP_MOVED_PERM
                    || status == HttpURLConnection.HTTP_SEE_OTHER;
        }
        return false;
    }

    private String getPage() {
        StringBuilder response = new StringBuilder();
        String line;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return response.toString();
    }

}
