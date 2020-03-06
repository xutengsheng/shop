package com.xts.shop.common;

import com.xts.shop.apps.BaseApp;

import java.io.File;

public interface Constant {
    boolean DEBUG = true;

    String PATH_DATA = BaseApp.sBaseApp.getCacheDir().getAbsolutePath() + File.separator + "data";

    String PATH_CACHE = PATH_DATA + "/shopapp";

    //商城的基础地址
    String BASE_SHOP_URL = "https://cdwan.cn/api/";

    int SUCCESS_CODE = 0;
    String TOKEN = "token";
    String USER_NAME = "user_name";
    String AVATAR = "avatar";//头像
}
