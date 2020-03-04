package com.xts.shop.common;

import com.xts.shop.apps.BaseApp;

import java.io.File;

public class Constant {
    public static final boolean DEBUG = true;

    public static final String PATH_DATA = BaseApp.sBaseApp.getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/shopapp";

    //商城的基础地址
    public static final String BASE_SHOP_URL = "https://cdwan.cn/api/";

    public static final int SUCCESS_CODE = 0;
}
