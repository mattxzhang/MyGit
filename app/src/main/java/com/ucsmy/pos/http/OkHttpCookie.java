package com.ucsmy.pos.http;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import okhttp3.Cookie;

/**
 * Created by ucs_zhangjiaheng on 2017/3/13.
 */
public class OkHttpCookie implements Serializable {
    private transient final Cookie cookies;
    private transient Cookie clientCookies;

    public OkHttpCookie(Cookie cookies) {
        this.cookies = cookies;
    }

    public Cookie getCookies() {
        return clientCookies != null ? clientCookies : cookies;
    }

    public void writeObject(ObjectOutputStream os) throws IOException {
        os.writeObject(cookies.name());
        os.writeObject(cookies.value());
        os.writeLong(cookies.expiresAt());
        os.writeObject(cookies.domain());
        os.writeObject(cookies.path());
        os.writeBoolean(cookies.secure());
        os.writeBoolean(cookies.httpOnly());
        os.writeBoolean(cookies.hostOnly());
        os.writeBoolean(cookies.persistent());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        String name = (String) in.readObject();
        String value = (String) in.readObject();
        long expiresAt = in.readLong();
        String domain = (String) in.readObject();
        String path = (String) in.readObject();
        boolean secure = in.readBoolean();
        boolean httpOnly = in.readBoolean();
        boolean hostOnly = in.readBoolean();
        boolean persistent = in.readBoolean();
        Cookie.Builder builder = new Cookie.Builder();
        builder = builder.name(name);
        builder = builder.value(value);
        builder = builder.expiresAt(expiresAt);
        builder = hostOnly ? builder.hostOnlyDomain(domain) : builder.domain(domain);
        builder = builder.path(path);
        builder = secure ? builder.secure() : builder;
        builder = httpOnly ? builder.httpOnly() : builder;
        clientCookies = builder.build();
    }


}
