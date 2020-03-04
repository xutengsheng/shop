package com.xts.shop.apps;

import android.app.Application;
import android.content.res.Resources;

public class BaseApp extends Application {
    public static BaseApp sBaseApp;
    @Override
    public void onCreate() {
        super.onCreate();
        sBaseApp = this;
    }

    public static Resources getRes(){
       return sBaseApp.getResources();
    }
}
