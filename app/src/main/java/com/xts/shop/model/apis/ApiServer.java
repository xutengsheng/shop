package com.xts.shop.model.apis;


import com.xts.shop.model.bean.HomeBean;
import com.xts.shop.model.bean.TopicBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServer {

    @GET("index")
    Flowable<HomeBean> getHome();

    //专题数据请求接口
    @GET("topic/list")
    Flowable<TopicBean> getTopic(@Query("page") int page, @Query("size") int size);

}
