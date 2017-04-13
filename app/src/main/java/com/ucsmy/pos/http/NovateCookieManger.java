package com.ucsmy.pos.http;

import com.ucsmy.pos.FosApp;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;


public class NovateCookieManger implements CookieJar {

    private static final String TAG = "NovateCookieManger";
    private static PersistentCookieStore cookieStore;

    /**
     * Mandatory constructor for the NovateCookieManger
     */
    public NovateCookieManger() {
        if (cookieStore == null && FosApp.app != null) {
            cookieStore = new PersistentCookieStore(FosApp.app.getApplicationContext());
        }
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                cookieStore.add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url);
        return cookies;
    }

}
