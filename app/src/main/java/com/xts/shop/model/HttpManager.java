package com.xts.shop.model;

import android.util.Log;

import com.xts.shop.common.Constant;
import com.xts.shop.model.apis.ApiServer;
import com.xts.shop.utils.SpUtils;
import com.xts.shop.utils.SystemUtils;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
        OkHttpClient client = getOkhttp();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .callFactory(new CallFactoryProxy(client) {
                    @Override
                    protected HttpUrl getNowUrl(String curUrl, Request request) {
                        String oldUrl = request.url().toString();
                        String newUrl = "";
                        HttpUrl httpUrl = null;
                        for(int i=0; i<CallFactoryProxy.URLS.length; i++){
                            int index = oldUrl.indexOf(CallFactoryProxy.URLS[i]);
                            if(index >= 0){
                                //用当前的基础地址替换原来得请求地址
                                newUrl = oldUrl.replace(CallFactoryProxy.URLS[i],curUrl);
                                httpUrl = HttpUrl.parse(newUrl);
                                break;
                            }
                        }
                        //如果没有新的地址替换，直接用原来得URL
                        if(httpUrl == null) httpUrl = request.url();
                        return httpUrl;
                    }
                })
                .build();

        return retrofit;
    }

    private OkHttpClient getOkhttp() {
        File file = new File(Constant.PATH_CACHE);
        Cache cache = new Cache(file,100*1024*1024);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new HeadersInterceptor())
                .addNetworkInterceptor(new NetWorkInterceptor())
                .cache(cache);
        if (Constant.DEBUG){
            builder.addInterceptor(new LoggingInterceptor());
        }
        OkHttpClient client = builder
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

    /**
     * 日志拦截器，打印请求接口的报文信息
     * 提供日志信息帮组优化代码
     */
    static class LoggingInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            //通过系统时间的差打印接口请求的信息
            long time = System.nanoTime();
            Request request = chain.request();
            Log.i("Request:",String.format("Sending request %s on %s%n%s",request.url(),chain.connection(),request.headers()));
            Response response = chain.proceed(request);
            long now = System.nanoTime();
            Log.i("Received:",String.format("Received response for %s in %.1fms%n%s",response.request().url(),(now-time)/1e6d,response.headers()));

            Log.i("Data:",response.peekBody(response.body().contentLength()).string());
            return response;
        }
    }

    /**
     * 请求的修改设置
     */
    static class HeadersInterceptor implements Interceptor{

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request().newBuilder()
                    .addHeader("Client-Type","ANDROID")
                    .addHeader("X-Nideshop-Token",
                            SpUtils.getInstance().getString(Constant.TOKEN))
                    .build();
            return chain.proceed(request);
        }
    }

    /**
     * 网络拦截器
     */
    static class NetWorkInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if(!SystemUtils.checkNetWork()){
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response response = chain.proceed(request);
            //通过判断网络连接是否存在获取本地或者服务器的数据
            if(!SystemUtils.checkNetWork()){
                int maxAge = 0;
                return response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control","public ,max-age="+maxAge).build();
            }else{
                int maxStale = 60*60*24*28; //设置缓存数据的保存时间
                return response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control","public, onlyif-cached, max-stale="+maxStale).build();
            }
        }
    }


}
