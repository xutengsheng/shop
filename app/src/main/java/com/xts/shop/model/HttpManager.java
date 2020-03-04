package com.xts.shop.model;

import com.xts.shop.common.Constant;
import com.xts.shop.model.apis.ApiServer;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpManager {
    private static volatile HttpManager instance;
    private ApiServer mApiServer;
    private HttpManager(){}

    public static HttpManager getInstance(){
        if (instance == null){
            synchronized (HttpManager.class){
                if (instance == null){
                    instance = new HttpManager();
                }
            }
        }

        return instance;
    }

    private Retrofit getRetrofit(String baseUrl){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkhttp())
                .build();

        return retrofit;
    }

    private OkHttpClient getOkhttp() {
        File file = new File(Constant.PATH_CACHE);
        Cache cache = new Cache(file,100*1024*1024);
        OkHttpClient client = new OkHttpClient.Builder()
                .cache(cache)
                .build();
        return client;
    }

    public ApiServer getApiService(){
        if (mApiServer == null){
            synchronized (HttpManager.class){
                if (mApiServer == null){
                    mApiServer = getRetrofit(Constant.BASE_SHOP_URL)
                            .create(ApiServer.class);
                }
            }
        }
        return mApiServer;
    }
}
