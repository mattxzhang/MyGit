package com.ucsmy.pos;

import android.app.Application;

/**
 * Created by ucs_zhangjiaheng on 2017/3/13.
 */

public class FosApp extends Application {
    public static FosApp app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

    }
}
