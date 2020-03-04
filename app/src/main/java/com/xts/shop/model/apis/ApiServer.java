package com.xts.shop.model.apis;


import com.xts.shop.model.bean.HomeBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;

public interface ApiServer {

    @GET("index")
    Flowable<HomeBean> getHome();

}
