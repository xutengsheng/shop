package com.xts.shop.apps;

import android.app.Application;
import android.content.res.Resources;
import android.text.TextUtils;

import com.xts.shop.common.Constant;
import com.xts.shop.utils.SpUtils;

public class BaseApp extends Application {
    public static BaseApp sBaseApp;
    public static boolean sLogined;
    @Override
    public void onCreate() {
        super.onCreate();
        sBaseApp = this;
        String token = SpUtils.getInstance().getString(Constant.TOKEN);
        if (TextUtils.isEmpty(token)) {
            sLogined = false;
        }else {
            sLogined = true;
        }
    }

    public static Resources getRes(){
       return sBaseApp.getResources();
    }
}
