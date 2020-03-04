package com.xts.shop.apps;

import android.app.Application;

public class BaseApp extends Application {
    public static BaseApp sBaseApp;
    @Override
    public void onCreate() {
        super.onCreate();
        sBaseApp = this;
    }
}
