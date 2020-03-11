package com.xts.shop.model.apis;


import com.xts.shop.model.bean.GoodsListBean;
import com.xts.shop.model.bean.MainPageBean;
import com.xts.shop.model.bean.LoginBean;
import com.xts.shop.model.bean.SortCurrentBean;
import com.xts.shop.model.bean.SortTabBean;
import com.xts.shop.model.bean.TopicBean;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiServer {

    @GET("index")
    Flowable<MainPageBean> getHome();

    //专题数据请求接口
    @GET("topic/list")
    Flowable<TopicBean> getTopic(@Query("page") int page, @Query("size") int size);

    //登录接口
    @POST("auth/login")
    @FormUrlEncoded
    Flowable<LoginBean> login(@Field("nickname") String name, @Field("password") String psw);

    //注册接口
    @POST("auth/register")
    @FormUrlEncoded
    Flowable<LoginBean> register(@Field("nickname") String name, @Field("password") String psw);

    //分类竖直导航
    @GET("catalog/index")
    Flowable<SortTabBean> getSortTab();

    //分类右边对应的当前分类的数据
    @GET("catalog/current")
    Flowable<SortCurrentBean> getSortCurrentBean(@Query("id") int id);

    //商品列表数据
    @GET("goods/list")
    Flowable<GoodsListBean> getGoodsList(@Query("categoryId") int categoryId,
                                         @Query("page") int page,
                                         @Query("size") int size);


}
